FROM java:openjdk-8-jdk

EXPOSE 8081

ADD target/msTopCDs-swarm.jar /opt/msTopCDs-swarm.jar
ENTRYPOINT ["java", "-jar", "/opt/msTopCDs-swarm.jar"]
