package list.linkedList.single;

import interface_form.ListInterface;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements ListInterface<E>, Cloneable {

    private Node<E> head; //노드의 첫 부분
    private Node<E> tail; //노드의 끝 부분
    private int size; //요소 개수

    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    //특정 위치의 노드를 반환
    private Node<E> search(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> x = head; //head부터 시작

        for (int i = 0; i < index; i++) {
            x = x.next;
        }

        return x;

    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    public void addLast(E value) {

        Node<E> newNode = new Node<>(value);

        if (size == 0) { //첫 노드일 경우
            addFirst(value);
            return;
        }

        //Connection 작업
        tail.next = newNode;
        tail = newNode;
        size++;

    }

    @Override
    public void add(int index, E value) {

        //tail 뒤에 추가하는 경우도 있으므로, size index 까지 허용
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) { //첫 노드일 경우
            addFirst(value);
            return;
        }

        if (index == size) { //마지막 위치에 추가
            addLast(value);
            return;
        }

        //Connection 작업
        Node<E> prev_node = search(index - 1);
        Node<E> next_node = prev_node.next;
        Node<E> newNode = new Node<>(value);

        prev_node.next = null; //이전 노드의 링크 제거
        prev_node.next = newNode; //이전 노드의 링크 수정
        newNode.next = next_node; //삽입 위치 노드의 링크 수정
        size++;

    }

    public void addFirst(E value) {

        Node<E> newNode = new Node<>(value);
        newNode.next = head;
        head = newNode;
        size++;

        //추가하는 노드가 첫 노드일 경우
        if (head.next == null) {
            tail = head;
        }

    }

    //가장 앞에 있는 노드 제거
    public E remove() {

        Node<E> headNode = head;

        if (headNode == null) {
            throw new NoSuchElementException();
        }

        E elem = headNode.data; //삭제될 노드

        Node<E> nextNode = head.next;

        //head 삭제
        head.data = null;
        head.next = null;

        //리스트 갱신
        head = nextNode;
        size--;

        if (size == 0) { //삭제함으로써 빈 리스트가 된 경우
            tail = null;
        }

        return elem;

    }

    @Override
    public E remove(int index) {

        //삭제하려는 원소가 첫 번째 원소일 경우
        if (index == 0) {
            return remove();
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> prev_node = search(index - 1);
        Node<E> removedNode = prev_node.next; //삭제할 노드
        Node<E> next_node = removedNode.next;

        E elem = removedNode.data;

        prev_node.next = next_node; //링크 갱신

        if (prev_node.next == null) { //삭제할 노드가 last index's 노드라면
            tail = prev_node;
        }

        //노드 삭제
        removedNode.next = null;
        removedNode.data = null;
        size--;

        return elem;

    }

    @Override
    public boolean remove(Object value) {

        Node<E> prev_node = head;
        Node<E> x = head;

        for (; x != null; x = x.next) {
            if (value.equals(x.data)) {
                break;
            }
            prev_node = x; //prev_node를 1칸씩 당기기 위함
        }

        //일치하는 요소가 없을 경우
        if (x == null) {
            return false;
        }

        if (x.equals(head)) {
            remove();
            return true;
        } else {
            prev_node.next = x.next; //링크 수정

            if (prev_node.next == null) { //삭제할 노드가 last index's 노드라면
                tail = prev_node;
            }

            //노드 삭제
            x.data = null;
            x.next = null;
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

        for (Node<E> x = head; x != null; x = x.next) {
            Node<E> nextNode = x.next;
            x.data = null;
            x.next = null;
            x = nextNode;
        }
        head = tail = null;
        size = 0;

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        @SuppressWarnings("unchecked")
        SinglyLinkedList<? super E> clone = (SinglyLinkedList<? super E>) super.clone();

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
