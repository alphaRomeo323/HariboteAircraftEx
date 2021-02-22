HariboteAirCraft (はりぼてエアクラフト)
====================

## 概要

ブロックをはりぼて化して動かすことのできる「羅針盤ブロック」を追加します。
はりぼてはキー操作で操縦することができます。飛行船や飛行機などを作るのにちょうどいいかな？


## 前提

- Minecraft 1.7.2 以降
- MinecraftForge 1.7.2-10.12.1.1090 以降


## 導入

mods に zip のまま放り込んでください。
導入前にバックアップを取るのも忘れずに。".minecraft" 自体をバックアップするのがお手軽です。


## 利用条件

この MOD は Apache License(ver2.0) の下で配布されます。

    http://www.apache.org/licenses/LICENSE-2.0

- この MOD を使用したことにより発生したいかなる結果についても、製作者は一切の責任を負いません。
- この MOD は変更の有無にかかわらず再頒布が可能です。Apache License を確認してください。

この MOD または派生成果物は、それが minecraft を前提としている場合に、
minecraft 自体の利用条件に縛られることに注意してください。
利用条件の詳細は minecraft の利用規約を確認してください。


## レシピ

### 羅針盤ブロック　←　黒曜石３個、ダイヤ２個、金ブロック１個、コンパス１個

    　●　
    ◇□◇
    ■■■


## 使い方

- 羅針盤ブロックを設置し、右クリックするとON/OFFされます。
- 移動はテンキーで行います。マインクラフトの設定画面より他のキーを割り当てることもできます。
- 起動するとモブは強制的に座ります。足元を右クリックすることで起立/着席できます。


## Tips

- 移動するときは座りましょう。座らないと振り落とされます。
- 飛行船は地面などに当たると一旦止まります。
- 飛行船は一定時間入力がないと、その場で止まります。その時間はConfig[moveKeepTime]から設定できます。(デッドマン装置)
- 移動中のブロックは見た目だけです。ブロックとしての機能はありません。
- 一部の非対応ブロックは、移動中の見た目が羊毛ブロックになります。羅針盤をOFFにすると元のブロックに戻ります。
- 強制停止するときには、はりぼて終了キーを押してください。


## 操作説明(初期値)

    はりぼて前進    = テンキー1    (MaxSpeed = 4)
    はりぼて後退    = テンキー3    (MaxSpeed = 1)
    はりぼて上昇    = テンキー8    (MaxSpeed = 4)
    はりぼて下降    = テンキー2    (MaxSpeed = 4)
    はりぼて右旋回  = テンキー9    (MaxSpeed = 4)
    はりぼて左旋回  = テンキー7    (MaxSpeed = 4)
    はりぼて右移動  = テンキー6    (MaxSpeed = 3)
    はりぼて左移動  = テンキー4    (MaxSpeed = 3)
    はりぼて停止    = テンキー5    (停止中に停止するとブロックグリッドに整列)
    はりぼて終了    = テンキー/    (その場で強制的に再ブロック化)
    カメラ距離変更  = テンキー*    (おまけ機能：第三者視点の時のカメラ距離を変更)

キーは押し続けたり連打しなくても、１回押すだけでその方向に動き出します。
キーを重ねて入力するとその方向へスピードアップします。逆方向のキーでスピードダウンします。


## Config (mod_HariboteAirCraft.txt)

    blockLimit=2000                         # 一度に動かせる表面ブロックの最大数
    craftBodySize=-1                        # 飛行船の当たり判定の大きさ(-1で見た目通り)
    moveKeepTime=60                         # 無入力時に移動し続ける時間
    creativeOnly=false                      # true にすると、クリエイティブモードのプレイヤーだけが羅針盤を起動できるようになります

    blockTarget=                            # 移動可能なブロックID(指定の無いときにはデフォルトセットが使用されます)
    blockAppend=                            # 追加で移動可能なブロックID(blockTargetに追加で指定します)
    blockIgnore=2, 3, 8, 9, 10, 11, 12, 13, 31, 32, 37, 38, 78, 87, 121   # 移動されないブロックID(初期値としてワールドの表層ブロックを指定しています)
    renderIgnore=                           # 描画を無視するブロックID(描画に問題のあるブロックIDを指定すると、強制的に羊毛ブロックで描画するようになります)


## Copyright 2013 Yamato

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


## History

