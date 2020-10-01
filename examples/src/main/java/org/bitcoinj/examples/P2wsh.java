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
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptPattern;
import org.bitcoinj.wallet.RedeemData;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.KeyChainEventListener;
import org.bitcoinj.wallet.listeners.ScriptsChangeEventListener;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
import org.bitcoinj.wallet.listeners.WalletCoinsSentEventListener;
import org.bouncycastle.util.encoders.Hex;

import java.io.File;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bitcoinj.script.ScriptOpCodes.OP_CHECKMULTISIG;

public class P2wsh {

    public static void main(String[] args) {

        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.WARNING);

        NetworkParameters params = RegTestParams.get();

        WalletAppKit kit = new WalletAppKit(params, Script.ScriptType.P2WPKH, null, new File("."), "p2wsh-example");
        kit.connectToLocalHost();

        kit.startAsync();
        kit.awaitRunning();

        System.out.println("Receive address " + kit.wallet().currentReceiveAddress());
        System.out.println("Balance " + kit.wallet().getBalance().toFriendlyString());

//        ECKey key1 = new ECKey();
//        ECKey key2 = new ECKey();
//        byte[] pubKey1 = key1.getPubKey();
//        byte[] pubKey2 = key2.getPubKey();
//        Script p2SHMultiSigOutputScript = ScriptBuilder.createP2SHOutputScript(get2of2MultiSigRedeemScript(pubKey1, pubKey2));
//        Address p2shAddress = LegacyAddress.fromScriptHash(params, ScriptPattern.extractHashFromP2SH(p2SHMultiSigOutputScript));
//        System.out.println("p2shAddress = " + p2shAddress);

        // ECKey key1 = new ECKey();
        // ECKey key2 = new ECKey();
        ECKey key1 = ECKey.fromPrivate(Hex.decode("f8990b8d0aa88cb2a1c9032c6bf729cecb1c02ae5ecf1745dc73a2472e12d973"));
        ECKey key2 = ECKey.fromPrivate(Hex.decode("23a666c90e1d184384c450079d934ce45f29a81eff4a4d9377ef73dcc771286d"));

        ECKey pubKey1 = ECKey.fromPublicOnly(key1.getPubKey());
        ECKey pubKey2 = ECKey.fromPublicOnly(key2.getPubKey());
        // Take care of sorting! Need to reverse to the order we use normally
        List<ECKey> keys = ImmutableList.of(pubKey2, pubKey1);

        Script redeemScript = ScriptBuilder.createMultiSigOutputScript(2, keys);
        Script p2wshMultiSigOutputScript = ScriptBuilder.createP2WSHOutputScript(redeemScript);
        Address p2wshAddress = SegwitAddress.fromHash(params, ScriptPattern.extractHashFromP2SH(p2wshMultiSigOutputScript));
        // p2wshAddress = bcrt1q0u7t93uy40tgzflggd0ypmgmp03dfanzj80vjcu7lwlquuaplcjqtm45t6
        System.out.println("key1 = " + key1.getPrivateKeyAsHex());
        System.out.println("key2 = " + key2.getPrivateKeyAsHex());
        System.out.println("p2wshAddress = " + p2wshAddress);

        // Sent 5btc to "bcrt1q0u7t93uy40tgzflggd0ypmgmp03dfanzj80vjcu7lwlquuaplcjqtm45t6"
        // with txid "2a4e2a84d2ddb30055e4a9c1aad852961e33202332a65bdb157b917f08d97a15"

        String fundingTxString = "02000000000101a5e406d15613e50b9ec3cf945327291f2866708b5db4384dc20f36772bd091380000000000fdffffff020065cd1d000000002200207f3cb2c784abd68127e8435e40ed1b0be2d4f66291dec9639efbbe0e73a1fe24b9187c4d00000000160014e2966625a9f98a79c4ca9c3701354fc54e701119024730440220498fe6d43b67ca536d01f800edee809a5cfe29e92e356acbb87bd2e6eec41a6e02206fefa7b7c27d67c2911584133391ea5484c5cb3d89617097c01081f49b6e2fb001210265d063cf4b5779f5dd355e544423d5c6e08462ea036a0dfe1ddb02676b64af0c76000000";
        Transaction fundingTx = new BitcoinSerializer(params, false).makeTransaction(Hex.decode(fundingTxString));
        int fundingOutputIndex = 0;
        TransactionOutput fundingOutput = fundingTx.getOutput(fundingOutputIndex);

        Transaction spendTx = new Transaction(params);
        spendTx.addOutput(Coin.COIN, Address.fromString(params, "bcrt1qg9us4mz3jmjx2p348y4jqexgqqj6nssy7wjguc"));
        spendTx.addInput(fundingOutput);
        TransactionInput spendInput = spendTx.getInput(0);

        // Script scriptPubKey = fundingOutput.getScriptPubKey();
        // RedeemData redeemData = RedeemData.of(keys, redeemScript);
        // byte[] script = redeemScript.getProgram();

        // key1 sign
        Coin inputValue = spendInput.getValue();
        Script scriptCode = redeemScript;
        TransactionSignature signature1 = spendTx.calculateWitnessSignature(0, key1, scriptCode, inputValue,
                Transaction.SigHash.ALL, false);

        TransactionSignature signature2 = spendTx.calculateWitnessSignature(0, key2, scriptCode, inputValue,
                Transaction.SigHash.ALL, false);

        spendInput.setScriptSig(ScriptBuilder.createEmpty());

        TransactionWitness witness = new TransactionWitness(2);
        witness.setPush(0, new byte[]{});
        witness.setPush(1, signature2.encodeToBitcoin());
        witness.setPush(2, signature1.encodeToBitcoin());
        witness.setPush(3, redeemScript.getProgram());

        spendInput.setWitness(witness);

        kit.peerGroup().broadcastTransaction(spendTx);

        System.out.println("shutting down again");
        kit.stopAsync();
        kit.awaitTerminated();
    }

}
