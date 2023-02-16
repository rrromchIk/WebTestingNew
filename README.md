# WebTesting

## Project description:

WebApplication based on MVC pattern that provides test-passing for users. For system is provided:
  -  user management system:
      - authentication service
      - authorization service
      - forgot password service
  - access mediation
  - support of 2 languages(UA, ENG)
  - pagination mechanizm
  - logging
  - module testing
  - documentation

### For implementation are used: 
```
Java servlet API, Servlet Filters and Event Listeners, Jsp(JavaServer pages), 
JUnit5, Log4j2, JavaMail API, JDBC API, iText(for pdf building)
```
### Design Patterns used: 
```
Command, Builder, Singleton
```
---

## Task:

```
The student registers in the system and after registration can pass one or more Tests.
There is a list of tests in the system. For the list it is necessary to implement:
  - choice of tests on a particular subject;
  - sorting tests by name;
  - sorting tests by difficulty;
  - sorting tests by number of queries.
  
The student chooses the test and passes it. A certain period of time is allocated for 
passing the test, which is set for each test separately. The student has a personal account,
which displays registration information, as well as a list of passed tests with the results.
The system administrator:
  - creates, deletes or edits tests;
  - blocks, unblocks, edits the user.
  
When creating a test, the administrator:
  - sets the test time;
  - sets the complexity of the test;
  - adds Questions to the test.
  
A question can have one or more correct answers. The result of the test is the percentage
of questions that the student answered correctly in relation to the total number of questions
(it is considered that the student answered the question correctly if his answer coincides 
exactly with the correct answers).
```
---
## Database MySql

```
- You can create on your local machine by running sql/db_create.sql script
- In order to configure db, you should change src/main/webapp/META-INF/context.xml
```
` Here you can see db schema:`

  ![image](https://user-images.githubusercontent.com/90086332/215562680-89894f8b-b79e-4158-b9af-50a5ddfaf8ea.png)
  
---
## Logging Log4j2
```
- You can see logs in logs/logs.log
- In order to configure logging, check src/main/resurces/log4j2.xml
```
---
## Running
```
1. Clone this project
2. Create and configure database
3. Configure Logging
4. Download a Tomcat v9 
5. Add a tomcat configuration and Run
```
