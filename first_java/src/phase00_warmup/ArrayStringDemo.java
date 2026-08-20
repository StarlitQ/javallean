package phase00_warmup;

import java.util.Arrays;  // Arrays 工具类，提供排序、打印等方法

public class ArrayStringDemo {
    public static void main(String[] args) {
        // ============ 数组的三种创建方式 ============
        int[] a = {1, 2, 3, 4, 5};              // ① 直接初始化
        int[] b = new int[5];                    // ② 指定长度，默认全是0
        int[] c = new int[]{10, 20, 30};         // ③ 完整写法（较少用）
        int[] d = new int[3];
        int[] e = new int[]{};
        // ============ 数组和C的最大区别：自带 length ============
        System.out.println("数组长度：" + a.length); 
        System.out.println(a.length); // 5，不是方法！没有括号
        // C: sizeof(arr)/sizeof(arr[0])  ← 不用了！
        // Python: len(arr)              ← Java 直接用 .length

        // ============ 数组越界 ============
        // a[5] = 100;  // 运行时报错：ArrayIndexOutOfBoundsException
                         // 不像 C 那样默默写进未知内存

        // ============ 用 Arrays 工具类打印数组 ============
        System.out.println("直接打印：" + a);
        System.out.println(Arrays.toString(a));              // → [I@哈希码（没意义）
        System.out.println("格式化打印：" + Arrays.toString(a));  // → [1, 2, 3, 4, 5]
        Arrays.sort(a);
        Arrays.sort(a);  // 排序（原地排序，和 C 的 qsort 不同）
        System.out.println("排序后：" + Arrays.toString(a));

        // ============ 二维数组 ============
        int[][] mat ={
            {},{},{}
        };
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("第2行：" + Arrays.toString(matrix[1]));  // [4, 5, 6]

        // ============ String：不可变！ ============
        String s1 = "hello";
        String s2 = "hello";  // 字符串常量池，s2 和 s1 指向同一对象

        // 判断相等：和C/Python都不一样！
        System.out.println(s1==s2);
        System.out.println(s1.equals(s2));
        System.out.println("== 比较：" + (s1 == s2));         // true（同一对象）
        System.out.println("equals 比较：" + s1.equals(s2));  // true（内容相同）

        String s3 = new String("hello"); 
        String s4 = new String("hello"); // 强制新建对象
        System.out.println("new 之后 ==：" + (s1 == s3));     // false！不是同一对象了
        System.out.println("new 之后 equals：" + s1.equals(s3)); // true（内容还是一样的）

        // ⚠️ 记住：判断字符串内容是否相同，永远用 equals()！

        // ============ 字符串常用操作 ============
        String name = "  Zhang San  ";
        System.out.println("长度：" + name.length());           // 和数组不同，这是方法！
        System.out.println("去空格：" + name.trim());
        System.out.println(name.toUpperCase());        // "Zhang San"
        System.out.println("转大写：" + name.toUpperCase());     // "  ZHANG SAN  "
        System.out.println("是否包含：" + name.contains("San"));
        System.out.println(name.contains("Zhang")); // true
        // 注意：上面的操作都没有改变原字符串！因为 String 不可变
        System.out.println("原字符串：" + name);  // "  Zhang San  "  没变！

        // ============ 要频繁拼接？用 StringBuilder ============
        StringBuilder sb = new StringBuilder();
        sb.append("你好");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("hello");
        // sb.append("，");
        // sb.append("世界");
        System.out.println(sb2.toString());  // "你好，世界"

        // 对比：用 + 拼接在循环里很慢（每次创建新对象）
        // StringBuilder 是高效选择
    }
}
