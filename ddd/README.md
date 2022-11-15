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

# DIP
Dependency Inversion Principle