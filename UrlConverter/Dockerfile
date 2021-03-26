FROM openjdk:8

ADD target/urlconverter-0.0.1-SNAPSHOT.war urlconverter.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "urlconverter.war"]