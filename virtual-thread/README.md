# Virtual Thread

## 결과

### 가상 쓰레드 사용

#### 코드
```java
Thread.startVirtualThread(runnable);
```

#### 실행
```
Run time: 237
```

### 일반 쓰레드 사용

#### 코드
```java
Thread t = new Thread(runnable);
t.start();
```

#### 실행
```
Run time: 5770
```

## More reads
- https://www.happycoders.eu/java/virtual-threads/
- https://www.itworld.co.kr/mainnews/263362
- https://blogs.oracle.com/javamagazine/post/java-loom-virtual-threads-platform-threads
- https://homoefficio.github.io/2020/12/11/Java-Concurrency-Evolution
- https://dzone.com/articles/java-concurrency-evolution
- https://www.infoq.com/articles/java-virtual-threads/