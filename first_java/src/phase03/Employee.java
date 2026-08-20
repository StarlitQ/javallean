package phase03;

public class Employee {
    String name;
    double salary;
    Employee(String name, double salary){
        this.name = name;
        this.salary = salary;
    }
    void showInfo(){
        System.out.println("name:"+name+","+"salary:"+salary);
    }
    static void printAll(Employee[] emp){
        for (Employee tmp : emp){
            tmp.showInfo();
        }
    }
    public static void main(String[] args){
        Employee clerk = new Employee("Jack", 12000);
        Manager manage = new Manager("Chris", 16000, 10000);
        Employee[] workers = {clerk, manage};
        Employee.printAll(workers);
    }
}

class Manager extends Employee{
    double bonus;
    Manager(String name, double salary, double bonus){
        super(name, salary);
        this.bonus = bonus;
    }
    @Override
    void showInfo(){
        super.showInfo();
        System.out.println("bonus:"+bonus);
    }
}