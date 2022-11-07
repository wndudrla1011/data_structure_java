package priorityQueue;

import java.util.Comparator;
import java.util.Random;

public class PriorityQueueApplication {

    public static void main(String[] args) {

        PriorityQueue<Student> pq1 = new PriorityQueue<>();
        PriorityQueue<Student> pq2 = new PriorityQueue<>(comp);

        Random rnd = new Random();

        char name = 'A';
        for (int i = 0; i < 10; i++) {
            int math = rnd.nextInt(10);
            int science = rnd.nextInt(10);

            pq1.offer(new Student(name, math, science));
            pq2.offer(new Student(name, math, science));
            name++;
        }

        System.out.println("[pq1 내부 배열 상태]");
        for (Object val : pq1.toArray()) {
            System.out.print(val);
        }
        System.out.println();
        System.out.println();

        System.out.println("[pq2 내부 배열 상태]");
        Object[] q = new Object[15];
        for (Object val : pq2.toArray(q)) {
            System.out.print(val);
        }
        System.out.println();
        System.out.println();

        System.out.println("[수학-과학-이름순 뽑기]");
        System.out.println("name\tmath\tscience");
        while (!pq1.isEmpty()) {
            System.out.print(pq1.poll());
        }
        System.out.println();
        System.out.println("[과학-수학-이름순 뽑기]");
        System.out.println("name\tmath\tscience");
        while (!pq2.isEmpty()) {
            System.out.print(pq2.poll());
        }

    }

    private static Comparator<Student> comp = new Comparator<Student>() {

        /*
        * 과학 점수가 높은 순
        * 과학 점수가 같을 경우 수학 점수가 높은 순
        * 둘 다 같을 경우 이름 순
        * */
        @Override
        public int compare(Student o1, Student o2) {
            if (o1.science == o2.science) {
                if (o1.math == o2.math) {
                    return o1.name - o2.name; //이름 오름차순
                }
                return o2.math - o1.math; //수학 내림차순
            }
            return o2.science - o1.science; //과학 내림차순
        }

    };

    static class Student implements Comparable<Student> {

        char name;
        int math;
        int science;

        public Student(char name, int math, int science) {
            this.name = name;
            this.math = math;
            this.science = science;
        }

        /*
         * 수학점수가 높은 순
         * 수학점수가 같을 경우 과학 점수가 높은 순
         * 둘 다 같은 경우 이름순
         * */
        @Override
        public int compareTo(Student o) {
            if (this.math == o.math) {
                if (this.science == o.science) {
                    return this.name - o.name; //이름 오름차순
                }
                return o.science - this.science; //과학 내림차순
            }
            return o.math - this.math; //수학 내림차순
        }

        @Override
        public String toString() {
            return name + "\t" + math + "\t" + science + "\n";
        }

    }

}