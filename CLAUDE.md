# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository purpose

C3 Coding Dojo — a collection of self-contained exercises ("katas") used by the cronos C3 team for hands-on learning. Each kata is a deliberately incomplete slice of functionality; trainees fill in the blanks guided by the kata's `package-info.java` (or `kata.md` on the frontend). README and kata briefings are written in **German**; match that tone in user-facing strings and comments unless explicitly told otherwise.

## Architecture

Two independent build targets at the repo root:

- `backend/` — Spring Boot 4.0.x on **Java 25** (Maven). One application (`DojoStarter`) hosts every kata as a sibling package under `de.cronoscx.c3.dojo.katas.*`. Katas are mostly isolated: each contains its own `@Configuration`, `@RestController` (named `*UseCases`) and supporting types. There is no shared domain model linking the katas — they only share the security filter chains in `DojoConfig` and the Postgres + Liquibase infrastructure.
- `frontend/` — Angular 21 standalone-component app (`katas-fe`) under `frontend/src/app`. Routes are registered manually in `app.routes.ts`; not every backend kata has a frontend (e.g. CSS and TypeScript katas exist only on the frontend).

Cross-cutting infrastructure:

- **Security**: `DojoConfig` defines two filter chains — Basic auth on `/actuator/**` (credentials in `c3.dojo.management.*`) and a disabled-CSRF, stateless chain on `/api/**`. All backend HTTP routes are mounted under the `/api` context path; the Angular dev server proxies `/api/**` to `http://localhost:8080` via `frontend/src/proxy.conf.json`.
- **Persistence**: PostgreSQL (compose service on host port **5400**, db `c3_dojo`, user `master`). Schema is managed by Liquibase with `master.xml` including a per-kata folder under `db/changelog/katas/`. The `development` context loads only seed/dev data.
- **AI**: Spring AI on **Ollama** (default model `llama3.1`, `pull-model-strategy=always`). Each AI kata wires its own `ChatClient` bean and selects it via a `@Qualifier` constant (`SpringAiRagConfig.QUALIFIER_AI_RAG`, `SpringAiMcpConfig.QUALIFIER_AI_MCP`). MCP server configuration lives in `src/main/resources/spring_ai_mcp/mcp-servers-config.json`.
- **Tests**: Integration tests are named `*IT.java`. `TestcontainersConfig` provisions Postgres **and** a Toxiproxy in front of it on a shared Docker network — so JPA tests can inject `Proxy dbProxy` and inject latency/bandwidth toxics (see `ContactRepositoryIT`). `TestDojoStarter.main` boots the full app against those containers for local exploration.

## Build & run

Backend (run from `backend/`):

```bash
sdk env                                 # activate Java 25 (.sdkmanrc → 25.0.2-tem)
./mvnw spring-boot:test-run             # runs DojoStarter via TestDojoStarter — Testcontainers manages Postgres+Toxiproxy
./mvnw spring-boot:run                  # runs against the compose Postgres (compose.yaml auto-started by spring-boot-docker-compose)
./mvnw verify                           # full build incl. unit + IT + JMeter smoke
./mvnw test -Dtest=ContactRepositoryIT  # single test class
./mvnw test -Dtest='ContactRepositoryIT$Search#by_email'   # single test method
```

JMeter smoke test (`backend/src/test/jmeter/smoke.jmx`) runs during `verify` via `jmeter-maven-plugin`; failures fail the build.

Frontend (run from `frontend/`):

```bash
npm start                # ng serve on :4200, proxies /api to :8080
npm run build            # production build
npm test                 # vitest (configured via @angular/build:unit-test)
```

The local stack assumes Ollama is reachable for the AI katas (`brew install ollama`).

## Conventions worth knowing

- **MapStruct** is preconfigured via compiler args: Spring component model, constructor injection, and `unmappedTargetPolicy=ERROR` — incomplete mappers fail the build, not runtime.
- **Lombok** is used freely (`@RequiredArgsConstructor`, `@Data`, etc.) and is excluded from the Spring Boot fat jar.
- Kata REST controllers expose their path as a `static final String API_PATH` constant inside the class — reuse the pattern when adding new katas.
- Liquibase changelog locks/log tables are renamed (`db_change_locks`, `db_change_logs`); keep that in mind if writing migrations or pointing tools at the DB.
- Actuator runs on a **separate port (8081)** with HTTP Basic — don't expect actuator routes under `/api`.
- Spring AI chat prompts and completions are logged (`spring.ai.chat.observations.log-*=true`) — useful when debugging AI katas, but be mindful that prompts hit the logs.

## Common pitfalls

- The kata briefings are in `package-info.java` files. Read them before changing a kata — they describe *what the trainee is supposed to do*, which is often deliberately wrong/incomplete in the current code. Don't "fix" a kata's gaps unless that's the task.
- Don't add cross-kata imports. Each kata is intentionally a closed package; if you find yourself reaching into another kata's package, you're probably solving the wrong problem.
