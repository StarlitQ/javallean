package com.example.springbootdemo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;
import java.util.HashMap;
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello world";
    }
    @GetMapping("/info")
    public Map<String, Object> getInfo(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zhangxiyuan");
        map.put("age", 20);
        return map;
    }
    @GetMapping("/hello/{name}")
    public String helloName(@PathVariable String name){
        return "Hello " + name +"!";
    }
    @GetMapping("/greet")
    public String greet(@RequestParam String name){
        return "Hey " + name + "~";
    }
}
