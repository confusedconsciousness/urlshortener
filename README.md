# URLShortener: A Dropwizard Application

A Service to shorten large URLs

To run the service, you need to have Java 17 and Maven (3.9.8) installed.
First, make sure you run. If you are on Windows, installing Maven can be challenging. There are a bunch of tutorials
provided please refer one of them and then follow the steps.

``mvn clean install``

This creates the jar file in /target directory.
Post that you can run the service using the following command

``java -jar target/urlshortener-1.0-SNAPSHOT.jar server config/local.yml``

Now that your application is running, head over to ``localhost:9090/swagger`` and explore the APIs.
