package stack;

public class StackApplication {

    public static void main(String[] args) {

        Stack<Integer> stack = new Stack<>();
        Integer target = 3;

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        System.out.println("search in stack = " + stack.search(target));

        while (stack.size() != 0) {
            System.out.println("stack top value : " + stack.peek());
            stack.pop();
        }

        System.out.println("stack.empty() = " + stack.empty());

    }

}
