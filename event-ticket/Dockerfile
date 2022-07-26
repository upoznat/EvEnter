FROM openjdk:11
ADD /target/docker-event-ticket.jar docker-event-ticket.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "docker-event-ticket.jar", "-Dspring.profiles.active=dev"]






