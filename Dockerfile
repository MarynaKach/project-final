FROM openjdk:17
WORKDIR /opt
COPY target/jira-1.0.jar jira-1.0.jar
COPY ./resources /opt/resources
CMD ["java", "-jar", "jira-1.0.jar"]