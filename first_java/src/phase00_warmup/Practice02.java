package phase00_warmup;

public class Practice02 {
    public static void main(String[] args) {
        //task 1
        System.out.println("=====九九乘法表=====");
        for (int i=1;i<=9;i++){
            for (int j=1;j<=i;j++){
                System.out.printf("%d*%d=%d\t", j, i, i*j);
            }
            System.out.println();
        }



        //task 2
        int[] numbers = {64,32,76,18,45,90,23};
        int max=numbers[0];
        /*for (int n :numbers){
            if (n>max){
                max=n;
            }
        }*/
        
        for(int i=1;i<numbers.length;i++){
            if (numbers[i]>max){
                max=numbers[i];
            }
        }
        
        System.out.println("maxnum is "+max);

        // ========== 练习3：调用 dayOfWeek 方法 ==========
        System.out.println("1=>"+dayofWeek(1));
        System.out.println("9->"+dayofWeek(9));  // 预期：未知
    }


    // task3
    public static String dayofWeek(int day){
        return switch(day){
            case 1 -> "Monday";
            case 2 -> "hey";
            default -> "";
        };
    }

    public static String dayoweek(int day){
        String result;
        switch (day){
            case 1:
                result = "Monday";
                break;
            case 2:
                result = "hey";
                break;
            default:
                result="";
                break;
        }
        return result;
    }

}
