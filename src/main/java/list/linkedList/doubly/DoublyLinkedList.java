package list.linkedList.doubly;

import interface_form.ListInterface;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<E> implements ListInterface<E>, Cloneable {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private Node<E> search(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        //reverse tracking
        if (index > size / 2) {
            Node<E> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
        //in order tracking
        else {
            Node<E> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        }

    }

    public void addFirst(E value) {

        Node<E> newNode = new Node<>(value);
        newNode.next = head;

        if (head != null) {
            head.prev = newNode;
        }

        head = newNode;
        size++;

        //newNode 가 첫 노드
        if (head.next == null) {
            tail = head;
        }

    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    public void addLast(E value) {

        Node<E> newNode = new Node<>(value);

        if (size == 0) {
            addFirst(value);
            return;
        }

        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        size++;

    }

    @Override
    public void add(int index, E value) {

        //addLast case 를 고려하여 size index 허용
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            addFirst(value);
            return;
        }

        if (index == size) {
            addLast(value);
            return;
        }

        Node<E> prev_node = search(index - 1); //추가하려는 위치의 이전 노드
        Node<E> next_node = prev_node.next; //추가하려는 위치의 노드
        Node<E> newNode = new Node<>(value);

        //링크 제거
        prev_node.next = null;
        next_node.prev = null;

        //링크 수정
        prev_node.next = newNode;
        newNode.prev = prev_node;
        newNode.next = next_node;
        next_node.prev = newNode;

        size++;

    }

    public E remove() {

        Node<E> headNode = head;

        if (headNode == null) {
            throw new NoSuchElementException();
        }

        E elem = headNode.data; //반환용 데이터

        Node<E> nextNode = head.next;

        //head 삭제
        head.data = null;
        head.next = null;

        if (nextNode != null) { //NPE 방지
            nextNode.prev = null;
        }

        head = nextNode;
        size--;

        //삭제 후 빈 리스트가 된 경우
        if (size == 0) {
            tail = null;
        }

        return elem;

    }

    @Override
    public E remove(int index) {

        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            E elem = head.data;
            remove();
            return elem;
        }

        Node<E> prevNode = search(index - 1); //삭제할 노드의 이전 노드
        Node<E> removedNode = prevNode.next; //삭제할 노드
        Node<E> nextNode = removedNode.next; //삭제할 노드의 다음 노드

        E elem = removedNode.data; //반환용 데이터

        /*
        * index = 0 과 index < 0 인 경우는 이미 필터링됨
        * 즉, 분기 이후 prevNode 는 항상 존재함
        *
        * 링크 제거 및 삭제
        * */
        prevNode.next = null;
        removedNode.next = null;
        removedNode.prev = null;
        removedNode.data = null;

        //링크 삭제 + 수정
        if (nextNode != null) {
            nextNode.prev = null;

            nextNode.prev = prevNode;
            prevNode.next = nextNode;
        }
        //마지막 위치의 노드를 삭제한 경우
        else {
            tail = prevNode;
        }

        size--;

        return elem;

    }

    @Override
    public boolean remove(Object value) {

        Node<E> prevNode = head;
        Node<E> x = head;

        for (; x != null; x = x.next) {
            if (value.equals(x.data)) {
                break;
            }
            prevNode = x;
        }

        //일치하는 요소가 없을 경우
        if (x == null) {
            return false;
        }

        if (x.equals(head)) {
            remove();
            return true;
        } else { //remove(int index)
            Node<E> nextNode = x.next;

            //링크 및 데이터 제거
            prevNode.next = null;
            x.data = null;
            x.next = null;
            x.prev = null;

            if (nextNode != null) {
                nextNode.prev = null;

                nextNode.prev = prevNode;
                prevNode.next = nextNode;
            }
            //마지막 위치의 노드를 삭제한 경우
            else {
                tail = prevNode;
            }
            size--;

            return true;

        }

    }

    @Override
    public E get(int index) {
        return search(index).data;
    }

    @Override
    public void set(int index, E value) {
        Node<E> replaceNode = search(index);
        replaceNode.data = null;
        replaceNode.data = value;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) >= 0;
    }

    @Override
    public int indexOf(Object value) {

        int index = 0;

        for (Node<E> x = head; x != null; x = x.next) {
            if (value.equals(x.data)) {
                return index;
            }
            index++;
        }

        return -1;

    }

    public int lastIndexOf(Object value) {

        int index = size;

        for (Node<E> x = tail; x != null; x = x.prev) {
            index--;
            if (value.equals(x.data)) {
                return index;
            }
        }

        return -1;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {

        for (Node<E> x = head; x != null;) {
            Node<E> nextNode = x.next;
            x.data = null;
            x.next = null;
            x.prev = null;
            x = nextNode;
        }
        head = tail = null;
        size = 0;

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        @SuppressWarnings("unchecked")
        DoublyLinkedList<? super E> clone = (DoublyLinkedList<? super E>) super.clone();

        clone.head = null;
        clone.tail = null;
        clone.size = 0;

        for (Node<E> x = head; x != null; x = x.next) {
            clone.addLast(x.data);
        }

        return clone;

    }

    public Object[] toArray() {

        Object[] array = new Object[size];
        int idx = 0;
        for (Node<E> x = head; x != null; x = x.next) {
            array[idx++] = (E) x.data;
        }
        return array;

    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {

        if (a.length < size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        }

        int i = 0;
        Object[] result = a;
        for (Node<E> x = head; x != null; x = x.next) {
            result[i++] = x.data;
        }
        return a;

    }

    public void sort() {
        sort(null);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void sort(Comparator<? super E> c) {

        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);

        int i = 0;
        for (Node<E> x = head; x != null; x = x.next, i++) {
            x.data = (E) a[i];
        }

    }

}
