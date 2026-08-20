// 这个类在 phase00_warmup.utils 包里
// 和 Practice03 不在同一个文件夹，要 import 才能用
package phase00_warmup.utils;

public class MathHelper {

    // 判断一个数是不是偶数
    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    // 返回两个数中较大的
    public static int max(int a, int b) {
        return a > b ? a : b;
    }
}
