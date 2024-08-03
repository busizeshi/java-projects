package list;

import lombok.Data;

@Data
public class Dog {
    private String name;
    private int age;
    private int id;

    public Dog(String name, int age, int id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }
}
