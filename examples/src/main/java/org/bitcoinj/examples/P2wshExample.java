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

import org.bitcoinj.core.*;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.script.Script;
import org.bouncycastle.util.encoders.Hex;

import java.util.logging.Level;
import java.util.logging.Logger;

public class P2wshExample {

    public static void main(String[] args) {

        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.WARNING);

        NetworkParameters params = MainNetParams.get();


        Transaction signedTx = new BitcoinSerializer(params, false).makeTransaction(Hex.decode("02000000000101f91e644ebc2227e1cf948afdf3ba72bfb5b95d69292b54e0024d830d1c9840450000000000fdffffff0339c616000000000017a91445fc7236a4fb670df749ce2a6728165824901682872b4df4040000000017a9141a3b77f30f270a684f84ac8784b0d5c179ff6ee3878866cc12000000002200207afddf5c6a3632977a9a2918b9d472c910cadcd53b487479b9a248f94c912c6b050047304402205aa7dc47e417e458c94b69f0fd7bc2afa6f2b796730b45c476fd5859ce72c58b02206dcc03da1d2cda4bf2cf2966a9f166d7a9a2055827299a8d72dc460167de2f2a014730440220592f4a79b824fb3141addd633ce5e86fde7870cc224c17dce7bbc6bfb25744590220682febe13e7fb4ae6a02ad3f4142ecd4d796b788c7044bff33f6c6fcb8ff288201473044022043b48a8181234f09d4cc206d6bcb6bd8d28f0cc416c739ee20d9bda5911f81090220341480cebe66767564ccea041e42bd4b144d46e861c02686cd0dee3e81c4f94301ad53210246212229272880c86689cbf2df856ca69b89a37252d979d7209bbc04a10f45092102ae08416173df6d5f42ae3c457dd90312cf36aee7b3f6c615aae8f31c6627867d2103198270acaa2e19ea69c72189a645ab0e974a6d302d3732caf2c90472c711824621033051627c7a4f1df5c0af74d00f8aac86cd0704a1fd194ffa482716a9200b80bf21039c638267e140023e3c66aceea3e0c85f2677a16d75fc4853d52f4e6ad47d475a55ae7bf00900"));

        System.out.println("signedTx = " + signedTx);


    }

}
