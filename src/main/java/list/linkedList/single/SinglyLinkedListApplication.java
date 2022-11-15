package list.linkedList.single;

import java.util.Arrays;
import java.util.Comparator;

public class SinglyLinkedListApplication {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws CloneNotSupportedException {

        SinglyLinkedList<Integer> original = new SinglyLinkedList<>();
        original.add(10);

        SinglyLinkedList<Integer> copy = original;
        SinglyLinkedList<Integer> clone = (SinglyLinkedList<Integer>) original.clone();

        copy.add(20);
        clone.add(30);

        System.out.println("original list");
        for (int i = 0; i < original.size(); i++) {
            System.out.println("index " + i + " data = " + original.get(i));
        }

        System.out.println("\ncopy list");
        for (int i = 0; i < original.size(); i++) {
            System.out.println("index " + i + " data = " + copy.get(i));
        }

        System.out.println("\nclone list");
        for (int i = 0; i < original.size(); i++) {
            System.out.println("index " + i + " data = " + clone.get(i));
        }

        System.out.println("\noriginal list reference : " + original);
        System.out.println("copy list reference : " + copy);
        System.out.println("clone list reference : " + clone);
        System.out.println();

        SinglyLinkedList<Student> list = new SinglyLinkedList<>();

        list.add(new Student("김자바", 92));
        list.add(new Student("이시플", 72));
        list.add(new Student("조시샵", 98));
        list.add(new Student("파이손", 51));

        list.sort(comp);

        printList(list);

        System.out.println("\nlist.size() = " + list.size());
        System.out.println("list.get(1) = " + list.get(1));

        System.out.println("\n***index 2 데이터 변경***");
        list.set(2, new Student("이플플", 75));

        printList(list);

        list.clear();

        System.out.println("\nafter clear() = " + Arrays.toString(list.toArray()));
        System.out.println("list.isEmpty() = " + list.isEmpty());

        System.out.println("\n***Masking***");

        list.add(new Student("김OO", 92));
        list.add(new Student("이OO", 72));
        list.add(new Student("조OO", 98));
        list.add(new Student("파OO", 51));

        list.sort();

        printList(list);

        System.out.println("\n***remove()***");
        list.remove();

        printList(list);

    }

    private static void printList(SinglyLinkedList<Student> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    //sort 방법 1
    static Comparator<Student> comp = (o1, o2) -> o2.score - o1.score;

    static class Student implements Comparable<Student> {
        String name;
        int score;

        Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String toString() {
            return "이름 : " + name + "\t성적 : " + score;
        }

        @Override
        public int compareTo(Student o) {
            return o.score - this.score;
        }

    }

}

