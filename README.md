# urlshortener
A Service to shorten large URLs

To run the service, you need to have Java 17 and Maven installed.
First, make sure you run

``mvn clean install``

This creates the jar file in /target directory.
Post that you can run the service using the following command

``java -jar target/urlshortener-1.0-SNAPSHOT.jar server config/local.yml``

Now that your application is running, head over to ``localhost:9090/swagger`` and explore the APIs.
