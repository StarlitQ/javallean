package phase05_project;
import java.util.*;
public class StudentMS {
    public static void main(String[] args) {
        StudentManager sm = new StudentManager();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== 学生管理系统 =====");
            System.out.println("1. 添加学生");
            System.out.println("2. 查看全部");
            System.out.println("3. 查看平均分");
            System.out.println("4. 删除学生");
            System.out.println("5. 退出");
            System.out.print("请选择：");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("姓名：");
                    String name = sc.next();
                    System.out.print("年龄：");
                    int age = sc.nextInt();
                    System.out.print("成绩：");
                    double score = sc.nextDouble();
                    sm.add(new Student(name, age, score));
                    System.out.println("添加成功！");
                    break;
                case 2:
                    sm.displayAll();
                    break;
                case 3:
                    System.out.println("平均分：" + sm.getAverage());
                    break;
                case 4:
                    System.out.print("删除索引：");
                    int index = sc.nextInt();
                    sm.remove(index);
                    System.out.println("已删除");
                    break;
                case 5:
                    System.out.println("再见！");
                    break;
                default:
                    System.out.println("请输入1-5");
            }
        } while (choice != 5);

        sc.close();
    }
}

abstract class Person{
    String name;
    private int age;
    Person(String name, int age){
        this.name = name;
        this.age = age;
    }
    abstract String getRole();
    void showInfo(){
        System.out.println("name:"+name+",age:"+age);
    }
    int getAge(){
        return age;
    }
}

class Student extends Person implements Displayable{
    private double score;
    Student(String n, int a, double s){
        super(n, a);
        score = s;
    }
    @Override
    String getRole(){
        return "Student";
    }
    @Override
    void showInfo(){
        System.out.println("name:" + name + ", age:" + getAge()
        + ", score:" + score + ", " + (score >= 60 ? "及格" : "不及格"));
        // super.showInfo();
        // System.out.println(",score:"+score);
        // System.out.println("result is:"+(score>=60?true:false));
    }
    double getScore(){
        return score;
    }
    void setScore(double s){
        if(s>=0 && s<=100){
            score = s;
        }
    }
    @Override
    public String toDisplayString(){
        return "name:"+name+", age:"+getAge()+", score:"+score;
    }
}

interface Displayable{
    String toDisplayString();
}

interface Manageable<T>{
    void add(T item);
    void remove(int index);
    T get(int index);
    List<T> getAll();
    int size();
}

class StudentManager implements Manageable<Student>{
    private ArrayList<Student> students = new ArrayList<>();
    @Override public void add(Student t){
        students.add(t);
    }
    @Override public void remove(int index){
        students.remove(index);
    }
    @Override public Student get(int index){
        return students.get(index);
    }
    @Override public ArrayList<Student> getAll(){
        return students;
    }
    @Override public int size(){
        return students.size();
    }
    void displayAll(){
        for (Student t : students){
            System.out.println(t.toDisplayString());
        }
    }

    double getAverage(){
        double ave = 0;
        for (Student t : students){
            ave+=t.getScore();
        }
        ave /= students.size();
        return ave;
    }
}