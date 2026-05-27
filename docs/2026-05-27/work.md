# 2026-05-27 작업 기록

- Micrometer Prometheus 통합을 시작함.
- `pom.xml`에 `micrometer-registry-prometheus` 의존성을 추가함.
- `src/main/resources/application.properties`에 Actuator Prometheus 노출 및 메트릭 설정을 추가함.
- `prometheus/prometheus.yml`에 로컬 애플리케이션 스크랩 타겟을 추가함.
- `README.md`에 Prometheus 실행 및 메트릭 확인 방법을 추가함.
- `MetricsConfig`로 공통 Micrometer 태그 설정을 추가함.
- `SecurityConfig`로 `/actuator/prometheus`와 `/actuator/health`를 허용하도록 보안 설정함.
