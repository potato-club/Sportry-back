FROM openjdk:17-jdk AS build
WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew

RUN microdnf install -y findutils
RUN ./gradlew bootJar

ENV SPRING_PROFILES_ACTIVE=server
ENV JAVA_OPTS="-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}"

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/build/libs/sportry-0.0.1-SNAPSHOT.jar"]