package com.app.backend.api;

import com.app.backend.dto.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class api {

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }

    @GetMapping("/person")
    public Person getPerson(){
        return new Person("John",18);
    }
}
