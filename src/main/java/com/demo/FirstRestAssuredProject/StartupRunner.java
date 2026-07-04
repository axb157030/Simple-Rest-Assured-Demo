package com.demo.FirstRestAssuredProject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.demo.FirstRestAssuredProject.services.RestAssurredTest;

@Component
public class StartupRunner implements CommandLineRunner {

    private final RestAssurredTest restAssuredTestService;

    public StartupRunner(RestAssurredTest restAssuredTestService) {
        this.restAssuredTestService = restAssuredTestService;
    }

    @Override
    public void run(String... args) {
    	restAssuredTestService.hello();
    }
}

