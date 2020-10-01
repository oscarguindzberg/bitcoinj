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

import org.bitcoinj.core.BitcoinSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.params.RegTestParams;
import org.bouncycastle.util.encoders.Hex;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TxSizePlayground {

    public static void main(String[] args) {

        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.WARNING);

        printTx("legacy maker fee tx", "0100000001dd6bef402c18a8fdf4c10ad575e04c99c33be430d13084fd703ea5adf206f2fe010000006b483045022100af452f9c8c99af0862a0ce443cf845ea5116cf9f8c74d46298847b35a6be98b402203db320404dfa30f49a92bb20a3e00f6f254a5430217ac94598396bc07de4ec91012102fa4f401f74fb6cf2dc7d696e0499676999550b73f01d2075721209b6a291dda8ffffffff03a08601000000000017a9144c0e4893237f85479f489b32c8ff0faf3ee2e1c987c0c2da06000000001976a9145afc1e8a4629cb96d222d692cf77a6fb653c9ca888acd80cbe34000000001976a9148208d48d166cf44d985c0ef365d31b5b3ab0a31a88ac00000000");
        printTx("segwit maker fee tx", "0100000000010113c4ebc08443dc8df89b75d72e2d09a0dea30397b6e7413ee497ccf3ebab87390000000000ffffffff03307500000000000017a9144c0e4893237f85479f489b32c8ff0faf3ee2e1c987a06d0e0200000000160014e4a3e361b3cfc40ae8dd185cbf6197a8923629394ff4233400000000160014b2bc0f94252e3bb9ec24578a845eb696ad2974760247304402205749a965126477df6c69b709e3795452c65521bb0c7b5c6ff0ec1d50384b197702203ffa819f9c4fb228ca822a0277bc5365b201ab89ad1eda8284929c2164ed1f8e012102e0284cdeae6a8c971e2ea5004ebf9196ee9b3037d6f1ed039c4b5672a69cddc600000000");

        printTx("legacy taker fee tx", "0100000001dd6bef402c18a8fdf4c10ad575e04c99c33be430d13084fd703ea5adf206f2fe020000006b483045022100da5a81752f309c68906bf14fb98fc2a6f268b10130661c896722af8105d221a2022050d79c933e2d767dfcfaadc93e413d33f4ec23469e802fa80628adc003f179690121034e9f9a698de930e9d4e1ae2cb3f3cb6a5e6cd2d827e5419a4c719bfa45372f70ffffffff03e09304000000000017a9144c0e4893237f85479f489b32c8ff0faf3ee2e1c9877819e600000000001976a914fb9ca2c0aac34e0b8e3ce130a9ed59e9a121cfcb88accc80af3a000000001976a914172a622805273b4abbe20590e6dac304f0e1b9eb88ac00000000");
        printTx("segwit taker fee tx", "01000000000101e20143ffb3f227c3e22d77fe7298c1ee97ee69e773c25df0dbb6e9cffa279cb40000000000ffffffff03905f01000000000017a9144c0e4893237f85479f489b32c8ff0faf3ee2e1c98730c14500000000001600148776b3be38dae675e82205db4460cf58fe3e972363cc9f4000000000160014140594085fbd1eb1f02eabd5ea78ce1ec8e05f7f02483045022100cf6309942f0bce41139f73bd79bb07f5dd71134707e253f7a653d0baa663c82502204b6afce6ec6c0f28c990f3b01fe3c2ef6a97169eb175f71a59d7409ae483c5490121027f6da96ede171617ce79ec305a76871ecce21ad737517b667fc9735f2dc3423400000000");

        printTx("legacy deposit tx", "010000000214a57fdbf5dc64114fd487dd986ddf165998d7094ca285e28f7b78a8b1feb007010000006b4830450221009615e9c910d79f21d48882feedd408aea31ea8a4d92c5916718b93d7e238d4370220686676b827dcb23e74b289425c6e84fda7343771cb6525d3059c13673676a458012102f93455a788569f23e568c15089ecb952a357ae7a41e6ca49f66aa80583becca5ffffffff4c226ccd8cb4f992cc8184a15d3d090a617de22fdcd6bcc737f1bf6b9bb6e92b010000006b483045022100a7fe618710367a3ad1a7e34b326dc573389a414c39b26666da7601c15e30705002203caff8285b440d61c28f666223b0adf74a50bae7b3e332680e70d29147d2e724012103daf625cf04a8d7a2d3c7329db7a2d8ae9bce9d07e93cbd6bee1d1e20c67815eaffffffff025c40c0070000000017a9141f4e110fa5a5b97f15f9c5bd42b0ddf8c52f8751870000000000000000226a202f77dfd1e1f2f8f3fe582bdc9ce118725ec88afa26597f413891fae6ae2349a500000000");
        printTx("legacy deposit with change tx", "010000000286e7965ed10fc159050aefbf24ae873dccf8cd6af01282ff2866145bb57320b5010000006b483045022100c024f42631080b48ee882206a7f67838f80a1acf8f0ef99e3dffbc49eed183c802204d5ddb2ce3f51b7455d3f577799b9f9b2156e6c7a9e335eb1ed9ffda44f7d6570121035d970d75a7f19945e45727900212942216b57e19ca53286a87b8ca4876cd1ed0ffffffff9b20b190dd601d50a8db4cb774ac10a1b2b3d74b600cff12ad7c399bed06f298010000006a473044022061dd8e7e3e6fedb9b81afdfb980081d86a6b10d052c68a4101d936117f8976da022048753465b5c89963afcfa2ec675edd028cb34766aed3d5ca33f00159708e299d012103ae9bf197a367d52975946849d08d2577e214f5a6bc5e8766acbca466f0ab62d2ffffffff03d8c800090000000017a91452fdae57e13467e16fb935b075cf118ac31a7cb4870000000000000000226a2035c6053ee7ed089a8b345eb64f21ee4a376d3b24ab7cc49b455ff6c363f00291404b4c00000000001976a914f1f04e9353b90d48effebcd59b789f99c8e747bb88ac00000000");
        printTx("segwit deposit tx", "0100000000010281e84017cfd52f8881291784a6b061bbae4c858aa53cd32c539c3f22793eabd00100000000ffffffff5caf79a12340abc0528b2b9d1e5ef86000220581863ec0fb4ff6e9e8453281350100000000ffffffff0248a353020000000022002071bb78b6a3bebdd75fee53b1be10423d3ade4e0f4f9ba96050946a72417acf190000000000000000226a203646d7661e263cfe2e0083a539208152660b22d3dee5fd86edb72a35742d07b50247304402201062319dcec6856cceb4ebbce6ae35a41256883712f8e687139f964bfae287720220585668a7e820461be3c9aac280e749ad4e32711be03420f1ea99d12e57cd3eea012102e7312cdd480c7105cd736d4471a8c0afd873abe63c926ddeafaf45af7bf033ad0247304402201ba66a8af49c59d79a6e74df9b5da7ab99eaab09f4a924eda3611e3affcf760f0220185e72169de1f70925d87ac6e004b80f4bd9b285799bfc1771f17a3c249a1aec0121035344d4e56f1ceade2307a1462da7bd1e4147103b53a6700559f35e9c1dbf0ea500000000");
        printTx("segwit deposit with change tx", "01000000000102fa3226513e375eb2e2f8ce45aa66e8247ffbc4d5e7554a6608731e2014058f3d0100000000ffffffffedb232f8cf4a34b8f6b1f513808249d7831a8a586adefb8006c8131da6e91ba70100000000ffffffff03c881d404000000002200208e4cb9c06791e8e7b764b56040c8e58e5f825a15354a993776866efd5db46dbc0000000000000000226a20995930112c83c61ef01367d62dde872e4b92af9df4394954566f29b9c4eb35a08096980000000000160014a05ac7730330742492619ef0f2fb9ef90ea2e1be0247304402205fdb04f8377fb642e661936920ce57bc12caf24e3e0dfa986edc99fa7f7af397022064e33e163dcbd896b8fe97e15e13216a3b1483031c4bfe478d30f7f780407b8401210374c62392bcb9af73efa352a6e13f518665ce24076d652f3ec519fa6e7087843a024730440220704761edaaaded0bd0f16c9ecac25741069d2d402ecd5ef9e1d3b5237cff066f022003a9f04bc4ff88f5aa9eb2de59ec34efe0ef53b81c44eaec5759e5955762222c012103a19f7c4c78940eb1c80d0f047976d86ca0a8f54ba4d46baca6ff524dc1cc719400000000");

        printTx("legacy payout tx", "0100000001ccbb6bae92d68580ece1b9b92d53f5983b74e2329d2c93f10ed1c623bf81e18700000000da00483045022100819603dbef8c4c1b3c42d9efde00689c09e270c7c25746f0411905ac3dd3a44f02201e5c1bc193a37597790312518ca4ccfb230672845efc3e571f9bfdd2ceda1a960147304402204f701855d2120658a28917f7059ef58e64b9596cbcfb90d2cf1e8c38bd67feb702201ac951a785ebef87b07383cbb31b69f80b130a4ed941422011039788d933cbc80147522103ae9bf197a367d52975946849d08d2577e214f5a6bc5e8766acbca466f0ab62d2210347f0c2ce21d2f554ecdb2be27019155a438e6d92ef26fbc402de8d0c75e76e6e52aeffffffff02c0c2da06000000001976a91497145233b254db009f0dddb28bb47439679d47d288acc0e1e400000000001976a91469d5c89d9b3afd176076e40d610abab99b5ca55188ac00000000");
        printTx("segwit payout tx", "010000000001012070c413a5671f2119d3b91014d961660d8811b7e25b941850df7540a06300680000000000ffffffff02a06d0e0200000000160014d4faa8b3ce613df5596b25d8ffc46c1fb328844d20aa440000000000160014e63de535ac84e93fdcfe9e3f5ba4ae51785c66fa0400483045022100ab74c73c01e0b8c1ec409a96a2b932ec5f8d2179b7e23d31b85ca71a98ebabe9022046fb7c38b76486942adfd7932c45d791b46472ad4d52975965ef8c5674b0107401483045022100c6fbe80e2b8e47cbbcdd2db709c7789e27fcb5d17cda8277c0d35eb2aeb4f52a022018b304ec1474d5592a13ba37691ed97f843ea8c8dfe4fa60dd4cdf5e73dfc22501475221029be311398f7f5756554bac1ffaf5e13d6492d3f67e4680007a5891546fdbf9622103e69364c0510bcf81990604b4d8c0ea920eb7ed7e8480c77a0b6f5a6710aeb4ad52ae00000000");

        printTx("legacy delayed payout tx", "01000000013868b64f4e83f84fe64b70f39ea1ef4960ee981554f011f4ef7659b8aa63d0d400000000db00483045022100b2ef25787e19c6b982692d94a34b42fb4f66dae53a26ea03b3af3a3a3ca4815f022064df9216f27dc37939a6b03338f0f5ddceb9f5b912c8d1918db72c0fdcf8f76d01483045022100d280484b6c9e798af0f55ae418cbc18d06611ea3ac29d0a644ee11e099f23f9a02207cf4324d2e6999e5c307e95bcae98875e36258b1c0c064b04aff53d545a93d710147522102f5b28e1862dcd46c4cf9f27e0520d7b8a5ec49c32365b489a364cf1c1d2e0e0a21038d74489682096c71c3b2d6e805d83e34098f3f644d76f1693bdfe6509bfc8d3452aefeffffff0140bc120a0000000017a9144c0e4893237f85479f489b32c8ff0faf3ee2e1c98779000000");
        printTx("segwit delayed payout tx", "01000000000101f40d4101a6fd924ac94c7cbb089426c96918dc64e146564525ab9a45bed5071e0000000000feffffff018019d90a0000000017a9144c0e4893237f85479f489b32c8ff0faf3ee2e1c9870400483045022100a4f82fdd7df82349517cbfbe90f1bfef2ad4c74cd84eb843f983d47b15d0ee9b0220756e57bead58f7ce32226a77b8111f9dbd5d2c27746a207a775f174e8031773d0147304402201366ba32fd83c11baf6e72918a71abd261f27f793c044e960eda826c8c8a36510220730e84097b59a12e4fd1c4aae738643382518b6ab5009bf777a3235ffcb3c47e0147522103ad64b7e39d1ff8a12b4558dd1e0c90769c57ba6256179bf3ed35280af5a7317821027f6da96ede171617ce79ec305a76871ecce21ad737517b667fc9735f2dc3423452ae81000000");
    }

    private static void printTx(String txLabel, String txHex) {
        NetworkParameters params = RegTestParams.get();
        Transaction tx = new BitcoinSerializer(params, false).makeTransaction(Hex.decode(txHex));
        System.out.println(txLabel + " = " + tx);
        System.out.println(txLabel + " size = " + tx.getMessageSize());
        System.out.println(txLabel + " vsize = " + tx.getVsize());
        System.out.println("");
    }
}
