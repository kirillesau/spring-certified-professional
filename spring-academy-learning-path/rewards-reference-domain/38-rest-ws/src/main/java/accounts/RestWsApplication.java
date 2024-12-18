package accounts;

import config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfig.class)
@EntityScan("rewards.internal")
public class RestWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestWsApplication.class, args);
    }

    // In this lab, you are going to exercise the following:
    // - Implementing controller handlers that handle HTTP POST, PUT, DELETE requests
    // - Using proper annotation for extracting data from incoming request
    // - Creating URI for a newly created item in handling HTTP POST request
    // - Exercising RestTemplate for sending HTTP requests to the server application

    //  Run this Spring Boot application
    // - IMPORTANT: Make sure that you are not still running an application
    //   from a previous lab.
    // - Verify you can reach http://localhost:8080 from a browser or curl.

}
