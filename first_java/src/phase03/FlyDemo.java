package phase03;

// ========== 新的合同 ==========
interface FlyMethod {
    void fly();
}

// ========== 三种不同的"兑现" ==========
class WingFly implements FlyMethod {
    public void fly() {
        System.out.println("噗噗噗扇翅膀飞！");
    }
}

class JetFly implements FlyMethod {
    public void fly() {
        System.out.println("喷气背包——轰！！");
    }
}

class MagicFlyMethod implements FlyMethod {
    public void fly() {
        System.out.println("念咒语：阿瓦达起飞！");
    }
}

// ========== Hero 只认合同 ==========
class SuperHero {
    String name;
    FlyMethod flyAbility;

    SuperHero(String name) {
        this.name = name;
    }

    void setFly(FlyMethod f) {
        flyAbility = f;
    }

    void takeOff() {
        System.out.print(name + "：");
        if (flyAbility != null) {
            flyAbility.fly();
        } else {
            System.out.println("不会飞...");
        }
    }
}

public class FlyDemo {
    public static void main(String[] args) {
        SuperHero ironMan = new SuperHero("钢铁侠");
        SuperHero harry = new SuperHero("哈利波特");
        SuperHero eagle = new SuperHero("老鹰");

        ironMan.setFly(new JetFly());
        harry.setFly(new MagicFlyMethod());
        eagle.setFly(new WingFly());

        ironMan.takeOff();
        harry.takeOff();
        eagle.takeOff();

        System.out.println("\n--- 钢铁侠换装备 ---");
        ironMan.setFly(new MagicFlyMethod());  // 换一行，搞定
        ironMan.takeOff();
    }
}
