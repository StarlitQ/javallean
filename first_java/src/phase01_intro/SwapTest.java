package phase01_intro;

public class SwapTest {
    public static void main(String[] args){
        int a=5;
        int b=9;
        swap(a,b);
        System.out.println("a/b="+a+","+b);
        Box x = new Box();
        Box y = new Box();
        x.value=10;
        y.value=3;
        swap(x,y);
        System.out.println("x/y="+x.value+","+y.value);

    }
    public static void swap(int a, int b){
        a=a+b;
        b=a-b;
        a=a-b;
        
    }
    

    public static void swap(Box x, Box y){
        int z;
        z=x.value;
        x.value=y.value;
        y.value=z;
    }
}

// Box 挪到外面，不加 public（一个文件只能有一个 public 类），不加 static
class Box {
    int value;
}

