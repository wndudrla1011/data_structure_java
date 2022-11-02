package stack;

import interface_form.StackInterface;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack<E> implements StackInterface<E> {

    private static final int DEFAULT_CAPACITY = 10; //최소(기본) 용적 크기
    private static final Object[] EMPTY_ARRAY = {}; //빈 배열

    private Object[] array; //요소를 담을 배열
    private int size; //요소 개수

    //생성자1 (초기 공간 할당x)
    public Stack() {
        this.array = EMPTY_ARRAY;
        this.size = 0;
    }

    //생성자2 (초기 공간 할당o)
    public Stack(int capacity) {
        this.array = new Object[capacity];
        this.size = 0;
    }

    private void resize() {

        //빈 배열일 경우 (capacity is 0)
        if (Arrays.equals(array, EMPTY_ARRAY)) {
            array = new Object[DEFAULT_CAPACITY];
            return;
        }

        int arrayCapacity = array.length; //현재 용적 크기

        //용적이 가득 찰 경우
        if (size == arrayCapacity) {
            int newSize = arrayCapacity * 2; //더블링(메모리 할당)

            //배열 복사
            array = Arrays.copyOf(array, newSize);
            return;
        }

        //용적의 절반 미만으로 요소가 차지하고 있을 경우
        if (size < (arrayCapacity / 2)) {
            int newCapacity = (arrayCapacity / 2); //메모리 절약

            //배열 복사
            array = Arrays.copyOf(array, Math.max(DEFAULT_CAPACITY, newCapacity)); //최소 용적 크기 이상 유지
            return;
        }

    }

    @Override
    public E push(E item) {

        //용적이 꽉 차 있다면 용적을 재할당
        if (size == array.length) {
            resize();
        }

        array[size] = item; //마지막 위치에 요소 추가
        size++; //사이즈 1 증가

        return item;

    }

    @Override
    public E pop() {

        //삭제할 요소가 없다면 Stack이 비어 있다는 의미이므로 예외 발생시킴
        if (size == 0) {
            throw new EmptyStackException();
        }

        @SuppressWarnings("unchecked")
        E obj = (E) array[size - 1]; //삭제할 요소를 반환하기 위한 임시 변수

        array[size - 1] = null; //요소 삭제

        size--; //사이즈 1 감소
        resize(); //용적 재할당

        return obj;

    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek() {

        //삭제할 요소가 없다면 Stack이 비어있다는 의미이므로 예외 발생시킴
        if (size == 0) {
            throw new EmptyStackException();
        }

        return (E) array[size - 1];

    }

    @Override
    public int search(Object value) {

        //value가 null일 때는 equals(null)이 되어 NullPointerException 이 발생할 수 있음
        //== 로 null 값을 비교
        if (value == null) {
            for (int idx = size - 1; idx >= 0; idx--) {
                //배열 내 null 값 찾음
                if (array[idx] == null) {
                    return size - idx; //Top으로부터 거리
                }
            }
        } else {
            for (int idx = size - 1; idx >= 0; idx--) {
                //같은 객체를 찾았을 경우 size - idx 값을 반환
                if (array[idx].equals(value)) {
                    return size - idx;
                }
            }
        }

        return -1;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {

        //저장되어있던 모든 요소를 null 처리
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }

        size = 0;
        resize();

    }

    @Override
    public boolean empty() {
        return size == 0;
    }

}
