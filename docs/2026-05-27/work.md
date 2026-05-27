# 2026-05-27 작업 기록

- Micrometer Prometheus 통합을 시작함.
- `pom.xml`에 `micrometer-registry-prometheus` 의존성을 추가함.
- `src/main/resources/application.properties`에 Actuator Prometheus 노출 및 메트릭 설정을 추가함.
- `prometheus/prometheus.yml`에 로컬 애플리케이션 스크랩 타겟을 추가함.
- `README.md`에 Prometheus 실행 및 메트릭 확인 방법을 추가함.
- `MetricsConfig`로 공통 Micrometer 태그 설정을 추가함.
- `SecurityConfig`로 `/actuator/prometheus`와 `/actuator/health`를 허용하도록 보안 설정함.

추가 작업 (Phase 2 - 2026-05-27):

- Kafka 기반 예약 처리 파이프라인 초기 구현
  - `reservation-requests` 및 `reservation-dlq` 토픽 생성(NewTopic 빈)
  - 예약 메시지 DTO `api/dto/ReservationMessage` 추가
  - `ReservationProducer`: Kafka로 예약 의도 발행 및 DLQ 전송
  - `ReservationConsumer`: 메시지 수신, `finalizeReservation` 호출, 간단 재시도(최대 3회) 후 DLQ 전송
  - `ReservationService.reserve()`에서 예약 보류 생성 후 Kafka로 메시지 발행하도록 수정
