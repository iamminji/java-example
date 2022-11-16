# DDD

- Presentation (표현)
- Application (응용)
- Domain (도메인)
- Infrastructure (인프라스트럭쳐)

## Presentation
HTTP 요청을 응용 영역이 필요로 하는 형식으로 변환해서 응용 영역에 전달하고 응용 영역의 응답을 HTTP 응답으로 변환하여 전송한다.

## Application
도메인 영역의 도메인 모델을 사용한다.
응용 서비스는 로직을 직접 수행하기보다는 도메인 모델에 로직 수행을 위임한다.

## Domain
도메인 모델을 구현한다.

## Infrastructure
구현 기술에 대한 것을 다룬다. 이 영역은 RDBMS 연동을 처리하고 메시징 큐에 메시지를 전송하거나 수신하는 기능을 구현하고, 몽고DB나 
레디스와의 데이터 연동을 처리한다.

HTTP client를 이용해 다른 API를 호출하는 것도 처리한다.

인프라스트럭처 영역은 논리적인 개념을 표현하기 보다는 실제 구현을 다룬다.

# DIP (Dependency Inversion Principle)
저수준 모듈이 고수준 모듈에 의존하는 경우를 DIP, 의존 역전 원칙이라고 부른다.
DIP를 적용하면 infrastructure 영역에 의존할 때 발생했던, 구현 교체가 어렵다는 것과 테스트가 어려운 문제 모두를 해결할 수 있다.

Repository 같은 건 인터페이스(Domain/Application) 가 고수준이고 구현체(Infrastructure)가 저수준이다.
이 경우 저수준인 Infrastructure 가 고수준인 Domain/Application Layer에 의존성(상속/구현) 을 갖게 되는 구조가 되므로 Domain/Application Layer에 영향을 주지 않거나
최소화하면서 변경이 가능하게 된다.

> DIP 를 무리 하게 할 필요는 없고 점진적으로 추상화하는 방안도 고려해볼 것

# DDD Component

## Entity
unique identifier 를 갖는 객체로 자신의 life cycle을 갖는다.

__DB Entity 와는 다르다!__

## Value
unique identifier 를 갖지 않는 객체로 하나인 값을 표현할 때 사용한다.

__Immutable 인 편이 좋다.__

## Aggregate
연관된 Entity 와 Value 를 개념적으로 하나로 묶은 것이다.

## Repository

## Domain Service
특정 Entity에 속하지 않은 도메인 로직을 제공한다.