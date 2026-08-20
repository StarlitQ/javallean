package phase01_complexity;
import java.util.HashSet;
public class PracticeComplecity {
    static int findMax(int[] tmp){
        int max = tmp[0];
        for (int i : tmp){
            if (i>max)max = i;
        }
        return max;
    }

    // 方法A：双重循环 — O(n²)
    static boolean hasDuplicate(int[] tmp) {
        for (int i = 0; i < tmp.length; i++) {
            for (int j = i + 1; j < tmp.length; j++) {
                if (tmp[i] == tmp[j]) return true;
            }
        }
        return false;
    }

    // 方法B：HashSet — O(n)（预留，后续学集合时再写）

    public static void main(String[] args){
        int[] a = {1,2,3,4,5,9,11,3};
        System.out.println(findMax(a));
        System.out.println(hasDuplicate(a));
    }

    // 原地反转 — 时间 O(n)，空间 O(1)
    static int[] reverseArray(int[] tmp) {
        for (int i = 0; i<tmp.length/2;i++){
            int a = tmp[i];
            tmp[i]=tmp[tmp.length - 1 - i];
            tmp[tmp.length - 1 - i] = a;
        }
        return tmp;
    }
    static int sum(int[] arr, int n){
        if (n==1)return arr[0];
        return arr[n-1]+sum(arr, n-1);
    }

    // 递归反转字符串 — 时间 O(n)，空间 O(n)
    static String reverse(String s) {
        if (s.length() <= 1) return s;                     // 终止：空串或单字符
        return reverse(s.substring(1)) + s.charAt(0);      // 递推：反转后面 + 第一个字符
    }

}

