package phase02;

// ============ 没有访问控制的版本 ============
class UnsafeAccount {
    double balance;  // 没写修饰符 = default：同包可见

    UnsafeAccount(double b) {
        balance = b;
    }
}

// ============ 有访问控制的版本 ============
class SafeAccount {
    private double balance;  // 只有本类能碰

    SafeAccount(double b) {
        balance = b;
    }

    // 取钱：必须走这个方法，没法绕过
    void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("余额不足！");
        } else {
            balance -= amount;
            System.out.println("取款成功，余额：" + balance);
        }
    }

    // 查看余额：只能看，不能改
    double getBalance() {
        return balance;
    }
}

public class AccessDemo {
    public static void main(String[] args) {
        // ===== 没有保护：你可以乱来 =====
        UnsafeAccount bad = new UnsafeAccount(1000);
        bad.balance = -99999;    // ✅ 编译通过！直接改，想怎么搞怎么搞
        System.out.println("不安全账户余额：" + bad.balance);  // -99999

        // ===== 有保护：编译器帮你拦 =====
        SafeAccount good = new SafeAccount(1000);
        // good.balance = -99999;  // ❌ 编译报错！balance 是 private
        // 只能通过方法操作
        good.withdraw(200);           // 取款成功，余额：800.0
        good.withdraw(900);           // 余额不足！
        System.out.println("安全账户余额：" + good.getBalance());  // 只能读
    }
}
