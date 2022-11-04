package heap;

import java.util.Comparator;
import java.util.Random;

public class HeapApplication {

    public static void main(String[] args) {

        /*Heap<Integer> heap = new Heap<>();

        Random rdm = new Random();

        for (int i = 0; i < 15; i++) {
            heap.add(rdm.nextInt(100));
        }

        System.out.print("내부 배열 상태 : ");
        for (Object val : heap.toArray()) {
            System.out.print(val + " ");
        }
        System.out.println();

        System.out.print("힙 요소 뽑기 : \t");
        while (!heap.isEmpty()) {
            System.out.print(heap.remove() + " ");
        }*/

        Heap<Student> heap1 = new Heap<>();
        Heap<Student> heap2 = new Heap<>(comparator);

        heap1.add(new Student("김", 85));
        heap2.add(new Student("김", 85));

        heap1.add(new Student("이", 73));
        heap2.add(new Student("이", 73));

        heap1.add(new Student("한", 91));
        heap2.add(new Student("한", 91));

        heap1.add(new Student("장", 61));
        heap2.add(new Student("장", 61));

        heap1.add(new Student("조", 82));
        heap2.add(new Student("조", 82));

        heap1.add(new Student("백", 53));
        heap2.add(new Student("백", 53));

        System.out.println("[Heap 1] : 이름순(같을 경우 점수 오름차순)");
        while (!heap1.isEmpty()) {
            System.out.println(heap1.remove());
        }
        System.out.println();

        System.out.println("[Heap 2] : 점수 내림차순(같을 경우 이름순)");
        while (!heap2.isEmpty()) {
            System.out.println(heap2.remove());
        }
        System.out.println();
    }

    private static Comparator<Student> comparator = (o1, o2) -> {
        //나이가 같다면 이름순
        if (o1.point == o2.point) {
            return o1.name.compareTo(o2.name);
        }

        return o2.point - o1.point; //점수 내림차순
    };

    private static class Student implements Comparable<Student> {

        String name;
        int point;

        public Student(String name, int point) {
            this.name = name;
            this.point = point;
        }

        @Override
        public int compareTo(Student o) {

            //이름이 같다면 점수순 (오름차순)
            if (this.name.compareTo(o.name) == 0) {
                return this.point - o.point;
            }
            //이름순
            return this.name.compareTo(o.name);

        }

        @Override
        public String toString() {
            return "이름 : " + name + "\t나이 : " + point;
        }

    }


}
