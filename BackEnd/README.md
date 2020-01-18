**EventXpert API**

This API is a Spring API written in Java.

The code is split into 4 folders

*Controllers*
Controllers are responsible for delegating between user input and the repository layer.
All logic should occur here.

*Entites*
Entities are objects that provide a model for the database return types.

*Errors*
As they sound like they are errors that can be returned from the API.
They are thrown by the controllers when they need to be used.

*Repositories*
Repositories are interfaces whose implementation is provided by Spring.
They are used to communicate with the database.

**To Run**
To run the API run the command `./gradlew bootRun`.
 After this command finishes running you will be able to communicate with the API at `localhost:8080`.
 
**Testing**
Tests are in this project under a folder named test with the same folder structure as main, but with Test appended to the end of class names.
To run al the tests run the command `./gradlew test`. To run tests with coverage run the command `./gradlew jacocoTestCoverageVerification`.
If you need to disable a file from coverage add it to the appropriate section in `build.gradle`.