- 172v3: HariboteAirCraft.7.2 を 1.7.10 でも動くように修正。パケットがメモリリークする事象を修正。
- 172v2: CraftBukkit + MCPCplus 環境下において通信エラーとなる不具合を修正。
- 172v1: Minecraft1.7.2 に対応。1.7.2から追加されたブロックを動かせるようになりました。
- 164v2: 圧縮板/ボタン/ワイヤーフックが動作中にはりぼて化したとき、復帰後もオンのままになっていたのを修正。
- 164v1: Minecraft1.6.4 に対応。MinecraftForge 向けの MOD に変更しました。
- 162v3: マルチ向けの設定を追加。羅針盤を上付きできるように変更。カメラ距離変更できるように変更。看板などを動かせるようになりました。
- 162v2: マルチサーバ向け暫定対応。ブロック取得処理を内部変更。ブロック取得に関するバグが幾つかなくなりました。設定ファイルを独自形式へ変更。
- 162v1: Minecraft1.6.2 に対応。
- 161v1: Minecraft1.6.1 に対応。1.6.1から追加されたブロックを動かせるようになりました。
- 152v2: 移動スピードなど調整しました。緊急停止ボタンを付けました。ピストン、ビーコン、醸造台などが動かせるようになりました。
- 152v1: Minecraft1.5.2 に対応。一部の非対応ブロックを羊毛として描写する仕様にしました。
- 151v2: 描画系の処理を見直し、軽量化を図りました。チェスト、かまどなどが動かせるようになりました。Java6 で動かない部分を修正しました。
- 151v1: Minecraft1.5.1 に対応。1.5.1から追加されたブロックを一部動かせるようになりました。
- 147v2: 旋回に伴ってプレイヤーも方向を変えるようになりました。ドア、ベッド、植木鉢などが動かせるようになりました。
- 147v1: 初版


## Appendix

