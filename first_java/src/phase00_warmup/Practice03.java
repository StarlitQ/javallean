package phase00_warmup;
import java.util.Arrays;
import phase00_warmup.utils.MathHelper;
import phase00_warmup.utils.StringHelper;

public class Practice03 {
    public static void main(String[] args){
    //task 1
    int[] arr = {85,92,78,95,88,70};
    Arrays.sort(arr);
    System.out.println(Arrays.toString(arr));
    for (int i=0;i<arr.length;i++){
        System.out.println(arr[arr.length-1-i]);
    }

    //task 2
    String test = "racecar";
    // boolean flag = true;
    // for (int j=0; j<=test.length()/2-1;j++){
    //     if(test.charAt(j)!=test.charAt(test.length()-1-j)){
    //         flag=false;
    //     }
    // }
    // System.out.println(flag);
    System.out.println("是回文吗？" + StringHelper.isPalindrome(test));
    System.out.println("反转：" + StringHelper.reverse(test));



    //task 3
    String[] compare = {"java", "python", "c",
     "javascript","ruby"
    };
    for (String i : compare){
        if(i.length()>=3){
            System.out.println(i);
        }
    }

    //task 4：测试跨包调用
    System.out.println("4是偶数吗？" + MathHelper.isEven(4));
    System.out.println("7和3谁大？" + MathHelper.max(7, 3));


    }
}
