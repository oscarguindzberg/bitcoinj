package org.bitcoinj.tools.playground;

import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;

import java.io.File;

public class FullBlockOnSpv {
    public static void main(String[] args) {
        final Context btcContext = new Context(RegTestParams.get());
        WalletAppKit kit = new WalletAppKit(btcContext.getParams(), new File("."), "FullBlockOnSpv");
        kit.startAsync();
        kit.awaitRunning();

        kit.peerGroup().addBlocksDownloadedEventListener((peer, block, filteredBlock, blocksLeft) -> {
            Context.propagate(btcContext);
            // filteredBlock may be null if we are downloading just headers before fastCatchupTimeSecs
            // or we are receiving full blocks
            if (filteredBlock != null) {
                System.out.println("Received filteredBlock " + filteredBlock.getHash());
                boolean isBlockRelevant = false;
                for (Transaction tx : filteredBlock.getAssociatedTransactions().values()) {
                    if (kit.wallet().isTransactionRelevant(tx)) {
                        isBlockRelevant = true;
                    }
                }
                System.out.println("isBlockRelevant = " + isBlockRelevant);
                if (isBlockRelevant) {
                    // block is sending a tx that we care about, request the full block in order to receive witnesses.
                    GetDataMessage getDataMessage = new GetDataMessage(btcContext.getParams());
                    getDataMessage.addBlock(filteredBlock.getHash(), true);
                    kit.peerGroup().getDownloadPeer().sendMessage(getDataMessage);
                }
                // Don't invoke BtcToRskClient.onBlock() with a filtered block
            } else {
                System.out.println("Received block " + block.getHash());
                if (block.getTransactions()!=null) {
                    System.out.println("block = " + block);
                    // Here, invoke BtcToRskClient.onBlock() with a full Block.
                }
            }
        });
        kit.wallet().addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener() {
            @Override
            public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
                System.out.println("Received tx = " + tx);
            }
        });

        System.out.println("kit.wallet().currentReceiveAddress() = " + kit.wallet().currentReceiveAddress());
        kit.awaitTerminated();
    }
}
