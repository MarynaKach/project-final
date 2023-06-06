FROM openjdk:17
COPY target/jira-1.0.jar jira-1.0.jar
COPY ./resources /opt/resources
COPY ./resources/static /opt/resources/static
CMD ["java", "-jar", "jira-1.0.jar"]