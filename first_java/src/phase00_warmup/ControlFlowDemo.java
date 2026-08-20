package phase00_warmup;

public class ControlFlowDemo {
    public static void main(String[] args) {
        // ============ 1. if-else（和C一模一样，但条件必须是boolean） ============
        int score = 85;

        // ✅ Java：条件必须是 boolean
        if (score >= 90) {
            System.out.println("优秀");
        } else if (score >= 60) {
            System.out.println("及格");
        } else {
            System.out.println("不及格");
        }

        // ❌ C 中可以写 if (1) 或 if (score)，Java 不行！
        // if (1) { }  // 编译报错！int 不是 boolean

        // ============ 2. 三元运算符（和C完全一样） ============
        String result = score >= 60 ? "及格" : "不及格";
        String result_2 = true?"yes":"no";
        System.out.println(result_2);
        System.out.println("三元运算结果：" + result);

        // ============ 3. for 循环（和C完全一样） ============
        System.out.print("1到5：");
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // ============ 4. 增强 for-each（C没有，但Python有） ============
       int aa[]={10,20,30};
       for (int n : aa) System.out.println(n);

        // ============ 5. while / do-while（和C完全一样） ============
        int count = 3;
        do{
            System.out.println("while 还剩：" + count);
            count--;
        }while(count>0);

        // ============ 6. switch — 传统写法（和C一样） ============
        String grade;
        switch (score / 10) {
            case 10:
            case 9:
                grade = "A";
                break;           // 别忘了break！否则会穿透
            case 8:
                grade = "B";
                break;
            case 7:
                grade = "C";
                break;
            case 6:
                grade = "D";
                break;
            default:
                grade = "F";
                break;
        }
        System.out.println("传统 switch 评级：" + grade);

        // ============ 7. switch 表达式（Java 14+ 新写法，更简洁） ============
        // String grade2 = switch (score / 10) {
        //     case 10, 9 -> "A";   // 箭头语法，不用写 break！
        //     case 8     -> "B";
        //     case 7     -> "C";
        //     case 6     -> "D";
        //     default    -> "F";
        // };
        char grade2 = switch(score/10){
            case 9,10->'A';
            case 8->'B';
            default->'S';

        };
        System.out.println("新式 switch 评级：" + grade2);
    }
}
