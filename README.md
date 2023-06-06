## [REST API](http://localhost:8080/doc)

## Концепция:
- Spring Modulith
  - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
  - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
  - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```
- Есть 2 общие таблицы, на которых не fk
  - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
  - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем проверять

## Аналоги
- https://java-source.net/open-source/issue-trackers

## Тестирование
- https://habr.com/ru/articles/259055/

## JiraRush

The final project of JavaRush Unversity.

## Start Application
To strat the application you have to use command in terminal:
Docker compose up -d
All sensitive information should be in the .env file, or you should change application.yaml file

## The list of the tasks that should be done

1. Understand the project structure (onboarding). 

    <span style="font-weight: bold;">✅ Done</span>
2. Remove social networks: vk, yandex.
 
    <span style="font-weight: bold;">✅ Done</span>
3. Extract sensitive information (database login, password, OAuth registration/authentication identifiers, email settings) to a separate properties file. The values of these properties should be read from environment variables upon server startup. 

    <span style="font-weight: bold;">✅ Done</span>
4. Refactor tests to use an in-memory database (H2) instead of PostgreSQL. To achieve this, define two beans, and the selection of which one to use should be determined by the active Spring profile.

    <span style="font-weight: bold;">✅ Done</span>, I did this task very quickly, but I was told that I should connect to H2 console, I couldn't manage to do it, so I decided that my configuaration are wrong, so I spent 3 days trying to fix it but it wasn't necessary at all
5. Write tests for all public methods of the ProfileRestController controller.

    <span style="font-weight: bold;">✅ Done</span>, tests were the harderst task for me, I suppose I have to practice in test writing
6. Add new functionality: adding tags to a task. Front-end development is optional.

    <span style="font-weight: bold;">✅ Done</span>, without fron-end
7. Add the ability to subscribe to tasks that are not assigned to the current user. (No need to implement notification/email sending for task status changes). Front-end development is optional.
 
    <span style="font-weight: bold;">✅ Done</span>
8. Add automatic calculation of the time a task spent in the "in progress" and "testing" states. Write two methods in the service layer that take a task as a parameter and return the time spent:
  How long the task was in the "in progress" state (ready minus in progress).
  How long the task was in the "testing" state (done minus ready).
  To complete this task, add three entries to the ACTIVITY table in the database changelog.sql script:
  Start time of task development - in progress
  End time of task development - ready
  End time of task testing - done
  
    <span style="font-weight: bold;">✅ Done</span>
9. Write a Dockerfile for the main server.

    <span style="font-weight: bold;">✅ Done</span>
10. Write a docker-compose file to run the server container along with the database and nginx. Use the config/nginx.conf file for nginx. Edit the config file if necessary.

    <span style="font-weight: bold;">✅ Done</span>
11. Add localization for at least two languages to email templates (mails) and the index.html landing page.

    <span style="font-weight: bold;">✅ Done</span>
12. Implement a backlog - a complete list of tasks (with pagination) that need to be completed and are not yet assigned to any sprint. (back-end + front-end). 

    <span style="font-weight: bold;">✅ Done</span>
13. Refactor the mechanism of "authentification" between the front-end and back-end from JSESSIONID to JWT. One of the challenges is to modify form submission from the front-end to include the authentication header.

    <span style="color: red;">❌ Not done</span>. I spent over two weeks on this task, you can find some classes that were supposed to use to complete this task, but I could not understand how to make the authentification using new AuthenticationRestController or new LoginController. 
