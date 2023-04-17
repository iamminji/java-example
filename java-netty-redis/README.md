#### netty 로 redis 서버 만들기 실습

### 서버 실행
TBD

### 커넥션 테스트
```shell
$ telnet 127.0.0.1 6379
```

### redis-cli 로 직접 실습
```shell
$ redis-cli --verbose
127.0.0.1:6379> SET 1 2
OK
127.0.0.1:6379> GET 1
"2"
```