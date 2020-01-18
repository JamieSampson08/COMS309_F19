*Spring Example*

**Running API**
1. Create a Database named `meganTest` with the table `user` and two columns `test_id` and `name`
2. From your terminal run the command `./gradlew bootRun`
3. `GET` `http://localhost:8080/greeting` returns a list of greetings for all the users currently in the database
4. `GET` `http://localhost:8080/greeting/{userId}` return a greeting for the user whose id corresponds to the one entered
5. `POST` `http://localhost:8080/greeting` with the body `{name: ${desiredName}}` returns a greeting and adds a new user to the database
6. `PUT` `http://localhost:8080/greeting/{userId}` allows you to modify users and returns a greeting for the modified user.

**Running Tests**
1. From your terminal run the command `./gradlew test`.
2. In order to enforce 100% test coverage (expect for Application.java, since it was a little wierd with PowerMock) run the command `./gradlew build jacocoTestCoverageVerification`