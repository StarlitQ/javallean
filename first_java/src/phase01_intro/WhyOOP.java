package phase01_intro;

// ============ C 风格：数据和操作分离 ============
class CStudent {
    // C 风格的 struct：只有数据，没有行为
    // 需要写独立的函数来操作这些数据
}

public class WhyOOP {
    // ----- C 风格：用独立函数操作独立数据 -----
    // 名字数组 + 分数数组，靠下标关联（很容易搞混）
    static void printCStudent(String[] names, int[] scores, int i) {
        System.out.println(names[i] + "：" + scores[i] + "分");
    }

    static double getAverage(int[] scores) {
        double sum = 0;
        for (int s : scores) sum += s;
        return sum / scores.length;
    }

    // ============ OOP 风格：数据 + 行为绑在一起 ============
    static class Student {
        // 数据（字段）
        String name;
        int score;

        // 行为（方法）—— 和对应的数据放在一起
        void printInfo() {
            System.out.println(name + "：" + score + "分");
        }

        boolean isPass() {
            return score >= 60;
        }
    }

    public static void main(String[] args) {
        // ===== C 风格调用：数据归数据，函数归函数 =====
        String[] names = {"张三", "李四", "王五"};
        int[] scores = {85, 42, 90};

        System.out.println("=== C 风格 ===");
        for (int i = 0; i < names.length; i++) {
            printCStudent(names, scores, i);  // 每次都要传两个数组
        }
        System.out.println("平均分：" + getAverage(scores));

        // ===== OOP 风格调用：数据和方法在一起 =====
        Student s1 = new Student();
        s1.name = "张三";   s1.score = 85;
        Student s2 = new Student();
        s2.name = "李四";   s2.score = 42;
        Student s3 = new Student();
        s3.name = "王五";   s3.score = 90;

        System.out.println("\n=== OOP 风格 ===");
        s1.printInfo();     // 不用传参数！数据自带的方法
        s2.printInfo();
        s3.printInfo();
        System.out.println("张三及格？" + s1.isPass());
        System.out.println("李四及格？" + s2.isPass());
    }
}