- DefaultMoveableSet

        1       stone                   石
        2       grass                   草
        3       dirt                    土
        4       cobblestone             丸石
        5       planks                  木材
        6       sapling                 木の苗
        8       flowing_water           水
        9       water                   水
        10      flowing_lava            溶岩
        11      lava                    溶岩
        12      sand                    砂
        13      gravel                  砂利
        14      gold_ore                金鉱石
        15      iron_ore                鉄鉱石
        16      coal_ore                石炭
        17      log                     原木(樫・白樺・松・ジャングルツリー)
        18      leaves                  木の葉(樫・白樺・松・ジャングルツリー)
        19      sponge                  スポンジ
        20      glass                   ガラス
        21      lapis_ore               ラピスラズリ鉱石
        22      lapis_block             ラピスラズリブロック
        23      dispenser               ディスペンサー
        24      sandstone               砂岩
        25      noteblock               音符ブロック
        26      bed                     ベッド
        27      golden_rail             パワードレール
        28      detector_rail           ディテクターレール
        29      sticky_piston           粘着ピストン
        30      web                     クモの巣
        31      tallgrass               背の高い草
        32      deadbush                枯れ木
        33      piston                  ピストン
        34      piston_head             ピストンアーム
        35      wool                    羊毛
        37      yellow_flower           花
        38      red_flower              バラ
        39      brown_mushroom          茶きのこ
        40      red_mushroom            赤きのこ
        41      gold_block              金ブロック
        42      iron_block              鉄ブロック
        43      double_stone_slab       石ハーフブロック(２段重ね)
        44      stone_slab              石ハーフブロック
        45      brick_block             レンガブロック
        46      tnt                     TNT
        47      bookshelf               本棚
        48      mossy_cobblestone       苔むした丸石
        49      obsidian                黒曜石
        50      torch                   たいまつ
        51      fire                    火
        52      mob_spawner             モンスタースポナー
        53      oak_stairs              木の階段
        54      chest                   チェスト
        55      redstone_wire           レッドストーンワイヤー
        56      diamond_ore             ダイヤ鉱石
        57      diamond_block           ダイヤブロック
        58      crafting_table          作業台
        59      wheat                   小麦
        60      farmland                農地
        61      furnace                 かまど
        62      lit_furnace             燃えているかまど
        63      standing_sign           立て看板
        64      wooden_door             木製のドア
        65      ladder                  はしご
        66      rail                    レール
        67      stone_stairs            丸石の階段
        68      wall_sign               壁看板
        69      lever                   レバー
        70      stone_pressure_plate    石の感圧板
        71      iron_door               鉄製のドア
        72      wooden_pressure_plate   木の感圧板
        73      redstone_ore            レッドストーン鉱石
        74      lit_redstone_ore        レッドストーン鉱石
        75      unlit_redstone_torch    レッドストーントーチ
        76      redstone_torch          レッドストーントーチ
        77      stone_button            石ボタン
        78      snow_layer              雪
        79      ice                     氷
        80      snow                    雪ブロック
        81      cactus                  サボテン
        82      clay                    粘土ブロック
        83      reeds                   サトウキビ
        84      jukebox                 ジュークボックス
        85      fence                   フェンス
        86      pumpkin                 かぼちゃ
        87      netherrack              ネザーラック
        88      soul_sand               ソウルサンド
        89      glowstone               グロウストーン
        90      portal                  ポータル
        91      lit_pumpkin             かぼちゃランタン
        92      cake                    ケーキ
        93      unpowered_repeater      レッドストーンリピーター
        94      powered_repeater        レッドストーンリピーター
        95      stained_glass           色付きガラス
        96      trapdoor                トラップドア
        97      monster_egg             石(シルバーフィッシュ)
        98      stonebrick              石レンガブロック
        99      brown_mushroom_block    赤キノコブロック
        100     red_mushroom_block      茶キノコブロック
        101     iron_bars               鉄格子
        102     glass_pane              板ガラス
        103     melon_block             スイカ
        104     pumpkin_stem            かぼちゃの苗
        105     melon_stem              スイカの苗
        106     vine                    ツタ
        107     fence_gate              フェンスゲート
        108     brick_stairs            レンガの階段
        109     stone_brick_stairs      石レンガの階段
        110     mycelium                菌糸ブロック
        111     waterlily               蓮の葉
        112     nether_brick            ネザーレンガブロック
        113     nether_brick_fence      ネザーレンガフェンス
        114     nether_brick_stairs     ネザーレンガの階段
        115     nether_wart             ネザーいぼ
        116     enchanting_table        エンチャント台
        117     brewing_stand           醸造台
        118     cauldron                大釜
        119     end_portal              エンドポータル
        120     end_portal_frame        エンドポータルフレーム
        121     end_stone               エンドストーン
        122     dragon_egg              ドラゴンエッグ
        123     redstone_lamp           レッドストーンランプ
        124     lit_redstone_lamp       レッドストーンランプ
        125     double_wooden_slab      木ハーフブロック(２段重ね)
        126     wooden_slab             木ハーフブロック
        127     cocoa                   カカオの実
        128     sandstone_stairs        砂岩の階段
        129     emerald_ore             エメラルド鉱石
        130     ender_chest             エンダーチェスト
        131     tripwire_hook           トリップワイヤーフック
        132     tripwire                トリップワイヤー
        133     emerald_block           エメラルドブロック
        134     spruce_stairs           木の階段(松)
        135     birch_stairs            木の階段(白樺)
        136     jungle_stairs           木の階段(ジャングル)
        137     command_block           コマンドブロック
        138     beacon                  ビーコン
        139     cobblestone_wall        丸石フェンス
        140     flower_pot              植木鉢
        141     carrots                 ニンジン
        142     potatoes                じゃがいも
        143     wooden_button           木ボタン
        144     skull                   頭
        145     anvil                   金床
        146     trapped_chest           トラップチェスト
        147     light_weighted_pressure_plate   金の感圧板
        148     heavy_weighted_pressure_plate   鉄の感圧板
        149     unpowered_comparator    レッドストーンコンパレータ
        150     powered_comparator      レッドストーンコンパレータ
        151     daylight_detector       太陽光センサー
        152     redstone_block          レッドストーンブロック
        153     quartz_ore              ネザー水晶鉱石
        154     hopper                  ホッパー
        155     quartz_block            ネザー水晶ブロック
        156     quartz_stairs           水晶の階段
        157     activator_rail          作動レール
        158     dropper                 ドロッパー
        159     stained_hardened_clay   色付き粘土
        160     stained_glass_pane      色付き板ガラス
        161     leaves2                 木の葉(アカシア・ダークオーク)
        162     log2                    原木(アカシア・ダークオーク)
        163     acacia_stairs           アカシアの階段
        164     dark_oak_stairs         ダークオークの階段
        170     hay_block               干し草ブロック
        171     carpet                  カーペット
        172     hardened_clay           堅焼き粘土
        173     coal_block              石炭ブロック
        174     packed_ice              氷解ブロック
        175     double_plant            草花(２段重ね)
