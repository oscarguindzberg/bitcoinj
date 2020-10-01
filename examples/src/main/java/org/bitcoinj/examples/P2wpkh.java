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
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptPattern;
import org.bouncycastle.util.encoders.Hex;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class P2wpkh {

    public static void main(String[] args) {

        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.WARNING);

        NetworkParameters params = RegTestParams.get();

        WalletAppKit kit = new WalletAppKit(params, Script.ScriptType.P2WPKH, null, new File("."), "p2wsh-example");
        kit.connectToLocalHost();

        kit.startAsync();
        kit.awaitRunning();

        // System.out.println("Receive address " + kit.wallet().currentReceiveAddress());
//        System.out.println("Balance " + kit.wallet().getBalance().toFriendlyString());
//        ECKey key = kit.wallet().findKeyFromAddress(kit.wallet().currentReceiveAddress());
//        System.out.println("key.getPrivateKeyAsHex() = " + key.getPrivateKeyAsHex());

        ECKey key1 = ECKey.fromPrivate(Hex.decode("3c1e0c0276b348fc0830112ffc35c07e072aedc779d02a1ab0e02f1515551280"));
        ECKey pubKey1 = ECKey.fromPublicOnly(key1.getPubKey());

        Address p2pkhAddress = SegwitAddress.fromKey(params, pubKey1); // bcrt1q9mgp77w77qahk7sy23mzths2xna8h34fhzf9m2
//
//        // Sent 8 btc to "bcrt1q9mgp77w77qahk7sy23mzths2xna8h34fhzf9m2"
//        // with txid "4f1c5dffd1f90487a665a5d010337871629886a6496a762d230811f866c0051d"
//
        String fundingTxString = "02000000000101a2256fc01869db85531b08ce0036645357cbc866234cbcde4376165499b829f20000000000fdffffff020008af2f000000001600142ed01f79def03b7b7a04547625de0a34fa7bc6a96fe183df00000000160014115d2c21406d39e843f83d2f9658f1a7111cfc5d0247304402206ad8bd9c05e496b8e85e0b825c1c17c41b1141b8179aef8db561d16e4690b72f0220645f687431307b2e580759e30f8b84587c962bf2fd72d014a92a370e8189de1a01210300a09dfc5a1979fc0a0354319408aaf57e0119f1abf20c80fc109934e9a68a0276000000";
        Transaction fundingTx = new BitcoinSerializer(params, false).makeTransaction(Hex.decode(fundingTxString));
        int fundingOutputIndex = 0;
        TransactionOutput fundingOutput = fundingTx.getOutput(fundingOutputIndex);

        Transaction spendTx = new Transaction(params);
        spendTx.addOutput(Coin.CENT, Address.fromString(params, "bcrt1qwn3523y68p73khzgwyqjq7ka9jr8qym4gfqsqs"));
        spendTx.addInput(fundingOutput);
        TransactionInput spendInput = spendTx.getInput(0);

        Script scriptCode = ScriptBuilder.createP2PKHOutputScript(pubKey1);
        Coin value = spendInput.getValue();
        TransactionSignature signature = spendTx.calculateWitnessSignature(0, key1, scriptCode, value,
                Transaction.SigHash.ALL, false);
        spendInput.setScriptSig(ScriptBuilder.createEmpty());
        spendInput.setWitness(TransactionWitness.redeemP2WPKH(signature, key1));


         kit.peerGroup().broadcastTransaction(spendTx);

        System.out.println("shutting down again");
        kit.stopAsync();
        kit.awaitTerminated();
    }

    private static Script get2of2MultiSigRedeemScript(byte[] key1, byte[] key2) {
        ECKey pubKey1 = ECKey.fromPublicOnly(key1);
        ECKey pubKey2 = ECKey.fromPublicOnly(key2);
        // Take care of sorting! Need to reverse to the order we use normally
        List<ECKey> keys = ImmutableList.of(pubKey2, pubKey1);
        return ScriptBuilder.createMultiSigOutputScript(2, keys);
    }

}
