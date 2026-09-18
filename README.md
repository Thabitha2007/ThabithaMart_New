# ThabithaMart
Multi-seller fashion marketplace using Java 17, Tomcat 9, JSP/JSTL, vanilla JavaScript, H2, jBCrypt, SLF4J and Logback.

## Run
1. Install JDK 17, Maven and Tomcat 9.
2. From the project folder:
   `mvn clean package`
3. Copy `target/thabithamart.war` into Tomcat 9's `webapps` folder.
4. Start Tomcat with `bin\startup.bat`.
5. Open `http://localhost:8080/thabithamart/`

Optional Maven Tomcat command (the plugin is not in Maven's default groups):
`mvn org.apache.tomcat.maven:tomcat7-maven-plugin:2.2:run`
For Tomcat 9, the WAR deployment method above is recommended.

## Seeded account
- Admin username: `admin`
- Admin password: `Admin@123`

Change the seeded password before any real deployment. This is a local academic project.

## Git commit plan
- Commit 01: `git add pom.xml README.md src/main/resources && git commit -m "chore: initialize project and database scripts"`
- Commit 02: `git add src/main/java/.../model src/main/java/.../dao src/main/java/.../util && git commit -m "feat: add models database layer and utilities"`
- Commit 03: `git add src/main/java/.../controller src/main/java/.../filter src/main/java/.../listener && git commit -m "feat: add authentication and request flow"`
- Commit 04: `git add src/main/webapp && git commit -m "feat: add landing page and JSP UI"`
- Commit 05: `git commit -am "feat: add seller product management"`
- Commit 06: `git commit -am "feat: add browsing search and cart"`
- Commit 07: `git commit -am "feat: add transactional checkout and orders"`
- Commit 08: `git commit -am "feat: add admin moderation and reviews"`

## Scope note
This is a runnable academic starter implementation covering the requested flow. Extend validation, CSRF protection, production secrets, and payment integration before production use.
