[![CI](https://github.com/ike04/GourmetSearchApp/actions/workflows/ci_settings.yml/badge.svg?branch=develop)](https://github.com/ike04/GourmetSearchApp/actions/workflows/ci_settings.yml)
[![codecov](https://codecov.io/gh/ike04/GourmetSearchApp/branch/develop/graph/badge.svg?token=ZBFOQGC248)](https://codecov.io/gh/ike04/GourmetSearchApp)
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
|  <img src="https://user-images.githubusercontent.com/48178913/136657217-48525c2b-f5b3-4822-b24f-4658df061739.gif" >  | <img src="https://user-images.githubusercontent.com/48178913/147894907-31c7db5c-372a-4a8b-8bf8-a27b0685d3d2.gif">  | 
|  map  |  search filter  |
| ---- | ---- |
|  <img src="https://user-images.githubusercontent.com/48178913/133267236-a7ff233c-390d-4fc0-99ec-a3369c07d760.mp4" >  | <img src="https://user-images.githubusercontent.com/48178913/133266962-3040a686-6cfa-4181-ad64-acd4378b90c5.mp4">  |

| settings |
| ---- |
|<img src="https://user-images.githubusercontent.com/48178913/136657405-24bb0f9b-178c-46f5-959e-f37217686b26.gif">|

エラーハンドリング

|  Permission  |  offline  |
| ---- | ---- |
|  <img src="https://user-images.githubusercontent.com/48178913/147894958-edafcbb8-f869-4c9c-9e45-660280df1c6e.gif" >  | <img src="https://user-images.githubusercontent.com/48178913/147895077-745f6d29-b50b-4e75-9c40-a9a13efc30e0.gif">  |

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
- Firebase Crashlytics

# ダウンロード（Androidのみ)
<img src="https://user-images.githubusercontent.com/48178913/137406829-947c0b8b-ecce-488a-830a-6cd8e2e5ab69.png">

# 
# 
