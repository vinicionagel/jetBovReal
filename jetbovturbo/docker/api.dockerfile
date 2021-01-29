FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=./rest/target/rest-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/app.jar"]


#para subirrrr o file abrir terminar e pa...
#buildar a imagem
#docker build -t jetbov -f docker/api.dockerfile .
#docker run -p 8090:8090 jetbov