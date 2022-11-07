package queue.linkedList;

public class LinkedListQueueApplication {

    public static void main(String[] args) {

        LinkedListQueue<Character> q = new LinkedListQueue<Character>();
        Character c = new Character("army_one", "A");

        q.offer(new Character("army_two", "B"));
        q.offer(c);
        q.offer(new Character("army_three", "C"));
        q.offer(new Character("army_four", "D"));

        q.toQueue();
        System.out.println();

        System.out.println("q.peek() = " + q.peek());
        System.out.println("q.poll() = " + q.poll());
        System.out.println();

        q.toQueue();
        System.out.println();

        System.out.println("q.size() = " + q.size());
        System.out.println("q.contains(c) = " + q.contains(c));
        System.out.println(">>>Clear Queue");
        q.clear();
        System.out.println("q.isEmpty() = " + q.isEmpty());

    }

    static class Character {

        String name;
        String grade;

        public Character(String name, String grade) {
            this.name = name;
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "Character{" +
                    "name='" + name + '\'' +
                    ", grade='" + grade + '\'' +
                    '}';
        }

    }

}