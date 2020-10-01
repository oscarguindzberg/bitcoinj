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
import org.bitcoinj.crypto.KeyCrypter;
import org.bitcoinj.crypto.KeyCrypterScrypt;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptPattern;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignPerformance {

    public static void main(String[] args) {

        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.WARNING);

        NetworkParameters params = RegTestParams.get();
        Sha256Hash message = Sha256Hash.twiceOf(new byte[] {1, 2, 3});

        ECKey key1 = new ECKey();
        key1.getPrivateKeyAsHex();
        KeyCrypter scrypt = new KeyCrypterScrypt();
        KeyParameter derivedKey = scrypt.deriveKey("hello");
        ECKey key2 = key1.encrypt(scrypt, derivedKey);
        //ECKey.ECDSASignature signature =
        long nanoTime1 = System.nanoTime();
        //long millis1 = System.currentTimeMillis();
        ECKey key3 = key2.decrypt(scrypt, derivedKey);
//        key1.sign(message);
        //long millis2 = System.currentTimeMillis();
        long nanoTime2 = System.nanoTime();
        //System.out.println("(millis2 - millis1) = " + (millis2 - millis1));
        System.out.println("(nanoTime2 - nanoTime1) = " + (nanoTime2 - nanoTime1));
    }

}
