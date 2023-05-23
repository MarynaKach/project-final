FROM openjdk:17
COPY target/jira-1.0.jar jira-1.0.jar
COPY ./resources /opt/resources
COPY ./resources/static/images/icon/favicon-32x32.png /opt/resources/static/images/icon/favicon-32x32.png
CMD ["java", "-jar", "jira-1.0.jar"]