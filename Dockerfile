FROM openjdk:17-jdk-slim
COPY ./build/libs/project-0.0.1-SNAPSHOT.jar /opt/service.jar
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://database:5432/postgres
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=ilia
EXPOSE 8080
CMD java -jar /opt/service.jar