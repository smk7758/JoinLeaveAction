# JoinLeaveAction
========================================
プラグイン名:	JoinLeaveAction
動作確認Ver:	Spigot-1.8.8
		(それ以外でも恐らく使用可)
作成:		smk7758
========================================


	###概要###
JoinLeaveActionは、
・スポーンポイントを設定し、その場所にTPする
・メッセージを出す
事を参加/退出時に行うプラグインです。

	###コマンド###
joinleaveaction	|	|特になんでもないコマンド。
(短縮:jla)	|	|上短縮です。
		|help	|コマンドリストを表示しますが、上記と同じです。
		|reload	|config.ymlをリロードします。
spawn		|	|スポーンポイントにTPします。(予備機能)
spawn [player]	|	|[player]に([]は要りません)プレーヤー名を入れることで、相手をスポーンポイントにTPします。
setspawn		|	|スポーンポイントを設定します。
		|	|なお取得した値は、config.yml内に即座に保存されます。
		|	|取得する値は、プレーヤーのx,y,z座標、Yaw,Pitchの値を取得します。

joinleaveaction/jla	|test	|JoinMsg	|参加時に出るメッセージのテストをします。
		|	|LeaveMsg |退出時に出るメッセージのテストをします。
		|	|Inv	|参加時に行うインベントリのテストをします。

	###config.yml###
JoinSpawn: true			|ログイン時にスポーンポイントにTPするか
SpawnTPedMsg_me: 'You tped to SpawnPoint'		|/spawnした後に表示するメッセージ。無効化は何も書かなければいい。
SpawnTPedMsg_other: 'You tped to SpawnPoint by %Other%'	|/spawn <Player>した後に表示するメッセージ。%Other%は実行した人。
						|無効化は何も書かなければいい。
Loc:				|スポーンポイントの座標設定の項目
  World: ''			|  ワールド名
  x: '0.0'			|  x座標
  y: '0.0'			|  y座標
  z: '0.0'			|  z座標
  Yaw: '0.0'			|  Yaw=角度
  Pitch: '0.0'			|  Pitch=高さ
#######################################	|
JoinInv: true			|参加時のインベントリをするか。
Inv:				|インベントリの設定:
  1:				|  1スロット目の設定:
    Item: BOOK			|    アイテム:
    ItemStackNumber: 1		|    アイテムの個数:
    ItemName: TEST			|    アイテムの名前:
    ItemLore:			|    アイテムのLore(List):
      - 'test'			|    - 'Lore'
      - 'abcd'			|    - 'Lore'
  2:				|  2スロット目の設定:
    Item: APPLE			|(以下略)
    ItemStackNumber: 55		|
    ItemName: Apple			|
    ItemLore:			|
      - 'RINGO'			|
      - 'abcd'			|
  3:				|
  4:				|
  5:				|
  6:				|
  7:				|
  8:				|
  9:				|
Inv_null_erro: false		|設定されていないスロットがある時にnullのエラーを出すか。
LeaveInv_clear: true		|退出時にインベントリをクリアするか。
#######################################	|
JoinMessage-true_or_false: true	|参加時に出すメッセージをするか
JoinMessage:			|参加時に出すメッセージ[List]
- '&a%Player% joind.'		|- 'メッセージ'
JoinMessage_user:			|指定したuserの参加時に出すメッセージ
  admin:				|  プレーヤー名:
  - '&aServer admin joind.'		|  - 'メッセージ'
JoinMessage_tell-true_or_false: true	|参加した人のみに出すメッセージをするか。
JoinMessage_tell:			|参加した人のみに出すメッセージ:
- '&bWelcome to A Minecraft Server!'	|- 'メッセージ'
JoinMessage_to_came_user: true	|参加時に出すメッセージを来た人にも表示するか。
#######################################	|
LeaveMessage-true_or_false: true	|退出時に出すメッセージをするか
LeaveMessage:			|退出時に出すメッセージ[List]
- '&b%Player% leaved.'		|- 'メッセージ'
LeaveMessage_user:			|指定したuserの退出時に出すメッセージ
  admin:				|  プレーヤー名:
  - '&aServer admin leaved.'		|  - 'メッセージ'
LeaveMessage_to_came_user: false	|(機能的には参加時と同じですが)出て行ったら見えないのでコマンド実行時(test)用です。
#######################################	|
Debug-mode: false			|デバッグモード(座標とかが見れる)をするか

※メッセージについて
%Player%をプレーヤー名に置き換えます。
カラーコードを使うには'&'を使用することで、可能です。

	###パーミッションノード###
plugin.ymlのやつを載せておくので、察して下さい。(自分でもよくわかってません)
============================================================
permissions:
   JoinLeaveAction.*:
       description: Gives the user access to all command.
       children:
           JoinLeaveAction.cmd: true
       description: Allows the JoinLeaveAction Plugin use.
       default: false
   JoinLeaveAction.cmd.*:
       description: Allows the JoinLeaveAction's commands.
       default: false
       children:
           JoinLeaveAction.cmd.setspawn: true
           JoinLeaveAction.cmd.spawn.me: true
           JoinLeaveAction.cmd.spawn.other: true
           JoinLeaveAction.cmd.reload: true
           JoinLeaveAction.cmd.test: true
   JoinLeaveAction.cmd.setspawn:
       description: Allows the /setspawn command.
       default: false
   JoinLeaveAction.cmd.spawn:
       description: Allows the /spawn [player] command.
       default: false
       children:
           JoinLeaveAction.cmd.spawn.me: true
           JoinLeaveAction.cmd.spawn.other: true
   JoinLeaveAction.cmd.spawn.me:
       description: Allows the /spawn command.
       default: false
   JoinLeaveAction.cmd.spawn.other:
       description: Allows the /spawn [player] command.
       default: false
   JoinLeaveAction.cmd.reload:
       description: Allows the reload.
       default: false
   JoinLeaveAction.cmd.test:
       description: Allows the test.
       default: false
============================================================


	###使用方法(設定)###
0.Bukkitサーバー(例:Spigot)を建てておいて下さい。
1.プラグインを導入します。(pluginsフォルダに入れるだけで良い)
2.サーバーを起動し、ログインして下さい。
3./setspawnでスポーンポイントを設定して下さい。
→これで設定終了です。
4./spawnでスポーンポイントに移動し、/spawn [player]で[player]に
プレーヤー名([]は除く)を入れることで、相手をスポーンポイントに移動します。


	###その他###
srcはソースです。(なおクソース)
ちなみに完コピ/二次配布はダメです。

何か問題があればsmk7758に連絡をください。
連絡先:
  Twitter: @smk7758
