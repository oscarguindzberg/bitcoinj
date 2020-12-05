/*
 * Copyright 2011 Google Inc.
 * Copyright 2014 Andreas Schildbach
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

package org.bitcoinj.core;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.bitcoinj.core.listeners.*;
import org.bitcoinj.net.discovery.PeerDiscovery;
import org.bitcoinj.net.discovery.PeerDiscoveryException;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptPattern;
import org.bitcoinj.testing.FakeTxBuilder;
import org.bitcoinj.testing.InboundMessageQueuer;
import org.bitcoinj.testing.TestWithPeerGroup;
import org.bitcoinj.utils.Threading;
import org.bitcoinj.wallet.KeyChainGroup;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.bitcoinj.core.Coin.COIN;
import static org.bitcoinj.core.Coin.valueOf;
import static org.junit.Assert.*;


public class PeerGroupBloomFilterTest extends TestWithPeerGroup {

    public PeerGroupBloomFilterTest() {
        super(ClientType.BLOCKING_CLIENT_MANAGER);
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    @After
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void testFilterUpdateP2PKH() throws Exception {
        testFilterUpdate(Script.ScriptType.P2PKH);
    }

    @Test
    public void testFilterUpdateP2WPKH() throws Exception {
        testFilterUpdate(Script.ScriptType.P2WPKH);
    }

    private void testFilterUpdate(Script.ScriptType scriptType) throws Exception {
        peerGroup.start();
        InboundMessageQueuer p1 = connectPeer(1);

        NetworkParameters params = peerGroup.params;

        Wallet w = new Wallet(params, KeyChainGroup.builder(params).fromRandom(scriptType).lookaheadSize(2).build());
        peerGroup.addWallet(w);

        // Fund the wallet with 1 btc
        Address receiveAddress = w.freshReceiveAddress();
        Transaction funding = new Transaction(params);
        funding.addOutput(Coin.COIN, receiveAddress);
        funding.addInput(Sha256Hash.ZERO_HASH, 0, ScriptBuilder.createEmpty());
        w.receivePending(funding, new ArrayList<Transaction>());

        BloomFilter f1 = p1.lastReceivedFilter;

        // Send 9 txs
        List<byte[]> changeHashes = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            SendRequest sr = SendRequest.to(Address.fromString(params, "2N2i8mhk3SxeKg8Tx6KrqdzmMaKvgZFvA9w"), Coin.CENT);
            sr.shuffleOutputs = false;
            sr.allowUnconfirmed();
            Wallet.SendResult sendResult = w.sendCoins(peerGroup, sr);
            Script output1ScriptPubKey = sendResult.tx.getOutput(1).getScriptPubKey();
            if (ScriptPattern.isP2PKH(output1ScriptPubKey)) {
                changeHashes.add(ScriptPattern.extractHashFromP2PKH(output1ScriptPubKey));
            } else if (ScriptPattern.isP2WH(output1ScriptPubKey)) {
                changeHashes.add(ScriptPattern.extractHashFromP2WH(output1ScriptPubKey));
            } else {
                throw new IllegalStateException("script type should be p2pkh or p2wpkh");
            }
            Thread.sleep(100);
        }
        BloomFilter f2 = p1.lastReceivedFilter;

        // Check filter was updated
        assertNotEquals(f1, f2);

        // Change change addresses are included in the filter
        for (byte[] changeHash : changeHashes) {
            assertTrue(f2.contains(changeHash));
        }


    }
}
