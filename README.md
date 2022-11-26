# ChatLimit
## 概要
Configで設定したNGワードを発言した際、プレイヤーをキックします

## 導入
1：Releasesから最新のjarファイルをダウンロード

2：サーバーのpluginsフォルダに入れる

3：Plugmanを利用してロードするか、サーバーを再起動する。

4：ChatLimitフォルダ内にあるmessage.ymlでNGワードを設定する

5：Plugmanを利用してリロードするか、サーバーを再起動する。



## 権限 / Permission
- `chatlimit.*`: ChatLimitの全権限

- `chatlimit.chat.bypass`: チャットの制限がなくなります
  
- `chatlimit.command.bypass`: コマンドの制限がなくなります(ほぼ未実装)

## ライセンス / License
[MIT License](LICENSE)
