package queue.linkedList;

public class Node<E> {

    E data;
    Node<E> next; //다음 노드를 가리키는 변수

    public Node(E data) {
        this.data = data;
        this.next = null;
    }

}
