# CS496 Week 1 Report

## Abstraction

프로젝트의 결과물은 총 3개의 탭으로 구성된 앱이며 각 탭은 다음과 같은 기능을 가진다.

* Tab1: 전화번호부 연동앱으로 스마트폰에 있는 전화번호부 데이터를 가져와 리사이클러뷰를 이용해 뷰를 그려준다.
* Tab2: 갤러리 앱으로 Tab1과 마찬가지로 스마트폰 내의 갤러리 앱의 사진 URI를 불러와 리사이클러뷰를 이용해 뷰를 그려준다.
* Tab3: 웹 주소를 북마크하여 간단한 메모와 태그를 남길 수 있는 앱으로 구글 플레이스토어에 있는 ***[LinKeep](https://play.google.com/store/apps/details?id=com.francescopennella.linkeep)***의 기능을 가능한 비슷하게 구현하였다.

## Demo

#### Tab1: Contacts

첫번째 탭은 스마트본에 있는 연락처 정보를 가져와서 보여주는 기능으로 리사이클러뷰를 이용하여 리스트 형식으로 전화번호 데이터를 순서대로 나열하였다. 

![contact]()

위의 보이는 사진이 가장 기본이 되는 주소록 뷰홀더이며 다음과 같은 기능을 가지로 있다.

* 뷰: 전화번호에 해당하느 주소록 데이터로 이동할 수 있다. 
* 전화 버튼: 클릭시 통화 화면으로 이동한다(통화 x).
* 문자 번튼: 클릭시 문자 화면을 이동한다.





#### Tab2: Gallery

탭1과 마찬가지로 스마트폰 내의 갤러리의 사진 데이터를 uri 형식으로 가져와서 갤러리 내에 리사이클러뷰를 이용하여 그리드 형식으로 사진을 보여주는 기능을 한다. 버튼을 누르게 되면 카메라 앱으로 인텐트 요청을 하며 카메라를 찍은 후에는 사진 폴더(`Picture\`)에  저장이 되면 사진 갤러리에 추가와 동시에 탭에 추가 된는 기능을 구현하였다.



#### Tab3: Link keeper

세번째 탭은 웹서핑을 하거나, 자주 들어가는 사이트에 대해서 메모와 동시에 북마크를 저장해 놓는 기능을 가지는 앱을 구현해 보았다. 

![urlcard]()

* 뷰: 클릭시 저장해 놓은 데이터로 이동이 가능하다.
* 태그버튼: 해당 태그에 해당하는 뷰를 만들어 보여주는 기능을 한다.
* 수정 버튼: 메모나 태그를 수정하는 기능을 한다.
* 복사 버튼: 클리보드에 해당 url을 복사한다.
* 공유 버튼: 연동되는 앱에 대해서 해당 url을 공유하는 기능을 한다.



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

  ![photo_demo](https://raw.githubusercontent.com/Jaegoomon/cs496_week1/master/demo_source/report1.gif)


### Tab 3

* [Realm](https://realm.io/kr/)

  ![realm_img](https://avatars1.githubusercontent.com/u/7575099?s=200&v=4)

## Discussion

## Reference

