# CH_IOT <br><br> 2024년 IOT 시스템기초

## 팀원소개

|                                             팀장 박준걸                                              |                                                 팀원 박창욱                                                  |                                                 팀원 홍서경                                                  |
| :--------------------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------------------------: |
| <img alt="박준걸프로필" src="https://avatars.githubusercontent.com/jxxn92" width="200" height="200"> | <img width="200" height="200" alt="박창욱프로필" src="https://avatars.githubusercontent.com/(깃허브닉네임)"> | <img width="200" height="200" alt="홍서경프로필" src="https://avatars.githubusercontent.com/(깃허브닉네임)"> |
|                                             - 코드 구현                                              |                                                    - 팀원                                                    |                                                    - 팀원                                                    |
|                                [🔗깃허브](https://github.com/jxxn92)                                 |                                       [🔗깃허브](https://github.com/)                                        |                                       [🔗깃허브](https://github.com/)                                        |

<br>

---

### 03조 아두이노를 이용한 간이 음주측정기

---

## 주요 기능

-   **회원가입, 로그인, 블루투스** 연결
    -   `사용자 이름, 생년월일(6자리)를 활용`한 회원가입
    -   블루투스 `ON -> 선택`, `OFF -> 블루투스 연결 화면`
-   **현재 알코올 농도** 측정
    -   `0.00 일치, 0.02 이하, 0.05 이하, 그 외`에 따른 이미지 및 문구 변경
-   **혈중 알코올 농도** 및 **알코올 분해 시간** 계산
    -   `나이, 몸무게, 마신 잔 수, 경과 시간, 성별` 입력 후 계산
-   현재 **음주에 대한 통계**
    -   `음주 빈도, 음주 정도`에 대한 통계
-   **사용자 통계** 및 **부가정보**
    -   사용자에 대한 `전체적인 통계`
    -   음주 관련 `표준 잔, 과음 기준 등`에 대한 부가정보 표시

<br>

<br>

### 프로젝트 구조

```

📦src
 ┣ 📂androidTest
 ┃ ┗ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂example
 ┃ ┃ ┃ ┃ ┗ 📂ch_iot
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ExampleInstrumentedTest.java
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┣ 📂com
 ┃ ┃ ┃ ┗ 📂example
 ┃ ┃ ┃ ┃ ┗ 📂ch_iot
 ┃ ┃ ┃ ┃ ┃ ┣ 📜AdditionalActivity.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜AlcoholActivity.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BackPressCloseHandler.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜InfoActivity.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜MainActivity.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜RegisterActivity.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜SQLiteHelper.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜StatisActivity.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜TempActivity.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜ViewActivity1.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ViewActivity2.java
 ┃ ┃ ┗ 📜connectedThread.java
 ┃ ┣ 📂res
 ┃ ┃ ┣ 📂drawable
 ┃ ┃ ┃ ┣ 📜bluetooth.png
 ┃ ┃ ┃ ┣ 📜button_back.xml
 ┃ ┃ ┃ ┣ 📜edit_round.xml
 ┃ ┃ ┃ ┣ 📜gradient_disconnect.xml
 ┃ ┃ ┃ ┣ 📜gradient_high.xml
 ┃ ┃ ┃ ┣ 📜gradient_low.xml
 ┃ ┃ ┃ ┣ 📜gradient_middle.xml
 ┃ ┃ ┃ ┣ 📜gradient_none.xml
 ┃ ┃ ┃ ┣ 📜high.png
 ┃ ┃ ┃ ┣ 📜ic_cal.png
 ┃ ┃ ┃ ┣ 📜ic_home.png
 ┃ ┃ ┃ ┣ 📜ic_info.png
 ┃ ┃ ┃ ┣ 📜ic_launcher_background.xml
 ┃ ┃ ┃ ┣ 📜ic_statis.png
 ┃ ┃ ┃ ┣ 📜img_1.jpg
 ┃ ┃ ┃ ┣ 📜img_2.jpg
 ┃ ┃ ┃ ┣ 📜img_3.jpg
 ┃ ┃ ┃ ┣ 📜low.png
 ┃ ┃ ┃ ┣ 📜middle.png
 ┃ ┃ ┃ ┣ 📜none.png
 ┃ ┃ ┃ ┗ 📜view_round.xml
 ┃ ┃ ┣ 📂drawable-v24
 ┃ ┃ ┃ ┗ 📜ic_launcher_foreground.xml
 ┃ ┃ ┣ 📂font
 ┃ ┃ ┃ ┣ 📜bmju.ttf
 ┃ ┃ ┃ ┗ 📜fontfamily.xml
 ┃ ┃ ┣ 📂layout
 ┃ ┃ ┃ ┣ 📜activity_additional.xml
 ┃ ┃ ┃ ┣ 📜activity_alcohol.xml
 ┃ ┃ ┃ ┣ 📜activity_info.xml
 ┃ ┃ ┃ ┣ 📜activity_main.xml
 ┃ ┃ ┃ ┣ 📜activity_register.xml
 ┃ ┃ ┃ ┣ 📜activity_statis.xml
 ┃ ┃ ┃ ┣ 📜activity_temp.xml
 ┃ ┃ ┃ ┣ 📜activity_view1.xml
 ┃ ┃ ┃ ┣ 📜activity_view2.xml
 ┃ ┃ ┃ ┗ 📜custom_alert_dialog.xml
 ┃ ┃ ┣ 📂menu
 ┃ ┃ ┣ 📂mipmap-anydpi-v26
 ┃ ┃ ┃ ┣ 📜ic_launcher.xml
 ┃ ┃ ┃ ┗ 📜ic_launcher_round.xml
 ┃ ┃ ┣ 📂mipmap-hdpi
 ┃ ┃ ┃ ┣ 📜ic_launcher.webp
 ┃ ┃ ┃ ┗ 📜ic_launcher_round.webp
 ┃ ┃ ┣ 📂mipmap-mdpi
 ┃ ┃ ┃ ┣ 📜ic_launcher.webp
 ┃ ┃ ┃ ┗ 📜ic_launcher_round.webp
 ┃ ┃ ┣ 📂mipmap-xhdpi
 ┃ ┃ ┃ ┣ 📜ic_launcher.webp
 ┃ ┃ ┃ ┗ 📜ic_launcher_round.webp
 ┃ ┃ ┣ 📂mipmap-xxhdpi
 ┃ ┃ ┃ ┣ 📜ic_launcher.webp
 ┃ ┃ ┃ ┗ 📜ic_launcher_round.webp
 ┃ ┃ ┣ 📂mipmap-xxxhdpi
 ┃ ┃ ┃ ┣ 📜ic_launcher.webp
 ┃ ┃ ┃ ┗ 📜ic_launcher_round.webp
 ┃ ┃ ┣ 📂values
 ┃ ┃ ┃ ┣ 📜colors.xml
 ┃ ┃ ┃ ┣ 📜strings.xml
 ┃ ┃ ┃ ┗ 📜themes.xml
 ┃ ┃ ┣ 📂values-night
 ┃ ┃ ┃ ┗ 📜themes.xml
 ┃ ┃ ┗ 📂xml
 ┃ ┃ ┃ ┣ 📜backup_rules.xml
 ┃ ┃ ┃ ┗ 📜data_extraction_rules.xml
 ┃ ┗ 📜AndroidManifest.xml
 ┗ 📂test
 ┃ ┗ 📂jav ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂example
 ┃ ┃ ┃ ┃ ┗ 📂ch_iot
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ExampleUnitTest.java

```
