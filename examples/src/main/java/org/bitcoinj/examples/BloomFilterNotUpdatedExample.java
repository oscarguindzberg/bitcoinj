/*
 * Copyright by the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bitcoinj.examples;

import com.google.common.collect.ImmutableList;
import org.bitcoinj.core.*;
import org.bitcoinj.core.listeners.TransactionConfidenceEventListener;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.*;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.listeners.KeyChainEventListener;
import org.bitcoinj.wallet.listeners.ScriptsChangeEventListener;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
import org.bitcoinj.wallet.listeners.WalletCoinsSentEventListener;
import org.bouncycastle.util.encoders.Hex;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class BloomFilterNotUpdatedExample {

    // Start a bitcoin core in localhost and mine manually some blocks to get bitcoin core wallet to have some available funds
    // Start the program
    // Send 1 btc to the bitcoinj wallet (address printed with "send money to" label)
    // Press 1 (and enter) to generate and print a tx that sends some change back to the wallet (tx1)
    // Use bitcoin core console to broadcast the tx1
    // Press 2 (and enter) to generate and print a tx that send  NO   change back to the wallet (tx2)
    // Use bitcoin core console to broadcast the tx2
    // Press 4 to print bitcoinj wallet summary
    // Result: bitcoinj wallet IS NOT aware of tx2
    // Use bitcoin core console to mine a block
    // Press 4 to print bitcoinj wallet summary
    // Result: bitcoinj wallet IS aware of tx2

    public static void main(String[] args) throws IOException, InsufficientMoneyException, InterruptedException, UnreadableWalletException {

        NetworkParameters params = RegTestParams.get();
        WalletAppKit kit = new WalletAppKit(new Context(params), Script.ScriptType.P2WPKH, null, new File("."), "walletappkit-example");
        //kit.setPeerNodes()

//        String seedCode = "rigid play wet wasp level harsh gain daughter exercise immense fancy cube";
//        String passphrase = "";
//        Long creationtime = 0l;
//        DeterministicSeed seed = new DeterministicSeed(seedCode, null, passphrase, creationtime);
//        kit.restoreWalletFromSeed(seed);

        kit.startAsync();
        kit.awaitRunning();

        System.out.println("wallet seed = " + kit.wallet().getKeyChainSeed().getMnemonicCode());

        kit.wallet().allowSpendingUnconfirmedTransactions();
        System.out.println("wallet = " + kit.wallet().toString());

        System.out.println("send money to: " + kit.wallet().freshReceiveAddress().toString());

        while (true) {
            int value = System.in.read();
            if (value == 49) {
                // Press 1 to send tx that sends change back to us
                SendRequest sr = SendRequest.to(Address.fromString(params, "bcrt1qqau7ad7lf8xx08mnxl709h6cdv4ma9u3ace5k2"), Coin.CENT);
                kit.wallet().sendCoins(kit.peerGroup(), sr);
//                kit.wallet().completeTx(sr);
//                System.out.println("tx to broadcast = " + Hex.toHexString(sr.tx.bitcoinSerialize(true)));
            } else  if (value == 50) {
                // Press 2 to send tx that DO NOT sends change back to us
                SendRequest sr = SendRequest.emptyWallet(Address.fromString(params, "bcrt1qqau7ad7lf8xx08mnxl709h6cdv4ma9u3ace5k2"));
                kit.wallet().sendCoins(kit.peerGroup(), sr);
//                kit.wallet().completeTx(sr);
//                System.out.println("tx to broadcast = " + Hex.toHexString(sr.tx.bitcoinSerialize(true)));
            } else  if (value == 51) {
                // Press 3 to print receiving address
                System.out.println("send money to: " + kit.wallet().freshReceiveAddress().toString());
            } else if (value == 52) {
                // Press 4 to print wallet status
                StringBuilder builder = new StringBuilder();
                builder.append("  ").append(kit.wallet().getTransactionPool(WalletTransaction.Pool.PENDING).size()).append(" pending\n");
                builder.append("  ").append(kit.wallet().getTransactionPool(WalletTransaction.Pool.UNSPENT).size()).append(" unspent\n");
                builder.append("  ").append(kit.wallet().getTransactionPool(WalletTransaction.Pool.SPENT).size()).append(" spent\n");
                System.out.println("Wallet: \n" + builder.toString());
                //System.out.println("wallet = " + kit.wallet().toString());
            }
        }
    }

}
