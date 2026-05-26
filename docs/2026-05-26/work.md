# 2026-05-26 작업 기록

- `pom.xml`에 테스트용 `H2` 데이터베이스 의존성을 추가함.
- `src/test/resources/application.properties`를 만들어 테스트 시 인메모리 데이터베이스 설정을 추가함.
- `README.md`를 생성하여 프로젝트 실행 및 테스트 방법을 정리함.
- Phase 0: 티켓 재고 엔티티, JPA 리포지토리, 재고 조회/생성 API, 예약 요청 API 및 `X-Idempotency-Key` 기반 토큰 지원을 구현함.
- Redis 기반 idempotency 기록과 짧은 TTL 예약 홀드를 `ReservationService`에 추가함.

## Phase 1 구현 (2026-05-26)

- PostgreSQL 원자적 최종화 메서드 추가: `TicketRepository.decrementAvailableIfEnough(...)` 구현
- `ReservationService.finalizeReservation(...)` 추가: idempotency 검증, 홀드 확인, DB 원자적 감소, 홀드 삭제, 응답 저장
- 컨트롤러 엔드포인트 추가: `POST /api/inventory/reserve/finalize` (헤더 `X-Idempotency-Key` 필요)
- Micrometer 글로벌 레지스트리에 간단한 메트릭 카운터(`reservation.completed`, `reservation.failed`)를 기록하도록 함
- 기존 단위테스트(`ReservationServiceTest`) 실행: 성공

다음 작업 권장:

- Micrometer/Prometheus 연동 및 대시보드 구성
- Phase 2: Kafka 큐잉 및 소비자 구현, 데드레터·재시도 정책
- 통합 테스트(인메모리 DB + Redis)를 추가하여 E2E 검증
