FROM openjdk:8
EXPOSE 9000
ADD target/taskmanager-0.0.1-SNAPSHOT.jar taskmanager-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "taskmanager-0.0.1-SNAPSHOT.jar"]
