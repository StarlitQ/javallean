package phase01_intro;

public class Book {
    String title;
    String author;
    int pages;

    void showInfo() {  // 你写成了 shoeInfo，改了
        System.out.println("《" + title + "》 作者：" + author + "，共" + pages + "页");
    }

    boolean isThick() {
        return pages > 300;  // 和你的 if-else 等价，一行搞定
    }

    public static void main(String[] args) {
        Book book1 = new Book();
        book1.title = "三国演义";
        book1.author = "罗贯中";
        book1.pages = 800;

        Book book2 = new Book();
        book2.title = "小王子";
        book2.author = "圣埃克苏佩里";
        book2.pages = 120;

        Book book3 = new Book();
        book3.title = "三体";
        book3.author = "刘慈欣";
        book3.pages = 400;

        Book[] books = {book1,book2,book3};
        for (Book b : books){
            b.showInfo();
            System.out.println("  厚书？" + b.isThick());
        }

        // 放数组遍历
        // Book[] books = {book1, book2, book3};
        // for (Book b : books) {
        //     b.showInfo();
        //     System.out.println("  厚书？" + b.isThick());
        // }
    }
}
