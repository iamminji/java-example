
```shell
$ ./gradlew generateGrammarSource
```

run result
```
LogEntry{level=DEBUG, message='entering awesome method', timestamp=2018-05-05T14:20:21}
```

#### 문제
- grammar build 할 때 generated-src 하위에 파일이 들어가고 이 클래스들 중 일무를 src 로 옮김
- build 하고 실행하려니 src 메인에 있는 클래스와 generated-src 하위에 클래스가 이름이 같아서 duplicate class 에러가 났다
- class 파일 빌드 시 generated-src 를 막는 방법이 없나..?
