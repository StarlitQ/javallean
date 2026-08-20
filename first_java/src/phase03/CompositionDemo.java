package phase03;

// ============ 继承的坑：层次太深，牵一发动全身 ============
// 假设一个游戏，有各种角色
class Character {
    String name;
    int hp;

    Character(String name, int hp) {
        this.name = name;
        this.hp = hp;
    }

    void attack() {
        System.out.println(name + "：普通攻击");
    }
}

// 需求：有的角色会飞，有的角色会游泳，有的都会
// ❌ 用继承会很痛苦——不可能让 "会飞的战士" 同时继承 "战士" 和 "飞行器"
// Java 不允许继承多个父类！

// ============ ✅ 用组合：把"会飞""会游泳"拆成独立的能力 ============
interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

// 能力的具体实现
class CanFly implements Flyable {
    public void fly() {
        System.out.println("飞起来了！");
    }
}

class CanSwim implements Swimmable {
    public void swim() {
        System.out.println("游起来了！");
    }
}

// 角色用组合——有哪种能力就"装"哪种
class Hero {
    String name;
    Flyable flyAbility;      // 飞行能力（没有就是 null）
    Swimmable swimAbility;   // 游泳能力（没有就是 null）

    Hero(String name) {
        this.name = name;
    }

    void setFly(Flyable f) {
        flyAbility = f;
    }

    void setSwim(Swimmable s) {
        swimAbility = s;
    }

    void showAbilities() {
        System.out.print(name + "的能力：");
        if (flyAbility != null) System.out.print("飞 ");
        if (swimAbility != null) System.out.print("游泳 ");
        if (flyAbility == null && swimAbility == null)
            System.out.print("无特殊能力");
        System.out.println();
    }
}

public class CompositionDemo {
    public static void main(String[] args) {
        // 组合：灵活搭配能力，想要什么装什么
        Hero superman = new Hero("超人");
        superman.setFly(new CanFly());    // 装上飞行能力

        Hero aquaman = new Hero("海王");
        aquaman.setSwim(new CanSwim());   // 装上游泳能力

        Hero dragon = new Hero("飞龙");
        dragon.setFly(new CanFly());      // 两个都装！
        dragon.setSwim(new CanSwim());

        superman.showAbilities();  // 超人：飞
        aquaman.showAbilities();   // 海王：游泳
        dragon.showAbilities();    // 飞龙：飞 游泳

        // 使用能力
        if (superman.flyAbility != null) {
            superman.flyAbility.fly();
        }
    }
}
