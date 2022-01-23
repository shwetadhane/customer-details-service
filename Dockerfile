FROM openjdk:11
EXPOSE 8081
ADD target/customer-details-service.jar customer-details-service.jar

ENTRYPOINT ["java","-jar","/customer-details-service.jar"]