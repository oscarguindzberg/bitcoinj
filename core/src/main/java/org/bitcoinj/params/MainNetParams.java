/*
 * Copyright 2013 Google Inc.
 * Copyright 2015 Andreas Schildbach
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

import org.bitcoinj.core.*;
import org.bitcoinj.net.discovery.*;

import java.net.*;

import static com.google.common.base.Preconditions.*;

/**
 * Parameters for the main production network on which people trade goods and services.
 */
public class MainNetParams extends AbstractBitcoinNetParams {
    public static final int MAINNET_MAJORITY_WINDOW = 1000;
    public static final int MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED = 950;
    public static final int MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE = 750;

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

        majorityEnforceBlockUpgrade = MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE;
        majorityRejectBlockOutdated = MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED;
        majorityWindow = MAINNET_MAJORITY_WINDOW;

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
                "seed.bitcoin.sipa.be",         // Pieter Wuille
                "dnsseed.bluematt.me",          // Matt Corallo
                "dnsseed.bitcoin.dashjr.org",   // Luke Dashjr
                "seed.bitcoinstats.com",        // Chris Decker
                "seed.bitnodes.io",             // Addy Yeow
                "seed.bitcoin.jonasschnelli.ch" // Jonas Schnelli
        };
        httpSeeds = new HttpDiscovery.Details[] {
                // Andreas Schildbach
                new HttpDiscovery.Details(
                        ECKey.fromPublicOnly(Utils.HEX.decode("0238746c59d46d5408bf8b1d0af5740fe1a6e1703fcb56b2953f0b965c740d256f")),
                        URI.create("http://httpseed.bitcoin.schildbach.de/peers")
                )
        };


        // note: These are in big-endian format, which is what the SeedPeers code expects.
        // These values should be kept up-to-date as much as possible.
        //
        // these values were created using this tool:
        //  https://github.com/dan-da/names2ips
        //
        // with this exact command:
        //  $ ./names2ips.php --hostnames=seed.bitcoin.sipa.be,dnsseed.bluematt.me,dnsseed.bitcoin.dashjr.org,seed.bitcoinstats.com,seed.bitnodes.io,seed.bitcoin.jonasschnelli.ch --format=code --ipformat=hex --endian=big

        // TODO better would be that use same as deliver to Bitcoin Core: https://github.com/bitcoin/bitcoin/tree/master/contrib/seeds
        // Prefer to not use the list from BitoinJ

        // Updated Dec. 25th 2018
        addrSeeds = new int[]{
// -- seed.bitcoin.sipa.be --
                0x203a04d5, 0x2dac105e, 0x308e1c5e, 0x31e521,   0x3f6f448a, 0x40ce9850, 0x50179d50,
                0x5edfd4ad, 0x6059c450, 0x842ef657, 0x8bff4834, 0x8c745658, 0x90242b22, 0x90410730,
                0x9057b8d3, 0x9cfe8751, 0xa931315b, 0xab746457, 0xb0c4d536, 0xd01e9937, 0xd0cf0a30,
                0xd5f05b7c, 0xf0aba72e, 0xf3da5075, 0xf6ca1d62,
// -- dnsseed.bluematt.me --
                0x12541140, 0x2f0586bc, 0x35e10bc2, 0x6042445c, 0x657b1ec1, 0x6987e18,  0x750b2f44,
                0x82a4f9ac, 0x8337c658, 0x8566c752, 0x8ba8d35f, 0x8d76b4d2, 0xab65d662, 0xca3e036b,
                0xcae06bd0, 0xd940042e, 0xdd7fc56d, 0xe14bf58,  0xf28dfa68, 0xf33e8263, 0xf5c9a2b2,
// -- dnsseed.bitcoin.dashjr.org --
                0x1797a347, 0x1b0e7e4a, 0x2cb55258, 0x40e24212, 0x414ad654, 0x54ded5b,  0x55b1f323,
                0x577fcd23, 0x6b56419f, 0x7398e734, 0x77d66b51, 0x7b1db743, 0x90b5db5b, 0x9721306c,
                0xb4e4bdce, 0xc2372a7,  0xd06909b0, 0xd328bdd,  0xd4043bd8, 0xd5353d6c, 0xd919f9ad,
                0xdb9e4449, 0xef343a2d, 0xf6cae852,
// -- seed.bitcoinstats.com --
                0x1012cf36, 0x19be4fd9, 0x1f484f2d, 0x2204446f, 0x3044c6de, 0x4342d35f, 0x465d8149,
                0x678bb623, 0x7212da68, 0x7b44f351, 0x919a80b2, 0x933d0d9d, 0x94d463c0, 0x95bc592f,
                0x96eae722, 0xa2fa3532, 0xa43d0d9d, 0xc93a8354, 0xd036e412, 0xd180086f, 0xd1a27cb2,
                0xd4043bd8, 0xdae99753, 0xdd76bb25, 0xef9e4bda,
// -- seed.bitnodes.io --
                0x1085ef91, 0x1cabb333, 0x21aab3a7, 0x22eb69db, 0x290309b9, 0x2ba1caa0, 0x3e1fffc2,
                0x4030a07a, 0x42a3e057, 0x435b3a88, 0x4d2f36b2, 0x52c432c4, 0x62826c4d, 0x63403025,
                0x665aed59, 0x6c50e0b9, 0x73ac16b9, 0x7d349655, 0xaccf5dd5, 0xb4707bce, 0xea9fd012,
                0xedf2652e, 0xf5a0ca8f, 0xf8152ec9, 0xf8f88448,
// -- seed.bitcoin.jonasschnelli.ch --
                0x192dbc12, 0x1a8e71bd, 0x1ee16826, 0x205ee65f, 0x223a2c49, 0x2e6ea86c, 0x3bd94e34,
                0x42a0f8b9, 0x500fa4d3, 0x544add5,  0x70409837, 0x71bd6358, 0x75b8bd46, 0x80508a37,
                0x8a2f40d9, 0x90242b22, 0xa47dee50, 0xbeb2a936, 0xcabd7eaf, 0xce8b284b, 0xe37c2,
                0xe6dbc323, 0xf02d41d3, 0xf8566bb4,
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
