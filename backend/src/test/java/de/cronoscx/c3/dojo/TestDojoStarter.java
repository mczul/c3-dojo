package de.cronoscx.c3.dojo;

import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

public class TestDojoStarter {

    static void main(String[] args) {
        SpringApplication.from(DojoStarter::main).with(TestcontainersConfiguration.class).run(args);
    }

}
