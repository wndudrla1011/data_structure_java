package deque.array;

import interface_form.QueueInterface;

import java.util.Arrays;

public class ArrayDeque<E> implements QueueInterface<E> {

    private static final int DEFAULT_CAPACITY = 64; //최소(기본) 용적 크기

    private Object[] array; //요소를 담을 배열
    private int size; //요소 개수

    private int front; //start index (빈 공간)
    private int rear; //last index

    //생성자1 (초기 용적 할당을 안할 경우)
    public ArrayDeque() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    //생성자2 (초기 용적 할당을 할 경우)
    public ArrayDeque(int capacity) {
        this.array = new Object[capacity];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    private void resize(int newCapacity) {

        int arrayCapacity = array.length; //현재 용적 크기

        Object[] newArray = new Object[newCapacity]; //용적을 변경한 배열

        /*
        * i = new array index
        * j = original array
        * index 요소 개수(size) 만큼 새 배열에 값 복사
        * */
        for (int i = 1, j = front + 1; i <= size; i++, j++) {
            newArray[i] = array[j % arrayCapacity];
        }

        this.array = null;
        this.array = newArray; //새 배열 -> 기존 배열

        front = 0;
        rear = size;

    }

    @Override
    public boolean offer(E item) {
        return offerLast(item);
    }

    public boolean offerLast(E item) {

        //용적이 가득 찼을 경우
        if ((rear + 1) % array.length == front) {
            resize(array.length * 2); //용적 더블링
        }

        rear = (rear + 1) % array.length; //rear 다음 위치로

        array[rear] = item;
        size++;

        return true;

    }

    public boolean offerFirst(E item) {

        //용적이 가득 찼을 경우
        if ((front - 1 + array.length) % array.length == rear) {
            resize(array.length * 2); //용적 더블링
        }

        array[front] = item;
        front = ((front - 1) + array.length) % array.length; //front 전방 이동
        size++;

        return true;

    }

    @Override
    public E poll() {
        return pollFirst();
    }

    public E pollFirst() {

        //삭제할 요소가 없을 경우
        if (size == 0) {
            return null;
        }

        front = (front + 1) % array.length; //front 다음 칸 이동

        @SuppressWarnings("unchecked")
        E item = (E) array[front]; //반환 데이터 임시 저장

        array[front] = null; //데이터 삭제
        size--;

        // 용적이 최소 크기(64)보다 크고 요소 개수가 1/4 미만일 경우
        if (array.length > DEFAULT_CAPACITY && size < (array.length / 4)) {
            //용적 조정 (하한선 = DEFAULT_CAPACITY)
            resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
        }

        return item;

    }

    public E pollLast() {

        //삭제할 요소가 없을 경우
        if (size == 0) {
            return null;
        }

        @SuppressWarnings("unchecked")
        E item = (E) array[rear]; //반환 데이터 임시 저장

        array[rear] = null; //데이터 삭제
        rear = (rear - 1 + array.length) % array.length; //rear 전방 이동
        size--;

        // 용적이 최소 크기(64)보다 크고 요소 개수가 1/4 미만일 경우
        if (array.length > DEFAULT_CAPACITY && size < (array.length / 4)) {
            //용적 조정 (하한선 = DEFAULT_CAPACITY)
            resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
        }

        return item;

    }

    @Override
    public E peek() {
        return peekFirst();
    }

    public E peekFirst() {

        //요소가 없을 경우
        if (size == 0) {
            return null;
        }

        @SuppressWarnings("unchecked")
        E item = (E) array[(front + 1) % array.length];
        return item;

    }

    public E peekLast() {

        //요소가 없을 경우
        if (size == 0) {
            return null;
        }

        @SuppressWarnings("unchecked")
        E item = (E) array[rear];
        return item;

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object value) {

        int start = (front + 1) % array.length;

        /*
        * i : 요소 개수 만큼만 반복
        * idx : 데이터 범위 내 반복
        * */
        for (int i = 0, idx = start; i < size; i++, idx = (idx + 1) % array.length) {
            if (array[idx].equals(value)) {
                return true;
            }
        }

        return false;

    }

    public void clear() {
        Arrays.fill(array, null);
        front = rear = size = 0;
    }

    public void toDeque() {

        int start = (front + 1) % array.length;

        for (int i = 0, idx = start; i < size; i++, idx = (idx + 1) % array.length) {
            System.out.println("array[idx] = " + array[idx]);
        }

    }

}
