package phase03_stackqueue;
import java.util.Deque;
import java.util.ArrayDeque;
public class PracticeStack {
    public static void main(String[] args){


    }

    static boolean isPalindrome(String s){
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()){
            stack.push(c);
        }
        for (char c : s.toCharArray()){
            if(c != stack.pop())return false;

        }
        return true;
    }

    static String reverse(String s){
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()){
            stack.push(c);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }
}