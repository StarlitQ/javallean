package com.example.springbootdemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public Result getUser(@PathVariable Integer id){
        User user = userService.findById(id);
        return Result.success(user);
    }

    @GetMapping("/users")
    public Result listUsers(){
        return Result.success(userService.findAll());
    }

    @PostMapping("/user")
    public Result addUser(@RequestBody User user){
        userService.insert(user);
        return Result.success();
    }

    @PutMapping("/user/{id}")
    public Result updateUser (@PathVariable Integer id, @RequestBody User user){
        user.setId(id);
        int count = userService.update(user);
        if (count > 0) return Result.success();
        else{
            return Result.error("failed to update, havn't found user id = "+ id);
        }

    }

    @DeleteMapping("/user/{id}")
    public Result deleteUser(@PathVariable Integer id){
        int count = userService.delete(id);
        if (count > 0) return Result.success();
        else {
            return Result.error("failed to delete");
        }
    }

    @GetMapping("/demo/tx")
    public Result demoTx() {
        try {
            userService.addTwoUsers();
            return Result.success();
        } catch (Exception e) {
            return Result.error("第二步失败了，方法中断");
        }
    }

}
