# HomeTown League(우리동네리그) (2023.08 ~ 2023.11)

전국의 아마추어 스포츠동호회를 위한 `매칭 플랫폼`입니다.
<br>
단순 매칭에 그치지 않고, 입력된 경기결과를 기반으로 각팀의 `랭크`를 측정하여 제공합니다.
<br>
<br>
온라인게임(LOL, 오버워치 등)을 즐기는 유저들이라면 공감하겠지만, 랭크가 있는 랭크게임과 랭크가 없는 일반게임의 몰입도 차이는 무시할 수 없습니다.
<br>
스포츠 동호회에 랭크를 도입하여 더 몰입도 있게 즐길 수 있도록 하고자 합니다.
<br>
자세한 `Business Rule` 및 `고민`은 [WIKI](https://github.com/HometownLeague/hometown-league-be/wiki) 에서 확인할 수 있습니다.

## 링크

| 링크                                             | 설명             |
|------------------------------------------------|----------------|
| [우리 동네 리그](http://218.232.175.4:49155)         | 우리동네리그 서비스에 접속 |
| [API](http://218.232.175.4:49156/rest/docs.do) | API 명세서        |

## 멤버
| 이름  | 역할           | github                                                                                                                                             |
|-----|--------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| 전영주 | Front-End 개발 | <a href="https://github.com/Jeon-YJ1004"><img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/></a> |
| 이경훈 | Back-End 개발  | <a href="https://github.com/pongdangx2"><img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/></a>  |

## 주요 기능

## 시스템 구조 및 기술 스택
### 사용 기술
Java 17, Spring Boot, Maven, Mybatis, Spring Rest Docs, React, Thymeleaf

### 환경 및 미들웨어
Nginx, Tomcat, MySQL, Redis

### 구조도
<img src="./README-resource/SystemStructure.png" title="System 구조도"/>

## 주요 사항
### Session Clustering
- 분산 환경에서 `Session 정합성`을 유지하고 `Scale Out`에 유리하게 하기 위해 `Redis`에 `Session 정보`를 관리했습니다.
- Redis를 활용해 간단히 세션을 관리할 수 있는 `Spring Session Data Redis` 대신 `Interceptor`와 `Custom Annotation`을 활용해 직접 개발하는 방법을 택했습니다.

#### Session 생성 및 파기
- 로그인 시 `사용자 ID`와 `타임스탬프`의 해시값을 `Key`로 세션 정보를 `Redis`에 저장했습니다.
- 세션은 로그아웃 시 혹은 30분동안 갱신되지 않았을 시 파기됩니다.

#### Session Interceptor
- Sesison 처리가 필요한 API 호출 시 Session을 확인하는 `Interceptor`를 만들었습니다.
- `AuthCheck`라는 이름의 `Custom Annotation`을 만들어 이 애노테이션이 붙어있는 Method/Controller 호출 시 Session Interceptor에서 세션을 확인합니다.

### 매칭 서비스
- 팀의 주장이 매칭을 요청하면 `Redis`에 있는 매칭 대기열에 매칭 요청 데이터를 생성하고, RDB에도 저장합니다. 
- `@Scheduled`애노테이션을 활용해 대기열에 있는 매칭 요청들 중 시간, 장소, 랭크가 비슷한 요청들을 매칭시켰습니다.

### 랭크 서비스
- 랭크 계산은 [ELO Rating](https://ko.wikipedia.org/wiki/%EC%97%98%EB%A1%9C_%ED%8F%89%EC%A0%90_%EC%8B%9C%EC%8A%A4%ED%85%9C) 을 사용했습니다.

## 화면설계서
- [화면설계](https://www.figma.com/file/bjqo9hgQBbuflPYZ72ybpo/HomeTownLeague-%EA%B0%84%EB%8B%A8-%EC%99%80%EC%9D%B4%EC%96%B4%ED%94%84%EB%A0%88%EC%9E%84?type=design&node-id=0%3A1&mode=design&t=FnWuJ4wK3mXiBTHG-1) 는 Figma를 사용했습니다.

## ERD
- ERD는 dbdiagram을 사용해 작성했습니다.
- 각 테이블/컬럼의 코멘트는 [ERD](https://dbdiagram.io/d/HomeTownLeague-6562efe33be1495787bd843f) 를 참고하세요.
- 이미지 캡처본은 다음과 같습니다.
  <img src="./README-resource/erd.png" title="ERD"/>


