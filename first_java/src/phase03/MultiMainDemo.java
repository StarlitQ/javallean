package phase03;

// 这个文件里三个类，各有一个 main

class ClassA {
    public static void main(String[] args) {
        System.out.println("我是 A 的 main！");
    }
}

class ClassB {
    ClassA aa = new ClassA();
    public static void main(String[] args) {
        ClassA.main(args);
        System.out.println("我是 B 的 main！");
    }
}

public class MultiMainDemo {
    public static void main(String[] args) {
        System.out.println("我是 MultiMainDemo 的 main！");
    }
}
