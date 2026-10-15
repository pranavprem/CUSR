# California Ultra Speed Rail

Ticket-booking API for a fictional California rail line whose stations are the letters A through Z. Riders search for one-way or round-trip itineraries, book seats for up to a party of passengers, and cancel before departure. Cancelling a train run notifies every rider on it.

This repository is the **Spring Boot backend**. The React client lives on the [`react-code`](https://github.com/pranavprem/CUSR/tree/react-code) branch and was not merged here. See [Where the React client lives](#where-the-react-client-lives).

## Original context

Team 10's final project for **CMPE 275, Enterprise Application Development**, San Jose State University, Fall 2017. The last commit on `master` is 22 December 2017.

| Name | Email | Student ID |
| --- | --- | --- |
| Aaditya Deowanshi | aaditya.deowanshi@sjsu.edu | 011808850 |
| Pranav Prem | pranav.prem@sjsu.edu | 011549707 |
| Saumya Bhasin | saumya.bhasin@sjsu.edu | 011816311 |
| Vinayak Nigam | vinayak.nigam@sjsu.edu | 011822213 |

The team deployed the API on Heroku at `http://cusr.herokuapp.com/` and pointed the React app at an EC2 host. Both of those deployments are long gone. The app now runs locally on Java 21 with an embedded H2 database, and can still be pointed at MySQL.

## How it works

Stations sit on one line, A at one end and Z at the other.

- **Local trains** stop at every station. The scheduler names them with a direction and a departure time, for example `SB0915` (southbound, 09:15) or `NB1030` (northbound, 10:30).
- **Express trains** are the ones whose departure time ends in `00`. They stop at every fifth station: A, F, K, P, U, and Z. A trip that starts or ends off that grid is built as local + express + local.
- Search walks the next two hours in 15-minute steps and returns itineraries with trains, the station route, a fare, the number of seats left, and an arrival time. Round trips repeat the search in the other direction.
- At booking time the client appends a date, so a train id looks like `SB235012/20/2099` (`SB2350` + `MM/dd/yyyy`). The API stores departure as `HHmm` plus that date, which is also how the one-hour cancellation window is parsed.
- Each train run starts with **1000 seats**. Booked seats are counted on the train row. `GET /reset?seats=` wipes tickets and changes that capacity for the current process only; a restart goes back to 1000.
- A rider can cancel until one hour before the first train departs. After that the API responds `It's too late`. Cancelling the train itself (`GET /cancel`) removes every ticket on that train even inside the final hour, and emails those riders when SMTP is configured.

Fares are the number of 5-station blocks between the origin and destination letters, times the passenger count, times a per-train multiplier (2 for an express `:00` train, otherwise 1). `GET /cost` rounds a partial block up. The fare attached to search results uses the floor instead, so a hop shorter than five stations is quoted as 0 by search. That split is original and is covered by tests so it does not drift.

There is no UI on this branch, so this README does not include a screenshot. The interface is the React app on `react-code` (login, search, booking, ticket history). That app does not start on a current Node release; see below.

## Tech stack

| Piece | Then (2017) | Now |
| --- | --- | --- |
| Language | Java 8 | Java 21 |
| Framework | Spring Boot 1.5.9 | Spring Boot 4.1.1 |
| Persistence | Hibernate + MySQL on AWS RDS | Spring Data JPA, H2 by default, MySQL optional |
| Mail | Gmail SMTP | Spring Mail, off unless you set SMTP env vars |
| Time math | Joda-Time 2.8.1 | Joda-Time 2.15.0 |
| Build | Maven 3.5.2 | Maven 3.9.16 via `./mvnw` |

JSON is Jackson 3, which ships with Spring Boot 4. Request bodies accept the original field names plus the ones the React client actually sent (`userid`, `passenger`).

## Run it today

Install JDK 21, then:

```bash
./mvnw spring-boot:run
```

The API listens on [http://localhost:8080](http://localhost:8080). Ticket data is stored in `./data/` (gitignored).

```bash
curl http://localhost:8080/hello
curl "http://localhost:8080/search/A/F/08:00/No/00:00"
curl "http://localhost:8080/cost?from=A&to=G&count=2&trainFare=1"
```

Create a rider and book the 23:50 southbound on 20 December 2099:

```bash
curl -X POST "http://localhost:8080/user?firstName=Ada&lastName=Lovelace&email=ada@example.com&password=secret"
curl -X POST http://localhost:8080/1/ticket/book \
  -H 'Content-Type: application/json' \
  -d '{"userId":1,"trains":["SB235012/20/2099"],"route":["A","C"],"cost":10,"passengers":["Ada"]}'
```

### Configuration

Defaults live in `src/main/resources/application.properties`. Override them with environment variables (see `.env.example`). Do not commit real passwords.

| Variable | Purpose |
| --- | --- |
| `SPRING_DATASOURCE_URL` | JDBC URL. Default is a file-backed H2 database. Use `jdbc:mysql://host:3306/cusr` for MySQL. |
| `SPRING_DATASOURCE_USERNAME` | Database user. Default `sa`. |
| `SPRING_DATASOURCE_PASSWORD` | Database password. Default empty. |
| `SPRING_MAIL_HOST` | SMTP host. Mail is skipped when this is unset, and booking still succeeds. |
| `SPRING_MAIL_PORT` | SMTP port, usually `587`. |
| `SPRING_MAIL_USERNAME` | SMTP username. |
| `SPRING_MAIL_PASSWORD` | SMTP password. |
| `CUSR_MAIL_FROM` | From address. Default `noreply@localhost`. |

### Tests

```bash
./mvnw verify
```

`verify` runs the tests and the Spotless format check. `./mvnw spotless:apply` rewrites Java sources to match.

GitHub Actions (`.github/workflows/ci.yml`) runs the same command on Java 21.

## API

| Method | Path | What it does |
| --- | --- | --- |
| GET | `/hello`, `/home` | Liveness strings. |
| POST | `/user` | Register. Form fields `firstName`, `lastName`, `email`, `password`, optional `token`. Email must be unique. |
| POST | `/userlogin` | Form fields `user` (email) and `password`. |
| GET | `/sociallogin?email&name` | Return the user, or create one. |
| GET | `/user/{id}` | Fetch a user. |
| PUT | `/user/{id}` | Update a user. The same email is allowed; another account's email is not. |
| DELETE | `/user/{id}` | Delete a user. |
| GET | `/search/{from}/{to}/{time}/{returnTrip}/{time2}` | Itineraries. `from` and `to` are station letters, times are `HH:mm`, `returnTrip` is `Yes` or `No`. |
| GET | `/cost?from&to&count&trainFare` | Quoted fare. Partial 5-station blocks round up. |
| POST | `/{id}/ticket/book` | JSON body: `userId`, `trains`, `route`, `cost`, and `passengers` (or `passenger`). |
| GET | `/{id}/ticket` | That rider's tickets. |
| GET | `/{id}/ticket/{tid}` | One ticket. |
| DELETE | `/{id}/ticket/{tid}` | Cancel. `403` with `It's too late` inside the final hour. |
| GET | `/ticket` | Every ticket. |
| GET | `/seats?trainId` | Seats left on a train run. |
| GET | `/cancel?trainId` | Cancel a train and its tickets. |
| GET | `/reset?seats` | Delete ticket data and set the in-memory capacity. |

Passwords are stored and returned in plain text. That matches the original project and is not something to expose beyond a class demo.

## Project structure

```
src/main/java/com/cmpe275/cusr/
  CUSRApplication.java          entry point
  controller/                   HTTP adapters
  service/                      booking, search, users, mail
  dao/                          Spring Data repositories
  model/                        JPA entities and the book-request body
  util/Search.java              train-id and arrival-time math
  util/SearchResult.java        one itinerary
  util/SimpleCORSFilter.java    browser access for the React client
src/main/resources/application.properties
src/test/java/                  schedule, fare, mail body, and API tests
```

`TicketDetail` is the link between a ticket and a train run. The `Train.ticket` association is not what booking uses.

## Where the React client lives

`master` and `react-code` forked into two different trees. `master` is this Maven project. `react-code` is a Create React App 1.0.13 / React 16 client (last commit 20 December 2017) whose README is still the CRA boilerplate. The footer reads "CMPE 275 Project for Fall 2017". Pages cover login (including Facebook and Google buttons), registration, search, booking, and ticket history.

They are separate projects that share history, not two halves of one build. Merging them would replace one root or invent a layout the team never used, so this refresh leaves `react-code` untouched.

```bash
git fetch origin react-code
git checkout react-code
```

Expect it to need Node from the 2017 era (`react-scripts` 1.0.13). On a current Node it does not install and start cleanly, which is why there is no screenshot in this README. The client also calls a hardcoded EC2 address, posts login to `/login` (this API's path is `/userlogin`), and has a Cancel Train button whose handler is misspelled (`cance`). Treat that branch as the historical UI, not as a supported app.

## Left as it was

The search scheduler in `Search.java` still mutates `java.util.Date` and still prints debug lines. The empty `cancelTrain` method is unused; `trainCancel` is the one the controller calls. `TrainKey` is unused. Search results can repeat because `SearchResult` has no `equals`. None of that was rewritten, so the itineraries stay the ones the class project produced.
