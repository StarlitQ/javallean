package phase04;

class Shape{
    String name;
    Shape(String name){
        this.name=name;
    }
    double area(){
        return 0;
    }
}

class Circle extends Shape{
    double radius;
    Circle(String name, double radius){
        super(name);
        this.radius = radius;
    }
    @Override
    double area(){
        double s = Math.PI * radius * radius;
        return (s);
    }
}

class Rectangle extends Shape{
    double width, height;
    Rectangle(String name, double width, double height){
        super(name);
        this.width = width;
        this.height = height;
    }
    @Override
    double area(){
        double s = width * height;
        return s;
    }
}


public class ShapeArea {
    public static void main(String[] args){
        Rectangle rec1 = new Rectangle("1st rec", 10,20);
        Circle cir1 = new Circle("1st cir", 18);
        Rectangle rec2 = new Rectangle("2nd rec", 108,290);
        Circle cir2 = new Circle("2nd cir", 115);
        Shape[] areas = {rec1, rec2, cir1, cir2};
        for (Shape a : areas){
            System.out.println(a.area());
        }
    }
}
