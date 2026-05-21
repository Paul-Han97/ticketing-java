# Project Instruction

## Project Documentation Rules

- When responding to me, use Korean.
- Write agent.md, skill.md, and instruction.md in English.
  - agent.md should be written in the .github/agents/ path
  - skill.md should be written in the .github/skills/ path
  - instructions.md should be written in the .github/instructions/ path
- Write README.md, work descriptions, progress updates, and similar files in Korean.
  - All work must be recorded, and the work logs must be created inside a folder named with the current date in the format `docs/YYYY-MM-DD/`  
    For example, `docs/2026-05-20/work.md`

## Backend Runtime and Framework Versions

### Java and Spring Boot versions

- Java 25
- Spring Boot 4.0.6
  - Spring MVC (`spring-boot-starter-webmvc`) 4.0.6
  - Spring Data JPA (`spring-boot-starter-data-jpa`) 4.0.6
  - Spring Data Redis (`spring-boot-starter-data-redis`) 4.0.6
  - Apache Kafka (`spring-boot-starter-kafka`) 4.0.6
  - Spring Security (`spring-boot-starter-security`) 4.0.6
  - Spring Validation (`spring-boot-starter-validation`) 4.0.6
  - Actuator 4.0.6
  - DevTools 4.0.6
- PostgreSQL runtime driver 42.7.10
- Lombok 1.18.46

### docker versions

- Docker 29.5.1
- Docker Compose 5.1.3

### Databases versions

- PostgreSQL 18.4
- Redis 8.6.3

### Kafka versions

- Kafka 4.1.2

## Development Principles

### 1. Think Before Coding

**Don't assume. Don't hide confusion. Surface tradeoffs.**

Before implementing:

- State your assumptions explicitly. If uncertain, ask.
- If multiple interpretations exist, present them - don't pick silently.
- If a simpler approach exists, say so. Push back when warranted.
- If something is unclear, stop. Name what's confusing. Ask.

### 2. Simplicity First

**Minimum code that solves the problem. Nothing speculative.**

- No features beyond what was asked.
- No abstractions for single-use code.
- No "flexibility" or "configurability" that wasn't requested.
- No error handling for impossible scenarios.
- If you write 200 lines and it could be 50, rewrite it.

Ask yourself: "Would a senior engineer say this is overcomplicated?" If yes, simplify.

### 3. Surgical Changes

**Touch only what you must. Clean up only your own mess.**

When editing existing code:

- Don't "improve" adjacent code, comments, or formatting.
- Don't refactor things that aren't broken.
- Match existing style, even if you'd do it differently.
- If you notice unrelated dead code, mention it - don't delete it.

When your changes create orphans:

- Remove imports/variables/functions that YOUR changes made unused.
- Don't remove pre-existing dead code unless asked.

The test: Every changed line should trace directly to the user's request.

### 4. Goal-Driven Execution

**Define success criteria. Loop until verified.**

Transform tasks into verifiable goals:

- "Add validation" → "Write tests for invalid inputs, then make them pass"
- "Fix the bug" → "Write a test that reproduces it, then make it pass"
- "Refactor X" → "Ensure tests pass before and after"

For multi-step tasks, state a brief plan:

```
1. [Step] → verify: [check]
2. [Step] → verify: [check]
3. [Step] → verify: [check]
```
