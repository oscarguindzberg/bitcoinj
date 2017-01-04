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
                //  2017-01-03
                
                // -- dnsseed.bluematt.me --
                0xc24346,   0x2b133ad,  0x340ff68,  0x46d1955,  0x586df50,  0x690a368,  0x10df77ae, 
                0x1281afb2, 0x17526358, 0x2f028a3e, 0x3faeca95, 0x4067112e, 0x6af85a54, 0x6c3b112f, 
                0x6d06f9a2, 0x6d5b4c90, 0x704dff1b, 0x7664c94f, 0x84e063c0, 0x86463b25, 0x86dcbd7c, 
                0x8a5b448a, 0x8d9ec24e, 0x8e12bcd1, 0x95f75e17, 0x9a36e953, 0x9bc5652e, 0xa98df05b, 
                0xaaa6bd49, 0xb4421c60, 0xbd56089f, 0xc30f165f, 0xd4133895, 0xd4abad57, 0xdb21252d, 
                0xe3381c6c, 0xe7224836, 0xe859aa5c, 0xe8bbd96f, 0xedad67ac, 0xf27f0fc6, 0xf9c616ad, 
                // -- dnsseed.bitcoin.dashjr.org --
                0xa298953,  0x13261a78, 0x143dfe4c, 0x14f9435e, 0x3137b75f, 0x33cfd4ad, 0x36000f33, 
                0x3b68192e, 0x3b90db5a, 0x3f1b2a5f, 0x50f78656, 0x612c2d5f, 0x726d102f, 0x803bff8e, 
                0x843be6c3, 0x8d248d3e, 0x94f6345c, 0x98394a2d, 0xa2132dd4, 0xaae09e4b, 0xb82f0f33, 
                0xca992032, 0xcfd4f150, 0xe9527a79, 0xebd311b7, 0xfa34105b, 0xfab0764b, 
                // -- seed.bitcoinstats.com --
                0x40e3a34,  0xae7bad4,  0xebbbd42,  0x11bb652e, 0x13793b25, 0x2226f368, 0x2c1e5743, 
                0x37688852, 0x4f78f017, 0x4fd6d33d, 0x51169c32, 0x5a595a2f, 0x5ca35e36, 0x8b099357, 
                0x8ecebb73, 0x92346f5f, 0xa2e08c80, 0xa2e31e45, 0xa327ad6c, 0xc287d0a2, 0xc8439553, 
                0xc9040051, 0xd1261b4c, 0xf7ee3856, 0xf8a4be6d, 
                // -- seed.bitnodes.io --
                0x4e05ad1,  0xed99ea3,  0x159330c7, 0x2112dc1f, 0x23aeae4d, 0x27622346, 0x33cfd4ad, 
                0x3b68192e, 0x528bbc50, 0x55400ac1, 0x65f13bd4, 0x6b23725b, 0x7b297183, 0x84306fd0, 
                0x84e6bf5a, 0x96d0ded5, 0xb6669b83, 0xb77a995f, 0xbc245f59, 0xd094fd17, 0xd74f5dc1, 
                0xddc83818, 0xde7b2cbc, 0xdf0951d1, 0xf2740ab0,                 
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
