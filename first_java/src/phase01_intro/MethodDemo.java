package phase01_intro;

public class MethodDemo {
    public static void main(String[] args) {
        // ===== 1. this：谁调用我，this 就是谁 =====
        Student s1 = new Student();
        s1.setName("张三");   // setName 里面 this = s1
        s1.setScore(85);

        Student s2 = new Student();
        s2.setName("李四");   // 这次 setName 里面 this = s2
        s2.setScore(42);

        System.out.println("=== 两个学生 ===");
        s1.show();    // show 里面 this = s1
        s2.show();    // show 里面 this = s2

        // ===== 2. 构造方法：创建对象时自动调用 =====
        System.out.println("\n=== 用构造方法创建 ===");
        Vehicle v1 = new Vehicle("宝马", 200);
        Vehicle v2 = new Vehicle("小米", 180);
        v1.showInfo();
        v2.showInfo();

        // ===== 3. static vs 非 static =====
        // 非 static 方法：必须通过对象调用
        s1.show();   // ✅ 可以
        // Student.show();  // ❌ 不行！没对象没法调

        // static 方法：通过类名直接调用，不需要对象
        Student.printSchool();  // ✅ 静态方法，类名直接调
        // 像你天天用的 Math.abs(-5)、Arrays.toString(arr) 都是静态方法
    }
}

// ============ Student 类 ============
class Student {
    String name;
    int score;
    static String school = "北京大学";  // static 字段：所有学生共享同一个

    // ---- 非静态方法：通过 this 访问当前对象 ----
    void setName(String name) {
        // this.name = 字段（当前对象的 name）
        // name      = 参数（最近的那个）
        this.name = name;  // this 区分了字段和参数！
    }

    void setScore(int score) {
        this.score = score;
    }

    void show() {
        // 没歧义的时候 this 可以省略，编译器自动帮你加
        System.out.println(name + "：" + score + "分，学校：" + school);
        // 等价于：System.out.println(this.name + "：" + this.score + "分");
    }

    // ---- 静态方法：属于类本身，不属任何对象 ----
    static void printSchool() {
        System.out.println("校名：" + school);
        // System.out.println(name);  // ❌ 静态方法不能访问非静态字段！
        // 因为不知道是哪个对象的 name
    }
}

// ============ Vehicle 类（不用 Car 因为 Car.java 已存在） ============
class Vehicle {
    String brand;
    int speed;

    // 构造方法：名字和类名一样，没有返回值类型
    Vehicle(String brand, int speed) {
        this.brand = brand;
        this.speed = speed;
    }

    // 方法重载：多个构造方法，参数不同
    Vehicle() {
        this.brand = "未知";
        this.speed = 0;
    }

    void showInfo() {
        System.out.println(brand + "，时速" + speed + "km/h");
    }
}
