# Step 1: Maven வெச்சு project-ஐ build பண்ணி .war file உருவாக்குறது
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: அந்த .war file-ஐ Tomcat server-க்குள்ள வெச்சு run பண்றது
FROM tomcat:9-jdk17
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY --from=build /app/target/ThabithaMart.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]