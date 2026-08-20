package phase04;

class Pet {
    String name;
    Pet(String name) { this.name = name; }
    void makeSound() { System.out.println(name + "：..."); }
}

class Puppy extends Pet {
    Puppy(String name) { super(name); }
    @Override
    void makeSound() { System.out.println(name + "：汪汪！"); }
    void wagTail() { System.out.println(name + " 摇尾巴～"); }
}

class Kitty extends Pet {
    Kitty(String name) { super(name); }
    @Override
    void makeSound() { System.out.println(name + "：喵喵~"); }
}

public class CastingDemo {
    public static void main(String[] args) {
        // ===== 向上转型（upcasting）：自动、安全 =====
        Puppy dog = new Puppy("大黄");
        Pet a1 = dog;                    // 自动向上转型
        Pet a2 = new Kitty("咪咪");       // 也是向上转型
        a1.makeSound();  // 汪汪！（运行时还是 Puppy 的行为）

        // ===== 向下转型（downcasting）：手动、有风险 =====
        Pet a3 = new Puppy("小黑");      // 先向上
        Puppy d1 = (Puppy) a3;           // 向下转型：手动强转
        d1.wagTail();                    // 小黑 摇尾巴～

        // ⚠️ 转错类型运行时会炸！
        Pet a4 = new Kitty("花花");
        // Puppy d2 = (Puppy) a4;  // ClassCastException！

        // ===== instanceof：安全转型的前置检查 =====
        Pet[] zoo = {
            a3, new Puppy("来福"), new Kitty("阿咪"),
            new Puppy("旺财"), new Kitty("球球")
        };

        System.out.println("\n=== 让狗摇尾巴 ===");
        for (Pet a : zoo) {
            if (a instanceof Puppy) {
                Puppy d = (Puppy) a;
                d.wagTail();
            } else {
                System.out.println(a.name + " 不是狗，跳过");
            }
        }

        // ===== Java 16+ 新写法：检查+转型一步到位 =====
        System.out.println("\n=== 新写法 ===");
        for (Pet a : zoo) {
            if (a instanceof Puppy d) {  // 检查 + 转型 + 命名
                d.wagTail();
            }
        }
    }
}
