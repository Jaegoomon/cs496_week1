# CS496 Week 1 Report

## Abstraction

프로젝트의 결과물은 총 3개의 탭으로 구성된 앱이며 각 탭은 다음과 같은 기능을 가진다.

* Tab1: 전화번호부 연동앱으로 스마트폰에 있는 전화번호부 데이터를 가져와 리사이클러뷰를 이용해 뷰를 그려준다.
* Tab2: 갤러리 앱으로 Tab1과 마찬가지로 스마트폰 내의 갤러리 앱의 사진 URI를 불러와 리사이클러뷰를 이용해 뷰를 그려준다.
* Tab3: 웹 주소를 북마크하여 간단한 메모와 태그를 남길 수 있는 앱으로 구글 플레이스토어에 있는 ***[LinKeep](https://play.google.com/store/apps/details?id=com.francescopennella.linkeep)***의 기능을 가능한 비슷하게 구현하였다.

## Functions

각 탭을 구현하면서 가장 핵심이 되었던 기술 및 라이브러리에 대한 설명이다.

* [contentResolver](https://developer.android.com/guide/topics/providers/content-provider-basics?hl=ko)

  Tab1과 Tab2에서 필요한 데이터를 얻기위해 퍼미션을 얻은후 `contentResolver.query`를 이요해서 필요한 데이터를 데이터베이스와 유사한 테이블 형식으로 얻어올 수 있었다.

### Tab 2

* [Glide](https://github.com/bumptech/glide)

  ![glide_img](https://raw.githubusercontent.com/bumptech/glide/master/static/glide_logo.png)

  Glide를 사용한 이유는 리사이클러뷰에를 통해서 대규모 사진 데이터를 불러올 상황에서 리사이클러뷰의 스크롤에 딜레이가 발생하는 현상이 나타났다. 이를 막으면서도 이미지 파일의 형태를 최대한 보존할 수 있는 방법을 찾던 중에 glide 라이브러리를 사용하였다. 아래와 같이 이미지를 첨부할 수 있다.

  ```kotlin
  Glide
      .with(myFragment)
      .load(url)
      .centerCrop()
      .placeholder(R.drawable.loading_spinner)
      .into(myImageView);
  ```

  Glide를 사용하면서 가장 좋았던 점은 사진의 로딩으로 생기는 딜레이를 뷰가 만들어지자마자 보여주는 것이 아니라, 원하는 위치로 스크롤이 이동을 하며 원하는 위치에 도달하였을 때, 이미지 로딩이 완료되는 방식으로 앱을 구현할 수 있다는 점이었다.



### Tab 3

* [Realm](https://realm.io/kr/)

  ![realm_img](https://avatars1.githubusercontent.com/u/7575099?s=200&v=4)

## Demo

## Discussion

## Reference

