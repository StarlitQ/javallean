package phase01_intro;

public class MemoryModel {
    public static void main(String[] args) {
        // ===== 基本类型：值直接存在栈上 =====
        int a = 10;
        int b = a;    // 把 a 的值复制一份给 b
        b = 20;       // 改 b 不影响 a
        System.out.println("a=" + a + ", b=" + b);  // a=10, b=20

        // ===== 引用类型：栈上存地址，对象在堆上 =====
        Dog d1 = new Dog();
        d1.name = "大黄";

        Dog d2 = d1;       // 复制的是地址！不是对象本身
        d2.name = "小白";  // 改 d2 会影响 d1！因为它们指向同一个对象

        System.out.println("d1.name=" + d1.name);  // 小白！（不是大黄了）
        System.out.println("d2.name=" + d2.name);  // 小白

        // ===== 画成内存图 =====
        // 栈（stack）              堆（heap）
        // ┌──────┐              ┌────────────────┐
        // │ a=10 │              │ Dog(name="小白")│  ← 只有一个 Dog 对象
        // │ b=20 │              └────▲───▲────────┘
        // │ d1 ──┼────────→ 地址 0x100 ┘   │
        // │ d2 ──┼────────→ 地址 0x100 ────┘
        // └──────┘

        // ===== 对比：基本类型 vs 引用类型 =====
        // 基本类型：栈上直接存值，互相独立
        int x = 5;
        int y = x;
        y = 999;
        System.out.println(x);  // 5，不受影响

        // 引用类型：栈上存地址，指向堆上同一个对象
        Dog dx = new Dog();
        dx.name = "阿黄";
        Dog dy = dx;
        dy.name = "阿白";
        System.out.println(dx.name);  // 阿白，受影响了！

        // ===== null：空地址 =====
        Dog d3 = null;     // d3 在栈上，但没有指向任何对象
        // d3.name = "x";  // ❌ NullPointerException！空指针异常
        // 相当于 C 里：int* p = NULL; *p = 10;  // 段错误！
    }

    static class Dog {
        String name;
    }
}
