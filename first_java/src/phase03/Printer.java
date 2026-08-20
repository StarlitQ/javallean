package phase03;

public class Printer {

}

interface Printable{
    void print(String cont);
}

class ConsolePrinter implements Printable{
    public void print(String conten){
        System.out.println("content is:"+conten);
    }
}

class FilePrinter implements Printable{
    public void print(String test){
        System.out.println("打印到文件：" + test);
    }
}

class Document{
    String content;
    Printable printability;
    Document(String content, Printable p){
        this.content = content;
        printability = p;
    }
    void setPrinter(Printable p){
        printability = p;
    }
    void print(){
        printability.print(content);
}

    public static void main(String[] args){
        Document doc1 = new Document("The 1st doc is here", new ConsolePrinter());
        Document doc2 = new Document("This doc is for others", new FilePrinter());
        Document doc3 = new Document("Atest doc", new FilePrinter());
        Document[] docs = {doc1, doc2, doc3};
        ConsolePrinter tmp = new ConsolePrinter();
        doc3.setPrinter(tmp);
        for (Document a : docs){
            a.print();
        }

    }

}