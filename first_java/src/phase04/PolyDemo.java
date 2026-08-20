package phase04;

// 父类
class Animal {
    String name;
    Animal(String name) { this.name = name; }

    void makeSound() {
        System.out.println(name + "：...（某种叫声）");
    }
}

// 子类们 — 各自覆写 makeSound
class Dog extends Animal {
    Dog(String name) { super(name); }
    @Override
    void makeSound() {
        System.out.println(name + "：汪汪！");
    }
    void wagTail() {  // Dog 自己特有的
        System.out.println(name + " 摇尾巴");
    }
}

class Cat extends Animal {
    Cat(String name) { super(name); }
    @Override
    void makeSound() {
        System.out.println(name + "：喵喵~");
    }
}

class Duck extends Animal {
    Duck(String name) { super(name); }
    @Override
    void makeSound() {
        System.out.println(name + "：嘎嘎！");
    }
}

public class PolyDemo {
    public static void main(String[] args) {
        // ===== 多态的核心演示 =====
        // 声明类型（左边）都是 Animal，实际类型（右边）各不同
        Animal a1 = new Dog("大黄");
        Animal a2 = new Cat("咪咪");
        Animal a3 = new Duck("唐老鸭");

        // 同一个方法调用 → 不同行为！
        a1.makeSound();  // 大黄：汪汪！      ← Dog 的 makeSound
        a2.makeSound();  // 咪咪：喵喵~       ← Cat 的 makeSound
        a3.makeSound();  // 唐老鸭：嘎嘎！     ← Duck 的 makeSound

        // ===== 这就是多态：一个接口，多种形态 =====
        Animal[] zoo = {a1, a2, a3, new Dog("小黑"), new Cat("花花")};
        System.out.println("\n=== 动物园大合唱 ===");
        for (Animal a : zoo) {
            a.makeSound();  // 不需要知道每个动物具体是啥，统一调就行
        }

        // ===== 能调什么，看声明类型 =====
        Animal a4 = new Dog("旺财");
        a4.makeSound();   // ✅ Animal 有这个，能调
        // a4.wagTail();  // ❌ Animal 没有 wagTail，编译报错！
        // 虽然旺财实际上是一条狗，但编译器只认 Animal 门牌
    }
}

// ============================================================
// 组合版多态：用接口代替继承
// ============================================================
// 核心思想：把"能做什么"抽象成接口，而不是"是什么"抽象成父类
// 每个类独立实现接口，不依赖继承链
// ============================================================

// ① 定义"能叫"这个能力 —— 接口 = 合同
interface Soundable {
    void makeSound();   // 签了这个合同，就必须实现 makeSound()
}

// ② 每个动物独立实现接口，不需要共同的 Animal 父类
//    注意：没有 extends！每个类都是独立的
class Dog2 implements Soundable {
    String name;
    Dog2(String name) { this.name = name; }

    @Override
    public void makeSound() {
        System.out.println(name + "：汪汪！");   // 跟继承版一模一样的行为
    }

    // 可以有自己特有的方法，不会因为父类限制而调不了
    void wagTail() {
        System.out.println(name + " 摇尾巴");
    }
}

class Cat2 implements Soundable {
    String name;
    Cat2(String name) { this.name = name; }

    @Override
    public void makeSound() {
        System.out.println(name + "：喵喵~");
    }
}

class Duck2 implements Soundable {
    String name;
    Duck2(String name) { this.name = name; }

    @Override
    public void makeSound() {
        System.out.println(name + "：嘎嘎！");
    }
}

// ③ 使用：跟继承版的多态写法完全一样！
//    Soundable s = new Dog2("大黄");  ← 左边写接口类型
//    s.makeSound();                   ← 同样的多态效果！
//
// ┌────────────────────────────────────────────────────┐
// │  继承版:  Animal a = new Dog("旺财");              │
// │  组合版:  Soundable s = new Dog2("旺财");          │
// │                                                    │
// │  效果一样，但组合版：                                │
// │  - Dog2 不依赖 Animal 父类，更灵活                  │
// │  - Dog2 可以再实现别的接口（比如 Swimmable）        │
// │  - wagTail() 用 Dog2 类型就能直接调，不需要强转     │
// └────────────────────────────────────────────────────┘

// ④ 多接口演示：一个类可以实现多个接口（多继承做不到的！）
interface Swimmable {
    void swim();
}

// AmphiDog 既能叫又能游泳——用继承根本做不到（Java 单继承）
class AmphiDog implements Soundable, Swimmable {  // ← 实现两个接口！
    String name;
    AmphiDog(String name) { this.name = name; }

    @Override
    public void makeSound() { System.out.println(name + "：汪汪！(水花四溅)"); }

    @Override
    public void swim() { System.out.println(name + " 在水里游"); }
}

// ⑤ 完整 Demo（取消下面注释运行看看）
class CompositionPolyDemo {
    public static void main(String[] args) {
        // 多态：左边接口类型，右边实际类型
        Soundable s1 = new Dog2("大黄");
        Soundable s2 = new Cat2("咪咪");
        Soundable s3 = new Duck2("唐老鸭");

        System.out.println("=== 组合版动物园大合唱 ===");
        Soundable[] zoo = {s1, s2, s3};
        for (Soundable s : zoo) {
            s.makeSound();  // 同样的多态！同一个调用，不同行为
        }

        // 想调 wagTail？直接用 Dog2 类型就行，不用强转！
        Dog2 d = new Dog2("旺财");
        d.wagTail();  // ✅ 编译通过！不用 (Dog) 强转

        // 多接口：同一个对象，不同角度看有不同的能力
        AmphiDog ad = new AmphiDog("水狗");
        ad.makeSound();  // 会叫
        ad.swim();       // 会游

        // 用不同接口类型看同一个对象
        Soundable asTalker = ad;   // 从这个角度看：能叫
        Swimmable asSwimmer = ad;  // 从这个角度看：能游
        asTalker.makeSound();
        asSwimmer.swim();
    }
}
