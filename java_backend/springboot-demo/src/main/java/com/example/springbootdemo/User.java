package com.example.springbootdemo;

public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String status;
    private String gender;
    private Integer deptId;
    private String deptName;

    public Integer getId(){return id;}
    public void setId(Integer id){this.id = id;}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getDeptId() { return deptId; }
    public void setDeptId(Integer deptId) { this.deptId = deptId; }

    public String getDeptName(){return deptName;}
    public void setDeptName(String deptName){this.deptName = deptName;}

}
