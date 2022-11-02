package queue.linkedList;

import interface_form.QueueInterface;

public class LinkedListQueue<E> implements QueueInterface<E> {

    private Node<E> head; //가장 앞 노드
    private Node<E> tail; //가장 뒷 노드
    private int size; //큐 요소 개수

    public LinkedListQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public boolean offer(E value) {

        Node<E> newNode = new Node<>(value);

        //비어있을 경우
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode; //tail 대상 노드 변경
        size++;

        return true;

    }

    @Override
    public E poll() {

        //삭제할 요소가 없을 경우 null 반환
        if (size == 0) {
            return null;
        }

        //삭제될 요소 임시 보관
        E elem = head.data;

        Node<E> nextNode = head.next;

        //head의 모든 데이터 삭제
        head.data = null;
        head.next = null;

        //head = head.next
        head = nextNode;
        size--;

        return elem;

    }

    @Override
    public E peek() {

        //삭제할 요소가 없을 경우 null 반환
        if (size == 0) {
            return null;
        }

        return head.data;

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object value) {

        for (Node<E> e = head; e != null; e = e.next) {
            if (value.equals(e.data)) {
                return true;
            }
        }

        return false;

    }

    public void clear() {

        for (Node<E> e = head; e != null;) {
            Node<E> next = e.next;
            e.data = null;
            e.next = null;
            e = next;
        }

        size = 0;
        head = tail = null;

    }

    public void toQueue() {

        for (Node<E> e = head; e != null; ) {
            Node<E> next = e.next;
            System.out.println("e.data = " + e.data);
            e = next;
        }

    }

}
