package phase00_warmup;

public class VariablesDemo {
    public static void main(String[] args) {
        // ============ Java 的基本数据类型 ============

        // 整数 — 和 C 几乎一样，但没有 unsigned
        byte  b = 127;          // 1 字节，-128 ~ 127
        short s = 32767;        // 2 字节
        int   i = 2147483647;   // 4 字节（最常用）
        long  l = 9223372036854775807L; // 8 字节，注意末尾 L
        // 浮点数
        float  f = 3.14f;       // 4 字节，注意末尾 f
        double d = 3.1415926535; // 8 字节（默认）
        // 字符和布尔 — Java 的 char 是 2 字节（Unicode）
        char   c = '中';        // 可以存中文！和 C 不同
        boolean flag = true;    // 真真正正的 true/false，不是 0/1

        // 引用类型 — String 是大写开头（它是类，不是基本类型）
        String name = "张三";

        // ============ 打印看看 ============
        System.out.println("整数 int: " + i);    // + 号自动拼接字符串
        System.out.println("浮点 double: " + d);
        System.out.println("字符 char: " + c);
        System.out.println("布尔 boolean: " + flag);
        System.out.println("字符串 String: " + name);
        System.out.println("wowo");
        // ============ 和 C/Python 的关键区别 ============
        // 1. 变量使用前必须初始化，否则编译报错
        // int x;
        // System.out.println(x);  // ❌ 编译不通过！

        // 2. 类型检查非常严格
        // int n = 3.14;  // ❌ 编译不通过！double 不能直接赋给 int
        int n = (int) 3.14;  // ✅ 强制转换才行
        System.out.println("强制转换 3.14 -> int: " + n);  // 输出 3

        // 3. 基本类型 vs 引用类型（之后会详细讲）
        int a = 10;
        int bb = a;     // 基本类型：复制值
        bb = 20;
        System.out.println("a = " + a + ", bb = " + bb);  // a 还是 10

        String s1 = "hello";
        String s2 = s1;  // 引用类型：指向同一个对象（和 Python 一样）
        s2 = "world";
        System.out.println("s1 = " + s1 + ", s2 = " + s2);  // s1 还是 "hello"
        // 注意：String 比较特殊（不可变），后面细讲
    }
}
