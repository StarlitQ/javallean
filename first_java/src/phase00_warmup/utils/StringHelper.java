package phase00_warmup.utils;

public class StringHelper {

    // public static void main(String[] args){
    //     System.out.println(MathHelper.isEven(3));
    // }

    public static boolean isPalindrome(String s){
            boolean flag = true;
            for (int i=0; i<s.length()/2;i++){
                if (s.charAt(i)!=s.charAt(s.length()-1-i)){
                    flag = false;
                    break;
                }
            }
            return flag;
        }
    
    public static String reverse(String s){
        StringBuilder reved = new StringBuilder();
        for(int i=0;i<s.length();i++){
            reved.append(s.charAt(s.length()-1-i));
        }
        return reved.toString();

    }
}
