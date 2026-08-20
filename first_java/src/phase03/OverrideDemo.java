package phase03;

// 父类
class Phone {
    String model;

    Phone(String model) {
        this.model = model;
    }

    void call() {
        System.out.println(model + "：拨打电话...");
    }

    void showInfo() {
        System.out.println("手机型号：" + model);
    }
}

// 子类：智能手机
class SmartPhone extends Phone {
    String os;  // 操作系统

    SmartPhone(String model, String os) {
        super(model);
        this.os = os;
    }

    // ===== 覆写方式一：完全替换 =====
    @Override
    void call() {
        System.out.println(model + "（" + os + "）：高清语音通话...");
    }

    // ===== 覆写方式二：在父类基础上扩展 =====
    @Override
    void showInfo() {
        super.showInfo();   // ← 先调父类的，把基础信息打了
        System.out.println("操作系统：" + os);  // 再加自己的
    }

    // 新功能：父类没有的
    void browseWeb() {
        System.out.println(model + "：浏览网页...");
    }
}

// ===== 重写 vs 重载 — 放一起对比 =====
class Calculator {
    // 重载：同名方法，参数不同（编译时决定调哪个）
    int add(int a, int b) {
        return a + b;
    }
    int add(int a, int b, int c) {   // 参数个数不同
        return a + b + c;
    }
    double add(double a, double b) { // 参数类型不同
        return a + b;
    }
}

// 子类覆写 Calculator
class AdvancedCalc extends Calculator {
    @Override
    int add(int a, int b) {  // 覆写父类的 add(int, int)
        System.out.println("调用了覆写版 add");
        return super.add(a, b);  // 实际计算还是交给父类
    }
}

public class OverrideDemo {
    public static void main(String[] args) {
        SmartPhone sp = new SmartPhone("iPhone 20", "iOS");

        // 被完全替换的 call
        sp.call();  // iPhone 20（iOS）：高清语音通话...

        // 在父类基础上扩展的 showInfo
        sp.showInfo();  // 手机型号：iPhone 20
                        // 操作系统：iOS

        sp.browseWeb();  // 自己的新方法

        // ===== 重写 vs 重载对比 =====
        AdvancedCalc calc = new AdvancedCalc();
        System.out.println(calc.add(2, 3));        // 覆写版
        System.out.println(calc.add(2, 3, 4));     // 父类的重载版
        System.out.println(calc.add(2.5, 3.5));    // 父类的重载版
    }
}
