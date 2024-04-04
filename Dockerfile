FROM openjdk:17
EXPOSE 8080
ADD target/iss-loc.jar iss-loc.jar
ENTRYPOINT [ "java", "-jar","/iss-loc.jar" ]