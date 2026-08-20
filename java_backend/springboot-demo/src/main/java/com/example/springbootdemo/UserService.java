package com.example.springbootdemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findById(Integer id){
        return userMapper.findById(id);
    }

    public List<User> findAll(){
        return userMapper.findAll();
    }

    public void insert(User user){
        userMapper.insert(user);
    }

    public int update(User user){
        return userMapper.update(user);
    }

    public int delete(Integer id){
        return userMapper.delete(id);
    }

    @Transactional
    public void addTwoUsers() {
        // 第一步：插入合法用户（deptId=2 是存在的）
        User ok = new User();
        ok.setName("ok_user");
        ok.setAge(20);
        ok.setStatus("1");
        ok.setGender("M");
        ok.setDeptId(2);
        userMapper.insert(ok);

        // 第二步：插入违反外键的用户（deptId=999 不存在），必然报错
        User bad = new User();
        bad.setName("bad_user");
        bad.setAge(20);
        bad.setStatus("1");
        bad.setGender("M");
        bad.setDeptId(999);          // dept 表没有 999，外键约束会拒绝
        userMapper.insert(bad);      // 这里抛异常，方法中断
    }

}
