# sustocsv
企画開発部の春みんげ〜2023で制作している音ゲー用の譜面を、[PaletteWorks Editor](https://paletteworks.mkpo.li)を使って制作できるようにするためにsusファイルをcsvファイルに変換できるものを作ります。

勉強がてらJavaで書いてみました。

# 使い方
まず [**sustocsv.class**](https://github.com/UoA-Naoki/sustocsv/blob/main/sustocsv.class?raw=true) をダウンロードします。

**java sustocsv** コマンドを実行します。

「読み込むファイル名: 」の後にsusファイルのパスを書いてエンターを押します。

「書き出すファイル名: 」の後にcsvファイルのパスを書いてエンターを押します。(ファイルがなければ新しく生成されて、すでにある場合は上書きされます。)

以上

### 備考

Javaのバージョンは

openjdk version "17.0.7" 2023-04-18 LTS

OpenJDK Runtime Environment Zulu17.42+19-CA (build 17.0.7+7-LTS)

OpenJDK 64-Bit Server VM Zulu17.42+19-CA (build 17.0.7+7-LTS, mixed mode, sharing)

で動作確認をしています。バージョンの確認は **java -version** コマンドでできます。

違うバージョンでも動くかもしれませんが、もし動かなかったら必要なバージョンは[ここから](https://www.azul.com/downloads)ダウンロードできます。
