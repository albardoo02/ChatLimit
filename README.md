# ChatLimit
![word](https://user-images.githubusercontent.com/86724649/209516133-a5506756-2395-41cd-b9c1-edbc101912fd.png)

## 概要
Configで設定したNGワードを発言した際、プレイヤーをキックします

## 導入
1：Releasesから最新のjarファイルをダウンロード

2：サーバーのpluginsフォルダに入れる

3：Plugmanを利用してロードするか、サーバーを再起動する。

4：ChatLimitフォルダ内にあるword.ymlでNGワード、コマンドを設定する

5：`/chatlimit reload`でConfigを再読み込みするか、サーバーを再起動する。

## コマンド
`/chatlimit help`: ChatLimitのヘルプ

`/chatlimit reload`: Configの再読み込み

`/chatlimit version`: バージョンの表示

エイリアス: `/chat , /cl , /limit , /climit , /chatl`

## 権限 / Permission
### 全般
- `ChatLimit.*`: ChatLimitの全権限

### チャット制限
- `ChatLimit.chat.bypass`: チャットの制限がなくなります
  
### コマンド制限
- `ChatLimit.command.bypass`: コマンドの制限がなくなります

### コマンド権限
- `ChatLimit.command.*`: ChatLimitコマンドの全権限

- `ChatLimit.command.help`: helpコマンドの権限

- `ChatLimit.command.reload`: reloadコマンドの権限

- `ChatLimit.command.version`: versionコマンドの権限

## ライセンス / License
[MIT License](LICENSE)
