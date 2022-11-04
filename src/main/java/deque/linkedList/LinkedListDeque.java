package deque.linkedList;

import interface_form.QueueInterface;

public class LinkedListDeque<E> implements QueueInterface<E> {

    private Node<E> head; //가장 앞에 있는 노드를 가리키는 변수
    private Node<E> tail; //가장 뒤에 있는 노드를 가리키는 변수
    private int size; //요소(노드) 개수

    public LinkedListDeque() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean offer(E value) {
        return offerLast(value);
    }

    public boolean offerLast(E value) {

        //데이터가 없을 경우
        if (size == 0) {
            return offerFirst(value);
        }

        Node<E> newNode = new Node<>(value);

        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        size++;

        return true;

    }

    public boolean offerFirst(E value) {

        Node<E> newNode = new Node<>(value);
        newNode.next = head;

        /*
        * head가 null이 아닐 경우에만 기존 head 노드의 prev 변수가
        * 새 노드를 가리키도록 함
        * 기존 head 노드가 null인 경우, 데이터가 아무 것도 없는 상태이므로,
        * head.prev를 하면 잘못된 참조가 됨
        * */
        if (head != null) {
            head.prev = newNode;
        }

        head = newNode; //head가 새 노드를 가리키도록 함
        size++;

        /*
        * 다음에 가리킬 노드가 없는 경우(데이터가 새 노드밖에 없는 경우 == 이전 head가 null인 경우)
        * 데이터가 한 개(새 노드)밖에 없으므로 새 노드는 처음 시작 노드이자 마지막 노드
        * tail == head
        * */
        if (head.next == null) {
            tail = head;
        }

        return true;

    }

    @Override
    public E poll() {
        return pollFirst();
    }

    public E pollFirst() {

        //삭제할 데이터가 없는 경우
        if (size == 0) {
            return null;
        }

        E elem = head.data; //반환 데이터

        Node<E> nextNode = head.next;

        //삭제 처리
        head.data = null;
        head.next = null;

        //head 다음 노드가 있을 경우(새로운 head가 되므로 prev는 null)
        if (nextNode != null) {
            nextNode.prev = null;
        }

        head = null;
        head = nextNode; //head 갱신
        size--;

        /*
        * 삭제된 요소가 Deque의 유일한 요소였을 경우
        * 그 요소는 head이자 tail
        * 삭제되면서 tail도 가리킬 요소가 없기 때문에
        * size가 0일 경우, tail도 null 처리
        * */
        if (size == 0) {
            tail = null;
        }

        return elem;

    }

    public E pollLast() {

        //데이터가 없을 경우
        if (size == 0) {
            return null;
        }

        E elem = tail.data; //반환 데이터

        Node<E> prevNode = tail.prev;

        //삭제 처리
        tail.data = null;
        tail.prev = null;

        //tail 앞에 노드가 있을 경우(새로운 tail이 되므로 next를 null 처리)
        if (prevNode != null) {
            prevNode.next = null;
        }

        tail = null;
        tail = prevNode; //tail 갱신
        size--;

        /*
         * 삭제된 요소가 Deque의 유일한 요소였을 경우
         * 그 요소는 head이자 tail
         * 삭제되면서 head도 가리킬 요소가 없기 때문에
         * size가 0일 경우, head도 null 처리
         * */
        if (size == 0) {
            head = null;
        }

        return elem;

    }

    @Override
    public E peek() {
        return peekFirst();
    }

    public E peekFirst() {

        //데이터가 없을 경우
        if (size == 0) {
            return null;
        }

        return head.data;

    }

    public E peekLast() {

        //데이터가 없을 경우
        if (size == 0) {
            return null;
        }

        return tail.data;

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object value) {

        for (Node<E> e = head; e != null; e = e.next) {
            if (value.equals(value)) {
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
            e.prev = null;
            e = next;
        }

        size = 0;
        head = tail = null;

    }

    public void toDeque() {
        for (Node<E> e = head; e != null; e = e.next) {
            System.out.println("e.data = " + e.data);
        }
    }

}
