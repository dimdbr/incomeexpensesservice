FROM openjdk:14
EXPOSE 8081
ADD target/parking-incomeexpensesservice.jar parking-incomeexpensesservice.jar
ENTRYPOINT ["java","-jar","/parking-incomeexpensesservice.jar"]