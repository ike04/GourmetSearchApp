# GourmetSearchApp
HotPepperGourmetのAPIを利用したレストラン検索アプリ

# 概要

HotpepperグルメのAPI(https://webservice.recruit.co.jp/doc/hotpepper/reference.html )から現在地周辺のお店を取得し、リスト一覧と地図上にマッピングするアプリを作成しました。

# 機能一覧

- リスト一覧
   - APIから取得した店舗のデータをグリット形式で表示する

- 地図
   - APIから店舗の緯度・経度を取得し、その場所の地図上にマーカーを立て表示する
   - マーカーのタップとViewPagerのスクロールが連動するように実装
   - 検索フィルターダイアログ機能の実装

- WebView
   - フローティングアクションボタンを配置し、ここからお気に入り登録できるように実装
   
|onboarding|  list  | 
| ---- | ---- | 
|  <img src="https://user-images.githubusercontent.com/48178913/133264852-e4c43c12-0e42-4b45-985f-9194d6666c26.gif" >  | <img src="https://user-images.githubusercontent.com/48178913/133265331-79050c02-0b7e-442e-89fa-fa5afb9aab0d.gif">  | 
|  map  |  search filter  |
| ---- | ---- |
|  <img src="https://user-images.githubusercontent.com/48178913/133267236-a7ff233c-390d-4fc0-99ec-a3369c07d760.mp4" >  | <img src="https://user-images.githubusercontent.com/48178913/133266962-3040a686-6cfa-4181-ad64-acd4378b90c5.mp4">  |

|エラーハンドリング|
| ---- |
|<img src="https://user-images.githubusercontent.com/48178913/133268269-8a326fcb-1a8f-444a-b025-b68055667ab8.jpg" width=50%>|

# 設計
このアプリはRx、DataBinding、Daggerを利用したClean Architectureを使用しています。

# 使用技術
- Kotlin
- Room
- Hilt
- RxKotlin
- RxJava
- RxAndroid
- GoogleMap API
- retrofit
- OkHttp
- Gson
- Glide
- Groupie
- Lottie

# ToDo
- お気に入り画面の実装
- スプラッシュ画面の実装
