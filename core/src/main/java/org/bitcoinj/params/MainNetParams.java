/*
 * Copyright 2013 Google Inc.
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

package org.bitcoinj.params;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;

import static com.google.common.base.Preconditions.checkState;

/**
 * Parameters for the main production network on which people trade goods and services.
 */
public class MainNetParams extends AbstractBitcoinNetParams {
    public MainNetParams() {
        super();
        interval = INTERVAL;
        targetTimespan = TARGET_TIMESPAN;
        maxTarget = Utils.decodeCompactBits(0x1d00ffffL);
        dumpedPrivateKeyHeader = 128;
        addressHeader = 0;
        p2shHeader = 5;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        port = 8333;
        packetMagic = 0xf9beb4d9L;
        bip32HeaderPub = 0x0488B21E; //The 4 byte header that serializes in base58 to "xpub".
        bip32HeaderPriv = 0x0488ADE4; //The 4 byte header that serializes in base58 to "xprv"

        genesisBlock.setDifficultyTarget(0x1d00ffffL);
        genesisBlock.setTime(1231006505L);
        genesisBlock.setNonce(2083236893);
        id = ID_MAINNET;
        subsidyDecreaseBlockCount = 210000;
        spendableCoinbaseDepth = 100;
        String genesisHash = genesisBlock.getHashAsString();
        checkState(genesisHash.equals("000000000019d6689c085ae165831e934ff763ae46a2a6c172b3f1b60a8ce26f"),
                genesisHash);

        // This contains (at a minimum) the blocks which are not BIP30 compliant. BIP30 changed how duplicate
        // transactions are handled. Duplicated transactions could occur in the case where a coinbase had the same
        // extraNonce and the same outputs but appeared at different heights, and greatly complicated re-org handling.
        // Having these here simplifies block connection logic considerably.
        checkpoints.put(91722, Sha256Hash.wrap("00000000000271a2dc26e7667f8419f2e15416dc6955e5a6c6cdf3f2574dd08e"));
        checkpoints.put(91812, Sha256Hash.wrap("00000000000af0aed4792b1acee3d966af36cf5def14935db8de83d6f9306f2f"));
        checkpoints.put(91842, Sha256Hash.wrap("00000000000a4d0a398161ffc163c503763b1f4360639393e0e4c8e300e0caec"));
        checkpoints.put(91880, Sha256Hash.wrap("00000000000743f190a18c5577a3c2d2a1f610ae9601ac046a38084ccb7cd721"));
        checkpoints.put(200000, Sha256Hash.wrap("000000000000034a7dedef4a161fa058a2d67a173a90155f3a2fe6fc132e0ebf"));

        dnsSeeds = new String[] {
                "seed.bitcoin.sipa.be",        // Pieter Wuille
                "dnsseed.bluematt.me",         // Matt Corallo
                "dnsseed.bitcoin.dashjr.org",  // Luke Dashjr
                "seed.bitcoinstats.com",       // Chris Decker
                "seed.bitnodes.io",            // Addy Yeow
        };

        addrSeeds = new int[] {
                // note: These are in big-endian format, which is what the SeedPeers code expects.
                // These values should be kept up-to-date as much as possible.
                //
                // these values were created using this tool:
                //  https://github.com/dan-da/names2ips
                //
                // with this exact command:
                //  $ ./names2ips.php --hostnames=seed.bitcoin.sipa.be,dnsseed.bluematt.me,dnsseed.bitcoin.dashjr.org,seed.bitcoinstats.com,seed.bitnodes.io --format=code --ipformat=hex --endian=big
                //
                // on Date:
                //  2016-07-23
            
                // -- seed.bitcoin.sipa.be --
                0x9fb23ac7, 0xb0c69650, 0xe7e62060, 0x6aac84a4, 0x951f884f, 0xee2c39ae, 0x40caedc0, 
                0x40d4da52, 0xb16a0734, 0x82985552, 0xc431ea4d, 0x30ff8656, 0xe40c09b0, 0x4cd19378, 
                0x179c11b9, 0x824442d0, 0x66f4a556, 0xa3810a6e, 0x35b075d4, 0x7a3f8751, 0x5666cb40, 
                // -- dnsseed.bluematt.me --
                0x3917c658, 0x886fdd52, 0x554a28bc, 0x32757662, 0x5a751b9,  0x6329a36,  0xc91ec98a, 
                0x27689c68, 0x541b3b25, 0x6fafd57,  0x27fcb9f,  0xc3bf3d6c, 0xe87d0977, 0x1e07aca3, 
                0xd55309b0, 0x3246116c, 0x10b59750, 0x2510fb1,  0x960c448,  0x4618fa80, 0x5f2c7d47, 
                // -- dnsseed.bitcoin.dashjr.org --
                0x52bf6125, 0x5e68c3e,  0x50ca3dc6, 0x77ccc852, 0x550af443, 0xd29a7a56, 0x9066f568, 
                0xbf313b25, 0x66d54ddb, 0xa8c2f6ad, 0x1ecaedc0, 0x8f580944, 0x4c21625e, 0xd4e3c34a, 
                0x8b12cb44, 0x9bcb661b, 0x65936a7a, 0x6eab69bd, 0x34913953, 0x6527a57d, 0xea9f5344, 
                // -- seed.bitcoinstats.com --
                0x8aa6c36d, 0xb362f345, 0xd7cf1c0,  0xde47fec0, 0xfa4b2164, 0x1fc155d0, 0xa3d9f1c0, 
                0x40d7628,  0x1f03a5bc, 0x77e9f388, 0xb0c69650, 0x22a95e5f, 0xb8b29f59, 0x260bbc2,  
                0x62cedc51, 0x9cf54759, 0x7bdf8750, 0xbc75692e, 0xeb07cb9f, 0xf75f3e44, 0xe5efd5a2, 
                // -- seed.bitnodes.io --
                0xe0268353, 0x8442e32e, 0xb2822b3e, 0x27622346, 0x7a8bde4d, 0x126cf24d, 0x65c6084e, 
                0x330b75f,  0x50fea67,  0xfbdc656d, 0x299e008d, 0xcab7008d, 0x64fc0ab0, 0xb19220b0, 
                0xd187c2b0, 0xe658ecb2, 0xc71804b9, 0x179c11b9, 0x937fdebc, 0xa50cf7c2, 0xe06c1c7
        };
    }

    private static MainNetParams instance;
    public static synchronized MainNetParams get() {
        if (instance == null) {
            instance = new MainNetParams();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return PAYMENT_PROTOCOL_ID_MAINNET;
    }
}
