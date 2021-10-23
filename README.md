# GourmetSearchApp
HotPepperGourmetのAPIを利用したレストラン検索アプリ

# 概要

HotpepperグルメのAPI(https://webservice.recruit.co.jp/doc/hotpepper/reference.html )から現在地周辺のお店を取得し、リスト一覧と地図上にマッピングするアプリを作成しました。

# 機能一覧

- リスト一覧
   - APIから取得した店舗のデータをグリット形式で表示する
   - ローカルに保存したお気に入りレストランを表示する

- 地図
   - APIから店舗の緯度・経度を取得し、その場所の地図上にマーカーを立て表示する
   - マーカーのタップとViewPagerのスクロールが連動するように実装
   - 検索フィルターダイアログ機能の実装

- WebView
   - フローティングアクションボタンを配置し、ここからお気に入り登録できるように実装

- 設定画面
   - アプリの設定を開く
   - OSSライセンスの表示
   
|onboarding|  list  | 
| ---- | ---- | 
|  <img src="https://user-images.githubusercontent.com/48178913/136657217-48525c2b-f5b3-4822-b24f-4658df061739.gif" >  | <img src="https://user-images.githubusercontent.com/48178913/136657308-bb54e688-44e6-4f32-ae1d-130099b1cb23.gif">  | 
|  map  |  search filter  |
| ---- | ---- |
|  <img src="https://user-images.githubusercontent.com/48178913/133267236-a7ff233c-390d-4fc0-99ec-a3369c07d760.mp4" >  | <img src="https://user-images.githubusercontent.com/48178913/133266962-3040a686-6cfa-4181-ad64-acd4378b90c5.mp4">  |

| settings |
| ---- |
|<img src="https://user-images.githubusercontent.com/48178913/136657405-24bb0f9b-178c-46f5-959e-f37217686b26.gif">|

エラーハンドリング

<img src="https://user-images.githubusercontent.com/48178913/133268269-8a326fcb-1a8f-444a-b025-b68055667ab8.jpg" width= 50%>

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
- mockito
- jacoco

# ダウンロード（Androidのみ)
<img src="https://user-images.githubusercontent.com/48178913/137406829-947c0b8b-ecce-488a-830a-6cd8e2e5ab69.png">

# 
# 
