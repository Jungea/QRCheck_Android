# 이용 안내
성공회대학교 QR코드 출석체크 앱 이용 안내입니다.

웹 쪽으로 이동 [QRCheck](https://github.com/Jungea/QRCheck)

## 📖 상세
- 교수와 학생 모두가 출석체크를 편하게 하기 위해 만든 시스템.
- 이름을 불러 출석 체크를 하여 수업 시간이 늦춰지고 지각한 사람을 처리하기 위해 교수님이 다시 체크하는 불편한 점을 없애고 프로젝트를 생각하게 되었습니다.
- 주요 기능은 3가지 입니다.
    - 교수가 성공회대학교 출석체크 웹페이지를 통해 자신의 과목 QR코드를 띄우면
    - 학생이 성공회대학교 출석체크 어플을 통해 해당 QR코드를 스캔하여 출석체크를 한다.
    - 지각한 학생은 강의실 QR코드를 스캔하여 지각 처리를 한다.

## 🛠️ 사용 기술 및 라이브러리
- Web
    - Spring Boot, JPA, ZXing
    - HTML, CSS, JS, jQuery, Bootstrap, Ajax
- App
    - Android, Retrofit
- MySQL

## 📱 개발한 부분
- REST API 개발
- Web (교수)
- Bootstrap을 이용한 깔끔한 CSS
- Ajax를 이용한 비동기 통신
    - 로그인
    - 강의 목록
    - jQuery, Bootstrap을 이용한 QR 코드 모달 띄우기
    - 회차별 출석 정보
    - 수동 출석
- App (학생)
- Retrofit을 이용한 비동기 통신
    - 로그인
    - 학생 출석 정보 목록
    - QR 코드 스캐너 (ZXing)

## 💡 후기
- JPA와 안드로이드 복습용 프로젝트였지만 프로젝트를 구현하면서 더 많은 공부를 하였습니다.
- 비동기 처리
    - 스프링으로 REST API를 만들어 웹페이지의 경우 화면 전체가 새로고침 되지 않게 Ajax를 이용해 비동기 통신을 하였고
    - 애플리케이션의 경우 Retrofit을 이용해 데이터 처리를 하였습니다.
- ZXing 라이브러리르 이용해 QR코드 생성, 스캔 기능을 구현하였습니다.

## 🌈 앞으로의 계획
- 관리자 페이지
- QR 코드 인식 시 위치 정보 체크
- QR 코드 데이터 암호화 필요
- 시간마다 QR 코드 변경
- 앱 UI 개선


> 이용 시 RetrofitService.java 의 BASE_URL을 변경해줘야 한다.

## 테스트

> **공통 비밀번호: 1111**

**학생ID 14** 아이디: 200032014  
<img src="https://user-images.githubusercontent.com/33142199/79818669-523fdd80-83c3-11ea-86f0-15896c7f8794.jpg" alt="14 시간표" width="200px" height="300">

**학생ID 44** 아이디: 200132015  
<img src="https://user-images.githubusercontent.com/33142199/79818708-6b488e80-83c3-11ea-90b5-95f5801cd075.jpg" alt="44 시간표" width="200px" height="300">

---
시연영상 https://youtu.be/30qy2W7MbPU
