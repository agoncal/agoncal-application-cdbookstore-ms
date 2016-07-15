FROM java:openjdk-8-jdk

EXPOSE 8082

ADD target/msTopBooks-swarm.jar /opt/msTopBooks-swarm.jar
ENTRYPOINT ["java", "-jar", "/opt/msTopBooks-swarm.jar"]
