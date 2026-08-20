/**
 * LeetCode #17 — 电话号码的字母组合 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 * 数字到字母的映射：电话按键
 *   2→"abc", 3→"def", 4→"ghi", 5→"jkl"
 *   6→"mno", 7→"pqrs", 8→"tuv", 9→"wxyz"
 *
 * 例1：digits = "23"
 *  输出: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * 例2：digits = ""    → 输出: []
 * 例3：digits = "2"   → 输出: ["a","b","c"]
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium14_LetterCombinations {

    static List<String> letterCombinations(String digits) {
        if (digits.isEmpty()) return new ArrayList<>();
        List<char[]> letters = new ArrayList<>();
        String[] map = {"", "", "abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
        for (int i=0;i<digits.length();i++){
            String change = map[digits.charAt(i)-'0'];
            letters.add(change.toCharArray());
        }
        
        int depth = 0;
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        expand(letters, depth, sb, res);

        return res;
    }

    static void expand(List<char[]> letters, int depth, StringBuilder cur, List<String> res){
        if (depth == letters.size()){//cur.toString().length()
            res.add(cur.toString());
            return;
        }
        for (int i=0;i<letters.get(depth).length;i++){
            cur.append(letters.get(depth)[i]);
            expand(letters, depth + 1, cur, res);
            cur.deleteCharAt(cur.length() - 1);
        }



    }

    public static void main(String[] args) {
        System.out.println(letterCombinations("23"));   // [ad,ae,af,bd,be,bf,cd,ce,cf]
        System.out.println(letterCombinations(""));     // []
        System.out.println(letterCombinations("2"));    // [a,b,c]
    }
}
