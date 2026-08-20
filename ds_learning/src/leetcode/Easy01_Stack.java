/**
 * LeetCode #20 — 有效的括号 (Easy)
 *
 * ────────── 题目 ──────────
 * 给定一个只包含 '(' ')' '{' '}' '[' ']' 的字符串 s，判断字符串是否有效。
 *
 * 有效条件：
 *   1. 左括号必须用相同类型的右括号闭合
 *   2. 左括号必须以正确的顺序闭合
 *   3. 每个右括号都有一个对应的相同类型的左括号
 *
 * 例1：s = "()"        → true
 * 例2：s = "()[]{}"    → true
 * 例3：s = "(]"        → false
 * 例4：s = "([])"      → true
 * 例5：s = "([)]"      → false   ← 注意！交错嵌套是错的
 *
 * ────────── 提示 ──────────
 * 用栈（Stack）！你 Phase 3 学的：
 *   碰到左括号 → push
 *   碰到右括号 → pop 出来看匹不匹配
 *
 * 做完后改 main 里的测试用例验证。
 */
import java.util.*;

public class Easy01_Stack {

    static boolean isValid(String s) {
        Deque<Character> brackets = new LinkedList<>();
        char[] left = {'{','(','['};
        char[] right = {'}',')',']'};
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            for (int j = 0; j < 3; j++){
                if (c == left[j]) { brackets.push(c); break; }
                if (c == right[j]){
                    if (brackets.isEmpty() || brackets.peek() != left[j]) return false;
                    brackets.pop();
                    break;
                }
            }
        }
        return brackets.isEmpty();
    }

    public static void main(String[] args) {
        // 测试用例
        System.out.println(isValid("()"));        // true
        System.out.println(isValid("()[]{}"));    // true
        System.out.println(isValid("(]"));        // false
        System.out.println(isValid("([])"));      // true
        System.out.println(isValid("([)]"));      // false
    }
}
