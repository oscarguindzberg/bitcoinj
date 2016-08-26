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
                //  2016-08-26

                // -- seed.bitcoin.sipa.be --
                0xb5b7abc,  0x2656795b, 0x27dd314d, 0x2bd5fd49, 0x396ed655, 0x4e869ac3, 0x5379c19d, 
                0x6d314346, 0x7cd532b9, 0x8099795b, 0x9fb23ac7, 0xa7a71952, 0xae97f388, 0xb0c69650, 
                0xb30da988, 0xb4ae72a7, 0xb8d3685d, 0xcd165a2f, 0xd006e855, 0xd4e25cb6, 0xdb2b31c1, 
                0xdd782f4e, 0xdda8d35f, 0xdf3aca4d, 0xec09af6c, 0xf27f0fc6, 
                // -- dnsseed.bluematt.me --
                0x21455c,   0x8d3795b,  0xc43a832,  0x10b59750, 0x11e6ff33, 0x25d03b8b, 0x2872495d, 
                0x4a233960, 0x65bffb94, 0x85c0ec68, 0x8cffd5a2, 0x908bf94d, 0x9ae98caf, 0xa2a18adb, 
                0xaba063c0, 0xc91ec98a, 0xce464aad, 0xd9cf4a52, 0xdd81f050, 0xdea916d2, 0xf4e79718, 
                // -- dnsseed.bitcoin.dashjr.org --
                0x317072e,  0x1340448a, 0x24ceca95, 0x25d03b8b, 0x28cee625, 0x2f4f448a, 0x374f448a, 
                0x38e38bba, 0x39a715ae, 0x3b4e18bc, 0x4219448a, 0x45a1bc56, 0x52816455, 0x6e559e6d, 
                0x6ead255f, 0x732a3b25, 0x7f37a152, 0x80def0ec, 0x89fdff18, 0x95d63b8b, 0xa0269856, 
                0xa73309b0, 0xc80109b0, 0xcd159e45, 0xdb22d82e, 0xe087652e, 0xe7c3c98a, 
                // -- seed.bitcoinstats.com --
                0xd17158,   0xacc5f52,  0x17b93bd0, 0x1ade1a34, 0x1d7bb54c, 0x22414932, 0x296dcb9f, 
                0x3f88da55, 0x41c2ffad, 0x454888d1, 0x4cbe3818, 0x574bba36, 0x57ee2e4e, 0x6b585dd0, 
                0x7c042434, 0x7ecbbe6d, 0x87e05247, 0x89fdff18, 0x8d35445b, 0x91c50360, 0x948219ae, 
                0xa47a652e, 0xb9df0834, 0xcaf31e45, 0xd070399c, 
                // -- seed.bitnodes.io --
                0x8201a46,  0xc5038b9,  0xdad096d,  0x1f751c59, 0x28da6e43, 0x384f3018, 0x3c78fa40, 
                0x3e443eb2, 0x3e9a40c3, 0x4a6af967, 0x5aacfd5c, 0x5d30f88f, 0x65be3eb9, 0x7bd532b9, 
                0x83441c2e, 0x86ca0125, 0x88d3c552, 0x962fdca2, 0x9d1b9658, 0xa0a7b62e, 0xbba05382, 
                0xc9561c51, 0xcc30202e, 0xcfc269db, 0xe60d7786
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
