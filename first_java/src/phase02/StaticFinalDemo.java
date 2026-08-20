package phase02;

public class StaticFinalDemo {
    public static void main(String[] args) {
        // ===== 1. static 字段：所有对象共享同一份 =====
        Student.schoolName = "清华大学";   // 类名直接访问，不用 new

        Student s1 = new Student("张三");
        Student s2 = new Student("李四");

        s1.show();  // 张三，学校：清华大学
        s2.show();  // 李四，学校：清华大学

        // 改一次，所有人都变了——因为共享同一份
        Student.schoolName = "北京大学";
        s1.show();  // 北京大学
        s2.show();  // 北京大学

        // ===== 2. static 方法：不用对象就能调 =====
        System.out.println("学生总数：" + Student.getCount());  // 2

        // ===== 3. final：一旦赋值就不能改 =====
        final int MAX_SPEED = 120;
        // MAX_SPEED = 150;  // ❌ 编译报错！final 不能改

        final Student s3 = new Student("王五");
        // s3 = new Student("赵六");  // ❌ 引用不能指向别的对象
        s3.name = "王五改名";         // ✅ 但对象内部的内容可以改！

        // ===== 4. static final：类常量 =====
        System.out.println("圆周率：" + Math.PI);  // Math.PI 就是 static final
        // Math.PI = 3.0;  // ❌ 不能改
    }
}

class Student {
    String name;                           // 实例字段：每个对象各有一份
    static String schoolName = "未设置";     // 静态字段：整个类只有一份
    private static int count = 0;          // 静态计数器

    Student(String name) {
        this.name = name;
        count++;  // 每 new 一个学生，计数器 +1
    }

    void show() {
        System.out.println(name + "，学校：" + schoolName);
    }

    static int getCount() {    // 静态方法：可以不创建对象直接调
        return count;
        // System.out.println(name);  // ❌ 静态方法不能访问实例字段！
    }
}
