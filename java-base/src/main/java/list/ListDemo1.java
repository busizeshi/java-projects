package list;

import java.util.*;

public class ListDemo1 {
    public static void main(String[] args) {
//        创建一个集合
        List<Integer> list = Arrays.asList(64, 3, 22, 19, 97, 11, 4, 25, 53);

//        顺序排序
        Collections.sort(list);
        list.forEach(System.out::println);

        System.out.println("----------------------------------------");

//        逆序排序
        Collections.sort(list, (o1, o2) -> o2 - o1);
        list.forEach(System.out::println);

        System.out.println("----------------------------------------");

//        按照三个条件排序
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("A", 3, 1));
        dogs.add(new Dog("B", 2, 2));
        dogs.add(new Dog("C", 1, 3));
        dogs.add(new Dog("D", 4, 2));
        dogs.add(new Dog("D", 2, 5));
        dogs.sort(Comparator.comparing(Dog::getId,Comparator.reverseOrder()).thenComparing(Dog::getAge).thenComparing(Dog::getName));

        dogs.forEach(System.out::println);
    }
}
