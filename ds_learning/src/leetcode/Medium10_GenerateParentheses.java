/**
 * LeetCode #22 — 括号生成 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定 n 对括号，写一个函数生成所有可能的、并且有效的括号组合。
 *
 * 例1：n = 3
 *  输出: ["((()))", "(()())", "(())()", "()(())", "()()()"]
 *
 * 例2：n = 1  → 输出: ["()"]
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium10_GenerateParentheses {

    static List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        List<Character> path = new ArrayList<>();
        backtrack(path, 0, 0, res, n);


        return res;
    }

    static void backtrack(List<Character> path, int i, int k, List<String> res, int n){
        if(i > k){
            path.add(')');
            backtrack(path, i, k+1, res, n);
            path.remove(path.size() - 1);
        }
        
        if (i ==n && k == n){
            StringBuilder sb = new StringBuilder();
            for (char c : path){
                sb.append(c);
            }
            res.add(sb.toString());
            return;
        }

        if(i < n){
            path.add('(');
            backtrack(path, i+1, k, res, n);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(generateParenthesis(3));  // 5 种
        System.out.println(generateParenthesis(1));  // [()]
    }
}
