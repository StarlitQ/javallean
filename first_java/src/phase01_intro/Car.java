package phase01_intro;

public class Car {
    String brand;
    int speed;
    void showInfo(){
        System.out.println("brand/speed is "+brand+speed);
    }
    void accelerate(int amount){
        speed+=amount;
    }
    public static void main(String[] args){
    Car car1=new Car();
    car1.brand="bmw"; car1.speed=70;
    Car car2=new Car();
    car2.brand="xiaomi"; car2.speed=100;
    car1.accelerate(10);
    car2.accelerate(30);
    car1.showInfo();
    car2.showInfo();
    }
}

