package phase03;

// ============ 父类：动物 ============
class Animal {
    String name;
    int age;

    Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void sleep() {
        System.out.println(name + " 在睡觉...");
    }

    void showInfo() {
        System.out.println(name + "，" + age + "岁");
    }
}

// ============ 子类：狗 extends 动物 ============
// Dog 自动拥有 name、age、sleep()、showInfo()
class Dog extends Animal {
    String breed;  // 品种——狗特有的

    Dog(String name, int age, String breed) {
        super(name, age);   // ← 调用父类构造方法
        this.breed = breed;
    }

    // 狗特有的方法
    void bark() {
        System.out.println(name + "：汪汪！");
    }
}

// ============ 子类：猫 extends 动物 ============
class Cat extends Animal {
    Cat(String name, int age) {
        super(name, age);
    }

    void meow() {
        System.out.println(name + "：喵喵~");
    }
}

public class InheritanceDemo {
    public static void main(String[] args) {
        Dog dog = new Dog("大黄", 3, "金毛");
        Cat cat = new Cat("咪咪", 1);

        // Dog 可以用 Animal 的方法，也可以用自己特有的方法
        dog.showInfo();   // 从 Animal 继承来的
        dog.bark();       // Dog 自己的
        dog.sleep();      // 从 Animal 继承来的

        cat.showInfo();   // 从 Animal 继承来的
        cat.meow();       // Cat 自己的

        // ===== 多态预览：用父类类型接收子类对象 =====
        Animal a1 = new Dog("小黑", 2, "拉布拉多");  // Dog 是一种 Animal
        Animal a2 = new Cat("花花", 1);              // Cat 也是一种 Animal

        a1.showInfo();   // ✅ Animal 有的方法都能调
        // a1.bark();    // ❌ 但以 Animal 类型看，没有 bark()

        // 数组里放不同类型，但都是 Animal
        Animal[] zoo = {dog, cat, a1, a2};
        System.out.println("\n=== 动物园 ===");
        for (Animal a : zoo) {
            a.showInfo();  // 每个都调自己的 showInfo
        }
    }
}
