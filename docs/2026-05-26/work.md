# 2026-05-26 작업 기록

- `pom.xml`에 테스트용 `H2` 데이터베이스 의존성을 추가함.
- `src/test/resources/application.properties`를 만들어 테스트 시 인메모리 데이터베이스 설정을 추가함.
- `README.md`를 생성하여 프로젝트 실행 및 테스트 방법을 정리함.
- Phase 0: 티켓 재고 엔티티, JPA 리포지토리, 재고 조회/생성 API, 예약 요청 API 및 `X-Idempotency-Key` 기반 토큰 지원을 구현함.
- Redis 기반 idempotency 기록과 짧은 TTL 예약 홀드를 `ReservationService`에 추가함.
