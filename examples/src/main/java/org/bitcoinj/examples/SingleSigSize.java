
package org.bitcoinj.examples;

import org.bitcoinj.core.*;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleSigSize {

    public static void main(String[] args) {

        boolean segwit = true;
        boolean bsqInput = false;

        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.WARNING);

        NetworkParameters params = RegTestParams.get();

        String address;
        if (segwit) {
            address = "bcrt1qzc946ljn7x6evaeddn7s6zdlqy0mg49n069gwa";
        } else {
            address = "mu45HfNNCtVDA2guPKpcbGrVyrufroeHr8";
        }

        Transaction fundingTx = new Transaction(params);
        fundingTx.addOutput(Coin.COIN, Address.fromString(params, address));
        fundingTx.addOutput(Coin.COIN, Address.fromString(params, "mu45HfNNCtVDA2guPKpcbGrVyrufroeHr8"));
        TransactionOutput fundingOutput0 = fundingTx.getOutput(0);
        TransactionOutput fundingOutput1 = fundingTx.getOutput(1);

        Transaction spendTx = new Transaction(params);
        spendTx.addOutput(Coin.CENT, Address.fromString(params, "mu45HfNNCtVDA2guPKpcbGrVyrufroeHr8")); // Pay bisq fee in btc or bsq
        spendTx.addOutput(Coin.CENT, Address.fromString(params, address)); // Reserved for trade
        spendTx.addOutput(Coin.CENT, Address.fromString(params, address)); // Change to user

        TransactionInput spendInput0 = spendTx.addInput(fundingOutput0);
        ECKey sigKey0 = new ECKey();
        if (segwit) {
            Script scriptCode = ScriptBuilder.createP2PKHOutputScript(sigKey0);
            Coin value = spendInput0.getValue();
            TransactionSignature txSig = spendTx.calculateWitnessSignature(0, sigKey0, scriptCode, value,
                    Transaction.SigHash.ALL, false);
            spendInput0.setScriptSig(ScriptBuilder.createEmpty());
            spendInput0.setWitness(TransactionWitness.redeemP2WPKH(txSig, sigKey0));
        } else {
            Sha256Hash hash0 = spendTx.hashForSignature(0, fundingOutput0.getScriptPubKey(), Transaction.SigHash.ALL, false);
            ECKey.ECDSASignature signature0 = sigKey0.sign(hash0,null);
            TransactionSignature txSig0 = new TransactionSignature(signature0, Transaction.SigHash.ALL, false);
            spendInput0.setScriptSig(ScriptBuilder.createInputScript(txSig0, sigKey0));
        }

        if (bsqInput) {
            TransactionInput spendInput1 = spendTx.addInput(fundingOutput1);
            ECKey sigKey1 = new ECKey();
            Sha256Hash hash1 = spendTx.hashForSignature(1, fundingOutput1.getScriptPubKey(), Transaction.SigHash.ALL, false);
            ECKey.ECDSASignature signature1 = sigKey1.sign(hash1,null);
            TransactionSignature txSig1 = new TransactionSignature(signature1, Transaction.SigHash.ALL, false);
            spendInput1.setScriptSig(ScriptBuilder.createInputScript(txSig1, sigKey1));
        }

        System.out.println("spendTx = " + spendTx);
        System.out.println("spendTx vsize = " + spendTx.getVsize());


    }

}
