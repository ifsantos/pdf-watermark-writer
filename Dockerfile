FROM openjdk:17
WORKDIR /usr/src/java
#RUN ./mvnw clean install
#CMD ["java", "-jar", "./target/pdf-watermark-writer-0.0.1-SNAPSHOT.jar"]
CMD ["tail", "-f", "/dev/null"]
