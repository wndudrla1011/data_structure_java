package deque.array;

public class ArrayDequeApplication {

    public static void main(String[] args) {

        ArrayDeque<Coffee> deque = new ArrayDeque<>();
        Coffee c = new Coffee("연유라떼", 4000);

        deque.offer(new Coffee("콜롬비아", 4000));
        deque.offer(new Coffee("아메리카노", 2000));
        deque.offer(c);
        deque.offerFirst(new Coffee("솔트라떼", 4500));

        deque.toDeque();
        System.out.println();

        System.out.println("Do you have a 연유라떼? = " + deque.contains(c));
        System.out.println();

        System.out.println("deque.peek() = " + deque.peek());
        System.out.println("deque.peekLast() = " + deque.peekLast());
        System.out.println();

        System.out.println("deque.poll() = " + deque.poll());
        System.out.println("deque.pollLast() = " + deque.pollLast());
        System.out.println();

        deque.toDeque();
        System.out.println();

        System.out.println("deque.size() = " + deque.size());
        System.out.println();

        System.out.println("deque.poll() = " + deque.poll());
        System.out.println("deque.pollLast() = " + deque.pollLast());
        System.out.println();

        System.out.println("deque.isEmpty() = " + deque.isEmpty());

    }

    static class Coffee {

        String name;
        int price;

        public Coffee(String name, int price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Coffee{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }

    }

}