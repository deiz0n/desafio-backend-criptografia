FROM azul/zulu-openjdk:17.0.11

WORKDIR /app

COPY /target/*.jar /app/cripto.jar

EXPOSE 8080

CMD ["java", "-jar", "cripto.jar"]