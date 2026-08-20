package phase04;

public class WorkerDemo {
    public static void main(String[] args){
        Worker w1 = new Programmer("zyq", 52000);
        Worker w2 = new Salesman("Jack", 12000);
        Worker w3 = new Programmer("Mary", 49000);
        Worker w4 = new Salesman("Rose", 18000);
        Worker w5 = new Programmer("Robin", 50000);
        Worker w6 = new Salesman("Jackie", 15000);
        Worker[] workers = {w1,w2,w3,w4,w5,w6};
        for (Worker w : workers){
            w.showInfo();
            if (w instanceof Trainable){
                Trainable t = (Trainable)w;
                t.attendTraining();
                t.restBreak();
            }
        }
    }   
}

abstract class Worker{
    String name;
    double salary;
    Worker(String name, double salary){
        this.name = name;
        this.salary = salary;
    }
    abstract double getBonus();
    void showInfo(){
        System.out.println("name:"+name+",salary:"+salary+",bonus:"+getBonus());
    }
}

interface Trainable{
    void attendTraining();
    default void restBreak(){
        System.out.println("The worker is taking break.");
    }
}

class Programmer extends Worker implements Trainable{
    double bonus;
    Programmer(String name, double salary){
        super(name, salary);
    }
    @Override
    double getBonus(){
        bonus = salary * 0.3;
        return bonus;
    }
    @Override
    public void restBreak(){
        System.out.println("Programmer sleeping.");
    }
    @Override
    public void attendTraining(){
        System.out.println(name+" is training");
    }
}

class Salesman extends Worker implements Trainable{
    double bonus;
    Salesman(String name, double salary){
        super(name, salary);
    }
    @Override
    double getBonus(){
        bonus = salary * 0.15;
        return bonus;
    }
    @Override
    public void attendTraining() {
        System.out.println(name+" don't need to train");
    }
}