package org.example;


import java.lang.reflect.Method;

@Component
public class HelloController {
    @GetMapping("/hello")
    public static String hello() {
    return "Hello world!";
    }

    @GetMapping("/helloName")
    public static String helloName(String name) {
        return  "Hello" + name;
    }
}