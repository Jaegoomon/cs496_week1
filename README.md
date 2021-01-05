# CS496 Week 1 Report

## Abstraction

프로젝트의 결과물은 총 3개의 탭으로 구성된 앱이며 각 탭은 다음과 같은 기능을 가진다.

* Tab1: 전화번호부 연동앱으로 스마트폰에 있는 전화번호부 데이터를 가져와 리사이클러뷰를 이용해 뷰를 그려준다.
* Tab2: 갤러리 앱으로 Tab1과 마찬가지로 스마트폰 내의 갤러리 앱의 사진 URI를 불러와 리사이클러뷰를 이용해 뷰를 그려준다.
* Tab3: 웹 주소를 북마크하여 간단한 메모와 태그를 남길 수 있는 앱으로 구글 플레이스토어에 있는 ***[LinKeep](https://play.google.com/store/apps/details?id=com.francescopennella.linkeep)***의 기능을 가능한 비슷하게 구현하였다.



## Demo

#### Tab1: Contacts

첫번째 탭은 스마트본에 있는 연락처 정보를 가져와서 보여주는 기능으로 리사이클러뷰를 이용하여 리스트 형식으로 전화번호 데이터를 순서대로 나열하였다. 

![contact](https://raw.githubusercontent.com/Jaegoomon/cs496_week1/master/demo_source/contact_card2.png)

위의 보이는 사진이 가장 기본이 되는 주소록 뷰홀더이며 다음과 같은 기능을 가지로 있다.

* 뷰: 전화번호에 해당하느 주소록 데이터로 이동할 수 있다. 
* 전화 버튼: 클릭시 통화 화면으로 이동한다.(통화 x)
* 문자 번튼: 클릭시 문자 화면을 이동한다.

![contact_demo1](https://raw.githubusercontent.com/Jaegoomon/cs496_week1/master/demo_source/demo1.gif) ![](https://raw.githubusercontent.com/Jaegoomon/cs496_week1/master/demo_source/demo2.gif)



#### Tab2: Gallery

탭1과 마찬가지로 스마트폰 내의 갤러리의 사진 데이터를 uri 형식으로 가져와서 갤러리 내에 리사이클러뷰를 이용하여 그리드 형식으로 사진을 보여주는 기능을 한다. 버튼을 누르게 되면 카메라 앱으로 인텐트 요청을 하며 카메라를 찍은 후에는 사진 폴더(`Picture\`)에  저장이 되면 사진 갤러리에 추가와 동시에 탭에 추가 된는 기능을 구현하였다.



#### Tab3: Link keeper

세번째 탭은 웹서핑을 하거나, 자주 들어가는 사이트에 대해서 메모와 동시에 북마크를 저장해 놓는 기능을 가지는 앱을 구현해 보았다. 

![urlcard](https://raw.githubusercontent.com/Jaegoomon/cs496_week1/master/demo_source/url_card.png)

* 뷰: 클릭시 저장해 놓은 데이터로 이동이 가능하다.
* 태그버튼: 해당 태그에 해당하는 뷰를 만들어 보여주는 기능을 한다.
* 수정 버튼: 메모나 태그를 수정하는 기능을 한다.
* 복사 버튼: 클리보드에 해당 url을 복사한다.
* 공유 버튼: 연동되는 앱에 대해서 해당 url을 공유하는 기능을 한다.

![demo4](https://raw.githubusercontent.com/Jaegoomon/cs496_week1/master/demo_source/demo4.gif) ![demo3](https://raw.githubusercontent.com/Jaegoomon/cs496_week1/master/demo_source/demo3.gif)



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

  SQLite와 같이 안드로이드 내에서 지원하는 데이터 베이스도 있지만 세번째 탭에서 `Realm`을 사용한 것은 사용법이 상당히 용이한데 있었다. 세번째 탭을 구현하면서 `Realm`이 주었던 장점을 나열해 보았다. 다른 데이터 베이스에 대한 깊은 지식이 없기 때문에 이번 프로젝트를 진행하는 동안 느꼈던 필자의 생각이 주를 이루고 있음을 참고했으면 한다.

  

  #### 1.데이터 베이스 빌딩이 매우 쉽다.

  `Realm`을 사용하면서 가장 매력적이었던 점중에 하나였던 것은 클래스를 통해 데이터 베이스의 Primary key와 데이터의 프로퍼티(column)을 정의하는 것이 매우 직관적이 었다.

  ```kotlin
  open class ClipRealmData : RealmObject() {
      @PrimaryKey
      var id = 0
      var url: String = ""
      var title: String = ""
      var content: String = ""
      var tag: String = ""
      var tagColor: Int = 0
  }
  ```

  위와 같이 `PrimaryKey`와 각 데이터 베이스에 들어갈 요소들을 직관적으로 정의할 수 있다. 

  #### 2.`RealmRecycler View`

  무엇보다 가장 좋았던  `Realm`의 기능이었다. 주로 이번 프로젝트를 하면서 삭제와 추가를 구현하는 일이 생기는 상황에서는 프래그먼트를 새롭게 갱신하는 방법이나 `RecyclerAdapter.notifySetDataChanged()` 함수를 자주 자용하는 일이 많았는데, 세번째 탭에서 `Realm` 데이터 베이스와 리사이클러 뷰를 연동함으로써 삭제 혹은 수정과 같은 이벤트를 처리할 때, 자동으로 `Realm`의 변화를 인지하고 리사이클러뷰를 갱신해주었다. 삭제, 추가에 따른 requestCode와 resultCode를 처리하지 않아도 되었기 때문에 매우 편리하면서도 적은 양의 코드로 리사이클러뷰 구현이 가능하였다.

  #### 3. 데이터 베이스 가시화

  직접적으로 사용한 기능은 아니었지만 데이터 베이스를 초기화하고 데이터 추가 삭제에 대한 디버깅을 할때 [RealmStudio](https://docs.realm.io/sync/realm-studio)를 활용하여 데이터 베이스의 작동 여부를 확인할 수 있었다. 데이터 베이스를 반응적으로 보여주지는 못하지만(찾지 못하였다.) 처음 데이터 베이스에 대한 디버깅을 할때 지관적으로 이해를 하는데 많은 도움이 되었다.

  

## Discussion

#### 파일 저장 딜레이

카메로 찍은 사진, 연락처를 추가, 삭제, 편집하는 것 모두 무리없이 데이터의 변화가 일어나는 즉시 리사이클러뷰가 이를 인식하고 뷰를 그리도록 코드를 구현했으나, 사진을 찍어 사진 디렉토리에 추가하는데 약간의 딜레이가 발생하여 이 경우에 `Thread.sleep()`함수를 이용하여 안전하게 파일을 읽어오도록 구현하였다. 파일의 변화를 안드로이드 시스템에서 신호를 보내주는 방식도 생각해보았지만 관련된 자료를 찾지 못하였다.

#### 데이터 베이스 탭 추가

추가로 구현을 희망하는 것은 세번째 탭에 대하여 Custom tag를 구현하는 것이다. 이를 위해서 `Realm`의 데이터 베이스 탭(엑셀과 같이 한 파일에 여러 탭을 만드는 방식)을 새롭게 만들고 싶어 관련 내용을 찾아보았지만 프로젝트 파일이 수정한 코드를 빌드하지 못해 지정된 태그만을 선택하는 방식으로 코드를 수정하였다. 이와 관련해서 데이터 베이스에 대한 공부가 필요할 것으로 보인다.

#### 유동적으로 퍼미션 받기

현 프로젝트의 경우 앱이 실행됨과 동시에 앱 구동에 필요한 모든 퍼미션을 한 액티비티 안에서 모두 받도록 설계를 하였다. 이는 구현하는 과정에서는 퍼미션과 관련된 요청 부분을 쉽게 처리할 수 있었지만 사용자 경험을 상당히 떨어뜨리는 방식이기 때문에 유동적인 퍼미션 요청이 필요할 것으로 보인다.









