package phase02;

public class Counter {
    private static int total = 0;
    private int id;
    Counter(){
        this.id = ++total;
    }
    int getId(){
        return this.id;
    }
    static int getTotal(){
        return total;
    }
    static final String version = "1.0";

    public static void main(String[] args){
        Counter ppl1 = new Counter();
        Counter ppl2 = new Counter();
        Counter ppl3 = new Counter();
        System.out.println(ppl1.getTotal());
        Counter ppl4 = new Counter();
        Counter ppl5 = new Counter();
        System.out.println(ppl3.getId());
        System.out.println(ppl1.getTotal());
        System.out.println(ppl4.getTotal());
        System.out.println(getTotal());
        System.out.println(Counter.getTotal());
    }


}

