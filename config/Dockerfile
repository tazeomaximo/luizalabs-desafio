# Start with a base image containing Java runtime
FROM openjdk:8

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8082 available to the world outside this container
ENV HOST=0.0.0.0
EXPOSE 8082

# environment of app
ENV REDIS_HOST 127.0.0.1
ENV REDIS_PORT 6379
ENV MYSQL_HOST 127.0.0.1
ENV MYSQL_PORT 3306
ENV MYSQL_DATABASE desafioluizalabs
ENV MYSQL_USER_NAME desafio
ENV MYSQL_PASSWORD desafio
ENV TOKEN_REFRESH 600
ENV TOKEN_ACCESS_TIME 60
ENV URL_CHALLENGE http://challenge-api.luizalabs.com/api/product/
ENV PROFILES_ACTIVE prod
ENV Lang en_us.utf-8

# The application's jar file
ARG JAR_FILE=desafio.jar

# Add the application's jar to the container
ADD ${JAR_FILE} desafio.jar

# Run the jar file 
ENTRYPOINT ["java","-Dspring.profiles.active=${PROFILES_ACTIVE} -Dtoken.seconds.access=${TOKEN_ACCESS_TIME} -Dtoken.seconds.refresh=${TOKEN_REFRESH} -Dmysql.host=${MYSQL_HOST} -Dmysql.port={MYSQL_PORT} -Dmysql.database=${MYSQL_DATABASE} -Dspring.datasource.username=${MYSQL_USER_NAME} -Dspring.datasource.password=${MYSQL_PASSWORD} -Dspring.redis.host=${REDIS_HOST} -Dspring.redis.port=${REDIS_PORT} -Djava.security.egd=file:/dev/./urandom","-jar","/desafio.jar"]