package deque.linkedList;

public class Node<E> {

    E data; //데이터를 담을 변수
    Node<E> next; //다음 노드 참조
    Node<E> prev; //이전 노드 참조

    public Node(E data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

}
