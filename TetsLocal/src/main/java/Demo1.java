import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Demo1 {
    static class Book {
        String name;
        int price;

        Book(String name, int price) {
            this.name = name;
            this.price = price;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = Integer.parseInt(sc.nextLine());
        List<Book> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            String s = sc.nextLine();
            String[] split = s.split(" ");
            Book book = new Book(split[0], Integer.parseInt(split[1]));
            list.add(book);
        }
        list.sort((o1, o2) -> {
            if (o1.price < o2.price) {
                return 1;
            } else if(o1.price> o2.price){
                return -1;
            }
            return 0;
        });

        for(Book book:list){
            System.out.println(book.name);
        }
    }
}
