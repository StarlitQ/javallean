package phase01_intro;

public class BankAccount {
    public static void main(String[] args){
        Account myacc = new Account("zyq", 5200000);
        myacc.show();
        myacc.deposit(10086.99);
        myacc.withdraw(999.13);
        myacc.show();
    }

}

class Account{
    String owner;
    double balance;
    Account(String owner, double initialBalance){
        this.owner=owner;
        balance=initialBalance;
    }
    void deposit(double amount){
        balance+=amount;
    }
    void withdraw(double amount){
        double current = balance-amount;
        if(current>=0){
            balance=current;
        }
        else{
            System.out.println("you too poor");
        }
    }
    void show(){
        System.out.println("owner:"+owner+"\t"+"balance"+balance);
    }
}