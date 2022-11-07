package queue.array;

public class ArrayQueueApplication {

    public static void main(String[] args) {

        ArrayQueue<Student> q = new ArrayQueue<Student>();
        Student s = new Student("조지아", 93);

        q.offer(new Student("티오피", 88));
        q.offer(s);
        q.offer(new Student("스벅", 72));
        q.offer(new Student("파스쿠치", 66));

        q.toQueue();
        System.out.println();

        System.out.println("q.peek() = " + q.peek());
        System.out.println("q.poll() = " + q.poll());
        System.out.println();

        q.toQueue();
        System.out.println();

        System.out.println("q.size() = " + q.size());
        System.out.println("조지아 탐색 = " + q.contains(s));
        System.out.println("q.isEmpty() = " + q.isEmpty());

    }

    static class Student {

        String name;
        int score;

        public Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", score=" + score +
                    '}';
        }

    }

}