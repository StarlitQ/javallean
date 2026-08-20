package phase02;
public class EncapsulatedAccount {
    public static void main(String[] args){
        safeAccount myacc = new safeAccount("zyy", 5201314);
        myacc.show();
        myacc.setowner("zyq");
        myacc.withdraw(1.3);
        System.out.println("owner is:"+myacc.getowner());

    }
}

class safeAccount{
    private String owner;
    private double balance;
    public safeAccount(String owner, double initialBalance){
        this.owner=owner;
        balance=initialBalance;
    }
    public void deposit(double amount){
        balance+=amount;
    }
    public void withdraw(double amount){
        double current = balance-amount;
        if(current>=0){
            balance=current;
        }
        else{
            System.out.println("you too poor");
        }
    }
    public void setowner(String owner){
        this.owner = owner;
    }
    public void show(){
        System.out.println("owner:"+owner+"\t"+"balance:"+balance);
    }
    public String getowner(){
        return owner;
    }
    public double getbalance(){
        return balance;
    }
}