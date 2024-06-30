# CH_IOT <br><br> 2024년 IOT 시스템기초

## 조원 소개

|                                             팀장 박준걸                                              |                                                 팀원 박창욱                                                  |                                              팀원 홍서경                                              |
| :--------------------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------: |
| <img alt="박준걸프로필" src="https://avatars.githubusercontent.com/jxxn92" width="200" height="200"> | <img width="200" height="200" alt="박창욱프로필" src="https://avatars.githubusercontent.com/(깃허브닉네임)"> | <img width="200" height="200" alt="홍서경프로필" src="https://avatars.githubusercontent.com/hsk1587"> |
|                                      - 코드 구현<br>- 버그 수정                                      |                                        - 데이터 수집<br>- 데이터 분석                                        |                                      - UI 구성<br>- 보고서 작성                                       |
|                                [🔗깃허브](https://github.com/jxxn92)                                 |                               [🔗깃허브](<https://github.com/(깃허브닉네임)>)                                |                                [🔗깃허브](https://github.com/hsk1587)                                 |

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
    -   음주 관련 `표준 잔, 과음 기준 등`에 대한 `부가정보` 표시

<br>

## 구현 이미지

<br>

> 로그인 및 회원 가입

<img src="https://github.com/jxxn92/jxxn92/assets/116718062/6c39bb9d-bebb-4b20-af56-44af8547a7b6" alt="register" width="200" height="400"/>
<img src="https://github.com/jxxn92/jxxn92/assets/116718062/d75dd30a-0b6f-4406-8913-b8ef5c1c3a9f" alt="login" width="200" height="400"/>

<br><br>

> 블루투스 연결

<img src="https://github.com/jxxn92/jxxn92/assets/116718062/0d04138b-ed6b-450f-83be-c17cc649bc93" alt="blue_off" width="200" height="400"/>
<img src="https://github.com/jxxn92/jxxn92/assets/116718062/fe34948e-819c-40d9-a17f-997cef298c6b" alt="blue_on" width="200" height="400"/>

<br>
　<블루투스 OFF 상태>　　　<블루투스 ON 상태>

<br><br>

> 알코올 측정

<img src="https://github.com/jxxn92/jxxn92/assets/116718062/2e9d538b-11a0-4fe2-b1d1-2a3ad88eeee9" alt="0.00" width="200" height="400"/>
<img src="https://github.com/jxxn92/jxxn92/assets/116718062/b4ca0ea0-02ac-49eb-8541-00dfd555b5fb" alt="0.02 이하" width="200" height="400"/>
<img src="https://github.com/jxxn92/jxxn92/assets/116718062/55270137-adb5-4ad1-b734-4eddf79e5ca6" alt="0.05 이하" width="200" height="400"/>
<img src="https://github.com/jxxn92/jxxn92/assets/116718062/27febc8b-1e22-4f61-88e7-d150dab35fdb" alt="그 외" width="200" height="400"/>

<br>
　　　<측정치 0.00>　　　　　<측정치 0.02 이하>　　　　<측정치 0.05 이하>　　　　　<측정치 그 외>

<br><br>

> 혈중 알코올 농도, 알코올 분해시간 계산

<br>

<img src="https://github.com/jxxn92/jxxn92/assets/116718062/c6cc2309-50ad-4f45-9cb9-00c142ced6f3" alt="계산 입력창" width="200" height="400"/>
<img src="https://github.com/jxxn92/jxxn92/assets/116718062/8f7cd539-9126-4ca0-84c3-5a2961875152" alt="결과" width="200" height="400"/>

<br><br>

> 음주 빈도, 정도 통계

<br>

<img src="https://github.com/jxxn92/jxxn92/assets/116718062/3f863aa5-d8c3-435e-91e3-ff3b7f18fd74" alt="" width="200" height="400"/>
<img src="https://github.com/jxxn92/jxxn92/assets/116718062/cdb0c0a1-b60a-4e6e-a3a4-f52ed81ded9e" alt="" width="200" height="400"/>
<img src="https://github.com/jxxn92/jxxn92/assets/116718062/1b7f2f70-992d-4775-a82a-e78602f04aac" alt="" width="200" height="400"/>

<br><br>

> 사용자 통계 및 부가 정보

<br>

<img src="https://github.com/jxxn92/jxxn92/assets/116718062/4ce69dae-a95c-456f-b84f-d13d258d3750" alt="" width="200" height="400"/>
<img src="https://github.com/jxxn92/jxxn92/assets/116718062/b3baa531-c767-4ce4-98ac-582ca8f98a40" alt="" width="200" height="400"/>
<img src="https://github.com/jxxn92/jxxn92/assets/116718062/d3336d8d-9686-47cd-8bf4-034cc65829d1" alt="" width="200" height="400"/>

<br>

### 프로젝트 구조

---

```

📦src
 ┣ 📂androidTest
 ┃ ┗ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂example
 ┃ ┃ ┃ ┃ ┗ 📂ch_iot
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┣ 📂com
 ┃ ┃ ┃ ┗ 📂example
 ┃ ┃ ┃ ┃ ┗ 📂ch_iot
 ┃ ┣ 📂res
 ┃ ┃ ┣ 📂drawable
 ┃ ┃ ┣ 📂drawable-v24
 ┃ ┃ ┣ 📂font
 ┃ ┃ ┣ 📂layout
 ┃ ┃ ┣ 📂menu
 ┃ ┃ ┣ 📂mipmap-anydpi-v26
 ┃ ┃ ┣ 📂mipmap-hdpi
 ┃ ┃ ┣ 📂mipmap-mdpi
 ┃ ┃ ┣ 📂mipmap-xhdpi
 ┃ ┃ ┣ 📂mipmap-xxhdpi
 ┃ ┃ ┣ 📂mipmap-xxxhdpi
 ┃ ┃ ┣ 📂values
 ┃ ┃ ┣ 📂values-night
 ┃ ┃ ┗ 📂xml
 ┗ 📂test
```
