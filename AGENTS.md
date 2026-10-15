# Agent guide

CUSR is a Fall 2017 CMPE 275 course project: a California Ultra Speed Rail ticket API. `master` is the Spring Boot service. The React client is only on `react-code`. Do not merge that branch. The two roots are different projects; the repository description says to switch branches for the React code.

Read `README.md` for the domain (stations A–Z, express vs local, fares, cancellation window). This file is how to change the backend safely.

## Commands

Requires JDK 21. Maven is `./mvnw` (3.9.16).

```bash
./mvnw test            # unit and MockMvc tests, H2 in memory
./mvnw verify          # tests plus Spotless
./mvnw spotless:apply  # format Java before verify
./mvnw spring-boot:run # http://localhost:8080, file H2 under ./data
```

CI is `.github/workflows/ci.yml` and runs `./mvnw -B verify`.

## Layout

- `controller` maps HTTP. Keep response strings stable; tests and the old client depend on them (`It's too late`, `Email already exists.`, `Invalid Credentials!`).
- `service` holds booking, search, users, and mail. `TicketServiceImpl` owns seat counts and cancellation.
- `dao` is Spring Data `CrudRepository`. Use `findById` / `deleteById` / `existsById`.
- `util/Search.java` is the schedule. It is characterization-tested. Do not restyle the date math.
- `model` entities use explicit `@Column` names (`firstName`, `userId`, table `user`). Do not let Hibernate rename them to snake case.

## Conventions

- Java 21, Spring Boot 4.1.1, Jakarta (`jakarta.persistence`, `jakarta.servlet`, `jakarta.mail`).
- Format with Spotless (Palantir Java Format) rather than hand-wrapping. `verify` fails the build when the format drifts.
- Constructor injection is fine for new code. Existing services use field `@Autowired`; match the file you are editing.
- Prefer env vars over new entries in `application.properties`. Never commit passwords, SMTP credentials, or database hosts.
- Tests live under `src/test/java` and use JUnit 5. Pure tests (`SearchScheduleTest`, `FareTest`, `EmailBodyTest`) do not start Spring. `ApiIntegrationTest` uses MockMvc and the properties in `src/test/resources`.

## Behavior you should not "clean up" by accident

- Search fares floor the 5-station block count. `GET /cost` and `TicketServiceImpl.cost` round a remainder up. Both are tested. They disagree on purpose.
- Express multiplier is 2 when `trainId.substring(4, 6)` is `00`, otherwise 1.
- A booked train id is direction + `HHmm` + `MM/dd/yyyy` (`SB235012/20/2099`). Departure is stored as `HHmm` + the date (`235012/20/2099`) and parsed with `HHmmMM/dd/yyyy`. Arrival is `HH:mm` + the date. `getEmailBody` has to accept both.
- Passenger cancel is refused in the final hour with `IllegalStateException`, which the controller turns into HTTP 403. `trainCancel` bypasses that window. The no-arg `cancelTrain` method is an empty stub; do not wire the controller to it.
- Seat count on `Train` is seats **taken**, not seats left. `getSeatAvailable` returns `TOTAL_SEATS - taken`. `TOTAL_SEATS` is a static default of 1000. `/reset` changes it only until the process exits.
- Cancelling releases seats and deletes the passenger and ticket-detail rows. The original code did not, so cancelled seats stayed occupied.
- `BookRequest` accepts `userId` or `userid`, and `passengers` or `passenger`, because the React client sent the short names.
- Mail is best-effort. With no `SPRING_MAIL_USERNAME`, `EmailServiceImpl` logs and returns. When SMTP is set, a send failure still fails the booking, as before.
- Passwords are plain text and are returned on login. Leave that unless you are deliberately changing the project.
- CORS allows the request origin plus GET, POST, PUT, DELETE, OPTIONS. The React app needs PUT for profile updates.

## Gotchas

- Table name `user` is reserved. `hibernate.globally_quoted_identifiers` is on so H2 and MySQL will quote it. Do not drop that property.
- Default datasource is `jdbc:h2:file:./data/cusr`. Tests replace it with `jdbc:h2:mem:cusrtest`. Do not point tests at `./data`.
- `@Transactional(rollbackFor = Exception.class, noRollbackFor = IllegalStateException.class)` on `TicketServiceImpl` rolls back a failed booking but does not poison the request when a rider is simply too late to cancel.
- `SearchServiceImpl.search` prints to stdout and can return duplicate rows. `SearchResult` has no `equals`/`hashCode`, so the `HashSet` there does not dedupe.
- `Search.java` calls deprecated `Date` mutators and shares `Date` instances. Characterization tests lock a few ids and arrival times; add more before changing it.
- Jackson 3 is the HTTP mapper. Keep `@JsonAlias` in `com.fasterxml.jackson.annotation`.
- Old RDS and Gmail credentials were removed from `application.properties`. They are still in git history. Do not paste them back. Do not put secrets in docs.

## React branch

Checkout `react-code` only to read the client. It is CRA 1 / React 16, talks to a hardcoded host, and posts to `/login` rather than `/userlogin`. Do not "fix" it from `master`, and do not merge it, unless the owner explicitly asks for a frontend overhaul.
