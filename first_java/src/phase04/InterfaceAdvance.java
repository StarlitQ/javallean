package phase04;

// ============ 1. 接口的 default 方法 ============
interface Greetable {
    // 抽象方法 — 必须实现
    String getName();

    // default 方法 — 有方法体，实现类可以不用覆写
    default void greet() {
        System.out.println("你好，我是 " + getName());
    }

    default void sayBye() {
        System.out.println(getName() + "：再见！");
    }
}

class Person implements Greetable {
    String name;
    Person(String name) { this.name = name; }

    @Override
    public String getName() { return name; }
    // greet() 和 sayBye() 不用写！接口自带默认实现
}

class Robot implements Greetable {
    String id;
    Robot(String id) { this.id = id; }

    @Override
    public String getName() { return "机器人" + id; }

    @Override
    public void greet() {  // 也可以覆写 default 方法
        System.out.println("哔哔——我是 " + getName());
    }
}

// ============ 2. 接口的 static 方法 ============
interface MathUtil {
    // static 方法属于接口本身，不是对象的
    static int max(int a, int b) {
        return a > b ? a : b;
    }

    static double square(double x) {
        return x * x;
    }
    // 调用：MathUtil.max(3, 5)
}

// ============ 3. 函数式接口 ============
// 只有一个抽象方法的接口，可以当"函数"用
// @FunctionalInterface  // 加上这个，编译器帮你检查是不是只有一个抽象方法
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);  // 只有一个抽象方法
    // int other();  // ❌ 如果再加一个抽象方法，@FunctionalInterface 会报错
}

public class InterfaceAdvance {
    public static void main(String[] args) {
        // ===== default 方法 =====
        Person p = new Person("张三");
        Robot r = new Robot("R2D2");

        p.greet();    // 你好，我是 张三        （用的接口默认版）
        p.sayBye();   // 张三：再见！            （用的接口默认版）
        r.greet();    // 哔哔——我是 机器人R2D2  （自己覆写了）

        // ===== static 方法 — 接口名直接调 =====
        System.out.println("最大值：" + MathUtil.max(3, 8));   // 8
        System.out.println("平方：" + MathUtil.square(5));     // 25.0

        // ===== 函数式接口 + 匿名类（还没学 Lambda，先用传统写法） =====
        Calculator add = new Calculator() {
            @Override
            public int calculate(int a, int b) {
                return a + b;
            }
        };
        Calculator multiply = new Calculator() {
            @Override
            public int calculate(int a, int b) {
                return a * b;
            }
        };

        System.out.println("3+5=" + add.calculate(3, 5));       // 8
        System.out.println("3*5=" + multiply.calculate(3, 5));  // 15
    }
}
