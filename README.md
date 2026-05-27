# ticketing

Java 25 + Spring Boot 4.0.6 기반 티켓 예약 백엔드 프로젝트입니다.

## 실행 방법

1. 로컬 빌드 및 테스트

```bash
./mvnw clean test
```

2. 애플리케이션 실행

```bash
./mvnw spring-boot:run
```

## Metrics 및 Monitoring

- 애플리케이션 실행 후 `http://localhost:8080/actuator/prometheus`에서 Prometheus 메트릭을 확인할 수 있습니다.
- Prometheus를 실행하려면:

```bash
docker compose -f compose.yaml up -d prometheus
```

- Prometheus UI:

```text
http://localhost:9090
```

## 테스트 환경

- 테스트는 `H2` 인메모리 데이터베이스를 사용합니다.
- 기본 애플리케이션은 `src/main/resources/application.properties`를 사용합니다.
