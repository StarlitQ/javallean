package phase04;

public class BankTest {
    public static void main(String[] args){
        Account a1 = new SavingsAccount("Jack", 51888, 2.8);
        Account a2 = new CreditAccount("Tom", 21000, 25000);
        Account a3 = new SavingsAccount("Jakie", 29888, 0.8);
        Account a4 = new CreditAccount("Tomie", 210010, 259800);
        Account a5 = new SavingsAccount("Rose", 88, 1);
        Account a6 = new CreditAccount("Mary", 21, 25);
        Account[] acc = {a1,a2,a3,a4,a5,a6};
        for (Account a : acc){
            a.showInfo();
            if (a instanceof SavingsAccount)System.out.println("This is a saving account.");
            else if (a instanceof CreditAccount)System.out.println("This is a credit account.");
        }
    }
}

class Account{
    String owner;
    double balance;
    Account(String o, double b){
        owner = o;
        balance = b;
    }
    void showInfo(){
        System.out.println("owner:"+owner+","+"balance:"+balance);
    }
}

class SavingsAccount extends Account{
    double interestrate;
    SavingsAccount(String o, double b, double interestrate){
        super(o, b);
        this.interestrate = interestrate;
    }
    @Override
    void showInfo(){
        System.out.println("owner:"+owner+","+"balance:"+balance+","+"Interest rate:"+interestrate);
    }
}

class CreditAccount extends Account{
    double creditlimit;
    CreditAccount(String o, double b, double c){
        super(o, b);
        creditlimit = c;
    }
    @Override
    void showInfo(){
        System.out.println("owner:"+owner+","+"balance:"+balance+","+"Credit limit:"+creditlimit);
    }
}