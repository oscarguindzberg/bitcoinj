package org.bitcoinj.examples;

import com.google.common.collect.ImmutableList;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.bouncycastle.util.encoders.Hex;

public class WalletRecovery {

    public static void main(String[] args) throws Exception {
        String seedCode = "xxxx"; // User seed words eg "yard impulse luxury drive today throw farm pepper survey wreck glass federal"
        Address userBtcAddress = Address.fromString(MainNetParams.get(), "xxx"); // Address to send recovered funds to e.g. "12dRugNcdxK39288NjcDV4GX7rMsKCGn6B"
        Address devBtcAddress = Address.fromString(MainNetParams.get(), "xxxx"); // Address to send recovered funds to e.g. "12dRugNcdxK39288NjcDV4GX7rMsKCGn6B"
        long devPercentageCompensation = 7; // e.g. 30 for 30%


        // Txs to spend:
        // https://bitaps.com/8d3601bc440e086d6eaa26e4940dab4faf71d2ebedf00342f11778342b1f2be9
        String inputTx1Hex = "0100000001120e34cc9907b50d7d416d01d1d1b6b33721afd4c0eca269df79eaeb8ea0eb0100000000d90047304402206815054e99cb113dc2b72017013c5961d978a0e61d5a308b7858d92bd4a529d5022022b6149600b003e080ef999ba4e9f4ced9203167de0410e085a9671e7d7266fb014730440220720c8a7a0ae883642713248681b5116b1be445efc5ab15863a441328309d17d802203d588425ad6cd203a4b386b3177d7297a1dd987a014f0b30dc307df4635d719701475221035d0394dba459a985b575c1e767b42291afc9dea63c97d6d5e32e186a77ef87ba21037bd2068d9a1f7080f78689b7d6b5f649cb0f148008d2ac6689a08b82d758f25252aefdffffff02c8d4df00000000001976a9148621899a18c24ec29bfc56fec9aa237f7d44db3c88ac483e4700000000001976a91449ed59d6995a4b23a8c82ae1618129db627bd74d88ac00000000";
        Transaction inputTx1 = new Transaction(MainNetParams.get(), Hex.decode(inputTx1Hex));

        // https://bitaps.com/ac20a992027585e94f910167e8eb0a8c6b105a1d927a0642bc8c522e61f8d620
        String inputTx2Hex = "010000000178d30496501a036e49e4370f54a6c27560f148ba603994fe4737cd4cfec38b4f00000000d900463043021f5e7605ddfc937938d116c70d86512232d3d5df99a2e9012d9a22a1dcfbd83d02206b42b94963169a1cdae40da336737b1902d16407e41d583c0f44a74a551ff551014830450221008bcb1a216208358c751671e31a147c4d9461718797943cedb3725444d201decd02202fb0d1ac18062e3ad37bd1ce56aeda2fe5467c649c496031c572c1ee704deece01475221029e111d5f5c2dc7c63c747735497662bca6c8096e17172265570504c0ba27641e21023a4c4b3cd8d1a8e5aa1c80159e5b80d0079eda018a77565cca0b5a68df4c7a3252aeffffffff02108f4200000000001976a914b05d08db821f50773a6b3ce7c6c531bc8f0ea01688ac50c81400000000001976a914db12b17ee241532594917622cade702340c5e9d988ac00000000";
        Transaction inputTx2 = new Transaction(MainNetParams.get(), Hex.decode(inputTx2Hex));

        // https://bitaps.com/0f6611d857c0cf2178163ac542f9e2aa2044d02689571a09c1b75b1819306932
        String inputTx3Hex = "01000000012749d4b4f297f89bd61c01f7b84b04fd46ba7be0d28e6cf1aee3945d3eeebd7700000000da00483045022100d15715763ff94752357371c62784f1e87aac6da9d55f663e99333ee81452ad45022000eef0f10bf6f6a4343fe999c5cae05731218833ce1dd6b63ef0d6e2ee5df7dd01473044022060a21aea6d1d01792a928edc1436b1d87ed9a30fbdc35ab7dc76302fd3efdd0e02206f8ae79cf2f2bd396c592f01b07cc23be7fbb1ad6fc187adc006f04cc59f7bf40147522103e4d20ea2fad1e19056ed47cb669c34e0f5c577661b4362beaadcff0d63f6def92103b3edccdda149715c6c6d8c3aa5ef430808b424e6304a56ef3460d5c2071c775e52aeffffffff020caa8a00000000001976a91465f50f0ddaee72e0505965cece60766ee3fd645988acfc4b2b00000000001976a9149506ce4ec961060ba0aec6f98109de15cde4536888ac00000000";
        Transaction inputTx3 = new Transaction(MainNetParams.get(), Hex.decode(inputTx3Hex));

        Coin totalOutputValue = Coin.valueOf(14669000).add(Coin.valueOf(4362000)).add(Coin.valueOf(9087500l));
        long userPercentageCompensation = 100 - devPercentageCompensation;
        Coin userOuputValue = totalOutputValue.multiply(userPercentageCompensation).div(100);
        Coin devOuputValue = totalOutputValue.multiply(devPercentageCompensation).div(100);

        String passphrase = "";
        Long creationtime = 0l;
        DeterministicSeed seed = new DeterministicSeed(seedCode, null, passphrase, creationtime);
        ImmutableList<ChildNumber> BIP44_BTC_SEGWIT_ACCOUNT_PATH = ImmutableList.of(
                new ChildNumber(44, true),
                new ChildNumber(0, true),
                ChildNumber.ONE_HARDENED);
        Wallet wallet = Wallet.fromSeed(MainNetParams.get(), seed, Script.ScriptType.P2PKH, BIP44_BTC_SEGWIT_ACCOUNT_PATH);
        for (int i = 0; i < 500; i++) {
            wallet.freshReceiveAddress(Script.ScriptType.P2PKH);
        }

        wallet.commitTx(inputTx1);
        wallet.commitTx(inputTx2);
        wallet.commitTx(inputTx3);

        Transaction tx = new Transaction(MainNetParams.get());
        //tx.addOutput(totalOuputValue, userBtcAddress);
        tx.addOutput(userOuputValue, userBtcAddress);
        tx.addOutput(devOuputValue, devBtcAddress);
        tx.addInput(inputTx1.getOutput(0));
        tx.addInput(inputTx2.getOutput(0));
        tx.addInput(inputTx3.getOutput(0));

        SendRequest sr = SendRequest.forTx(tx);
        sr.recipientsPayFees = true;
        wallet.completeTx(sr);

        System.out.println("tx.bitcoinSerialize() = " + Hex.toHexString(tx.bitcoinSerialize()));

        // Then you can broadcast tx using https://www.blockchain.com/btc/pushtx
    }
}
