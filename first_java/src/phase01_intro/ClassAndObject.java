package phase01_intro;

// ============ 类 = 蓝图 = 自定义类型 ============
class Dog {
    // 字段：描述"狗有什么"
    String name;
    int age;

    // 方法：描述"狗能做什么"
    void bark() {
        System.out.println(name + "：汪！");
    }
}

public class ClassAndObject {
    public static void main(String[] args) {
        // ===== 类 vs 对象 =====
        // Dog 是类（类型）—— 只有一个
        // 下面的 d1、d2 是对象（实例）—— 可以有很多个

        Dog d1 = new Dog();  // new = 按照 Dog 蓝图造一只真狗
        d1.name = "大黄";
        d1.age = 3;

        Dog d2 = new Dog();  // 同一张蓝图，再造一只
        d2.name = "小白";
        d2.age = 1;

        d1.bark();  // 大黄：汪！
        d2.bark();  // 小白：汪！

        // ===== 为什么所有代码都必须在类里？=====
        // 因为 Java 是"纯面向对象"的——一切皆对象
        // 连 main 方法自己也得待在某个类里
        // 你已经在用的 String、Arrays、System 全部是类！
        String s = "hello";          // String 是一个类
        int[] arr = {1, 2, 3};       // 数组也是对象
        System.out.println(s);       // System 是一个类，out 是它的字段

        // ===== 同一个类可以创建无数个对象 =====
        // 就像一张图纸可以盖无数栋房子
        Dog d3 = new Dog();
        d3.name = "小黑";
        d3.age = 5;

        Dog d4 = new Dog();
        d4.name = "花花";
        d4.age = 2;

        System.out.println("\n=== 四条狗 ===");
        Dog[] dogs = {d1, d2, d3, d4};   // 数组里放对象
        for (Dog d : dogs) {
            System.out.println(d.name + "，" + d.age + "岁");
        }

        // ===== 类名就是新类型名 =====
        // int i = 10;        ← int 是内置类型
        // Dog d = new Dog(); ← Dog 是你自定义的类型！
        // 你能像用 int 一样使用 Dog：声明变量、做参数、放数组...
    }
}
