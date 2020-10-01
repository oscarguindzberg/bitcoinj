package org.bitcoinj.examples;

import com.google.common.collect.ImmutableList;
import org.bitcoinj.core.Address;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.KeyChain;
import org.bitcoinj.wallet.Wallet;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class XpubTest {
    public static final ImmutableList<ChildNumber> BIP44_BTC_NON_SEGWIT_ACCOUNT_PATH = ImmutableList.of(
            new ChildNumber(44, true),
            new ChildNumber(0, true),
            ChildNumber.ZERO_HARDENED);

    public static void main(String[] args) throws IOException {
        String bsqXpub = "xpubxxxx";
        String btcLegacyXpub = "xpubxxxx";
        String btcSegwitXpub = "zpubxxxx";

        FileWriter fw = new FileWriter("addresses.txt");
        PrintWriter pw = new PrintWriter(fw);
        DeterministicKey watchKey = DeterministicKey.deserializeB58(null, btcSegwitXpub, MainNetParams.get());
        watchKey.setCreationTimeSeconds(0);
        System.out.println("watchKey = " + watchKey);
        DeterministicKeyChain chain = DeterministicKeyChain.builder().watch(watchKey).outputScriptType(Script.ScriptType.P2PKH)
                .build();
        System.out.println("chain = " + chain);

        for (int i = 0; i < 900; i++) {
            DeterministicKey key = chain.getKey(KeyChain.KeyPurpose.RECEIVE_FUNDS);
            System.out.println("key = " + key);
            Address a = Address.fromKey(MainNetParams.get(), key, Script.ScriptType.P2PKH);
            pw.println(a);
        }
        pw.close();

    }
}
