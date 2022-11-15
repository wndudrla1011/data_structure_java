package list.linkedList.doubly;

import java.util.Arrays;
import java.util.Comparator;

public class DoublyLinkedListApplication {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws CloneNotSupportedException {

        DoublyLinkedList<Integer> original = new DoublyLinkedList<>();
        original.add(10);

        DoublyLinkedList<Integer> copy = original;
        DoublyLinkedList<Integer> clone = (DoublyLinkedList<Integer>) original.clone();

        copy.add(20);
        clone.add(30);

        System.out.println("original list");
        for(int i = 0; i < original.size(); i++) {
            System.out.println("index " + i + " data = " + original.get(i));
        }

        System.out.println("\ncopy list");
        for(int i = 0; i < copy.size(); i++) {
            System.out.println("index " + i + " data = " + copy.get(i));
        }

        System.out.println("\nclone list");
        for(int i = 0; i < clone.size(); i++) {
            System.out.println("index " + i + " data = " + clone.get(i));
        }

        System.out.println("\noriginal list reference : " + original);
        System.out.println("copy list reference : " + copy);
        System.out.println("clone list reference : " + clone);

        DoublyLinkedList<Student> list = new DoublyLinkedList<>();

        list.add(new Student("김자바", 92));
        list.add(new Student("이시플", 72));
        list.add(new Student("조시샵", 98));
        list.add(new Student("파이손", 51));

        list.sort(comp);

        System.out.println();
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        System.out.println("\nlist.size() = " + list.size());
        System.out.println("list.get(2) = " + list.get(2));

        list.clear();
        System.out.println("after clear() = " + list.isEmpty());

        System.out.println("\n***Masking***");

        list.add(new Student("김OO", 92));
        list.add(new Student("이OO", 72));
        list.add(1, new Student("조OO", 98));
        list.add(2, new Student("파OO", 51));

        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        list.remove();

        System.out.println("\n***remove()***");
        System.out.println("list.toArray() = " + Arrays.toString(list.toArray()));
        Object[] a = new Object[10];
        System.out.println("New Array = " + Arrays.toString(list.toArray(a)));

        list.remove(1);

        System.out.println("\n***remove(1)***");
        System.out.println("list.toArray() = " + Arrays.toString(list.toArray()));

    }

    static Comparator<Student> comp = (o1, o2) -> o2.score - o1.score;

    static class Student implements Comparable<Student> {

        String name;
        int score;

        public Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public String toString() {
            return "이름 : " + name + "\t성적 : " + score;
        }

        @Override
        public int compareTo(Student o) {
            return o.score - this.score;
        }

    }

}
