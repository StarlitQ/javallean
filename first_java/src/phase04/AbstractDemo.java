package phase04;

// ============ 1. 抽象类 ============
// 有 abstract 关键字，不能 new
abstract class AbstractShape {
    String name;

    AbstractShape(String name) {
        this.name = name;
    }

    // 抽象方法：没有方法体，子类必须覆写
    abstract double area();

    // 普通方法：有方法体，子类可以直接用
    void showInfo() {
        System.out.println(name + " 的面积：" + area());
    }
}

// 子类实现抽象方法
class Circle2 extends AbstractShape {
    double radius;
    Circle2(String name, double r) {
        super(name);
        radius = r;
    }
    @Override
    double area() { return Math.PI * radius * radius; }
}

class Rect2 extends AbstractShape {
    double w, h;
    Rect2(String name, double w, double h) {
        super(name);
        this.w = w; this.h = h;
    }
    @Override
    double area() { return w * h; }
}

// ============ 2. 接口 ============
// 纯合同，所有方法默认 public abstract
interface Drawable {
    void draw();  // 没有方法体，实现类必须写
}

interface Colorable {
    void setColor(String color);
    String getColor();
}

// 一个类可以实现多个接口，但只能继承一个类
class ColorCircle extends AbstractShape implements Drawable, Colorable {
    double radius;
    String color;

    ColorCircle(String name, double r) {
        super(name);
        radius = r;
    }

    @Override double area() { return Math.PI * radius * radius; }

    @Override public void draw() {
        System.out.println("画一个" + color + "色的圆：" + name);
    }
    @Override public void setColor(String c) { color = c; }
    @Override public String getColor() { return color; }
}

public class AbstractDemo {
    public static void main(String[] args) {
        // ===== 抽象类不能 new =====
        // AbstractShape s = new AbstractShape("x");  // ❌ 编译报错
        AbstractShape s1 = new Circle2("小圆", 5);     // ✅ 多态，可以
        AbstractShape s2 = new Rect2("矩形", 10, 20);   // ✅

        s1.showInfo();  // 小圆 的面积：78.5...
        s2.showInfo();  // 矩形 的面积：200.0

        // ===== 接口也不能 new =====
        // Drawable d = new Drawable();  // ❌
        Drawable d = new ColorCircle("彩圆", 3);  // ✅ 多态
        d.draw();

        // ===== 多接口 =====
        ColorCircle cc = new ColorCircle("彩虹圈", 4);
        cc.setColor("红");
        cc.draw();

        // ===== 用不同接口类型看同一个对象 =====
        Drawable asDraw = cc;     // 只能 draw()
        Colorable asColor = cc;   // 只能 setColor/getColor
    }
}
