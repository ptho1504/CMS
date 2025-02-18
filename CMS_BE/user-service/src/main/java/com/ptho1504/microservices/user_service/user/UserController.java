package com.ptho1504.microservices.user_service.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @GetMapping("hello-world")
    public void helloWorld() {
        System.out.println("Hello world");
    }
}
