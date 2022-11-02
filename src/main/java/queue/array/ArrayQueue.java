package queue.array;

import interface_form.QueueInterface;

public class ArrayQueue<E> implements QueueInterface<E> {

    private static final int DEFAULT_CAPACITY = 64; //최소(기본) 용적 크기

    private Object[] array; //요소를 담을 배열
    private int size; //요소 개수

    private int front; //시작 인덱스(빈 공간)
    private int rear; //마지막 인덱스

    //생성자1(초기 용적 할당을 안할 경우)
    public ArrayQueue() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    //생성자2(초기 용적 할당을 할 경우)
    public ArrayQueue(int capacity) {
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
        * index 요소 개수(size)만큼 새 배열에 값 복사
        * */
        for (int i = 1, j = front + 1; i <= size; i++, j++) {
            newArray[i] = array[j % arrayCapacity];
        }

        this.array = null;
        this.array = newArray; //새 배열을 기존 arry의 배열로 덮어씌움

        front=0;
        rear = size;

    }

    @Override
    public boolean offer(E item) {

        //용적이 가득 찼을 경우
        if ((rear + 1) % array.length == front) {
            resize(array.length * 2); //용적 더블링
        }

        rear = (rear + 1) % array.length; //rear을 rear의 다음 위치로 갱신

        array[rear] = item;
        size++; //사이즈 1 증가

        return true;

    }

    @Override
    public E poll() {

        //삭제할 요소가 없을 경우
        if (size == 0) {
            return null;
        }

        front = (front + 1) % array.length; //front를 한 칸 옮김

        @SuppressWarnings("unchecked")
        E item = (E) array[front]; //반환할 데이터를 임시 저장

        array[front] = null; //front 위치 데이터 삭제
        size--; //사이즈 1 감소

        //용적이 최소 크기(64)보다 크고 요소 개수가 1/4 미만일 경우
        if (array.length > DEFAULT_CAPACITY && size < (array.length / 4)) {
            //아무리 작아도 최소 용적 미만으로 줄이지는 않도록 함
            resize(Math.max(DEFAULT_CAPACITY, array.length) / 2);
        }

        return item;

    }

    @Override
    public E peek() {

        //삭제할 요소가 없을 경우
        if (size == 0) {
            return null;
        }

        @SuppressWarnings("unchecked")
        E item = (E) array[(front + 1) % array.length];

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
        * i : 요소 개수 만큼만 반복(전체 이동)
        * idx : 원소 위치로, 매회 (idx + 1) % array.length 위치로 갱신(데이터 내 이동)
        *
        * 전체 순회하면서 데이터 방 중에서 value 찾기 -> 효율적
        * */
        for (int i = 0, idx = start; i < size; i++, idx = (idx + 1) % array.length) {
            if (array[idx].equals(value)) {
                return true;
            }
        }

        return false;

    }

    public void clear() {

        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }

        front = rear = size = 0;

    }

    public void toQueue() {

        for (int i = 0, idx = (front + 1) % array.length; i < size; i++, idx = (idx + 1) % array.length) {
            System.out.println("array[idx] = " + array[idx]);
        }

    }

}
