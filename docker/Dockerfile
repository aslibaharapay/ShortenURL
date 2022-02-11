# Alpine Linux with OpenJDK JRE
FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
# Copy  file
COPY api-0.0.1-SNAPSHOT.jar /shortenurl-api.war

EXPOSE 8443

CMD ["java", "-jar","/shortenurl-api.war"]