package heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Heap<E> {

    private final Comparator<? super E> comparator;
    private static final int DEFAULT_CAPACITY = 10; //최소(기본) 용적 크기

    private int size; //요소 개수

    private Object[] array; //요소를 담을 배열

    //생성자1 (초기 공간 할당x)
    public Heap() {
        this(null);
    }

    public Heap(Comparator<? super E> comparator) {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.comparator = comparator;
    }

    //생성자2(초기 공간 할당o)
    public Heap(int capacity) {
        this(capacity, null);
    }

    public Heap(int capacity, Comparator<? super E> comparator) {
        this.array = new Object[capacity];
        this.size = 0;
        this.comparator = comparator;
    }

    //받은 인덱스의 부모 노드 인덱스 반환
    private int getParent(int index) {
        return index / 2;
    }

    //받은 인덱스의 왼쪽 자식 노드 인덱스 반환
    private int getLeftChild(int index) {
        return index * 2;
    }

    //받은 인덱스의 오른쪽 자식 노드 인덱스 반환
    private int getRightChild(int index) {
        return index * 2 + 1;
    }

    /**
     * @param newCapacity : 새로운 용적 크기
     */
    private void resize(int newCapacity) {

        Object[] newArray = new Object[newCapacity];

        //copy array to newArray
        for (int i = 1; i <= size; i++) {
            newArray[i] = array[i];
        }

        /*
         * 현재 배열은 GC 처리를 위해 null 처리
         * 새 배열 연결
         * */
        this.array = null;
        this.array = newArray;

    }

    public void add(E value) {

        //배열 용적 Fulled -> Doubling
        if (size + 1 == array.length) {
            resize(array.length * 2);
        }

        siftUp(size + 1, value); //가장 마지막에 추가되는 위치와 타겟을 넘김
        size++; //재배치 후 사이즈 증가

    }

    /**
     * 상향 선별
     *
     * @param idx    추가할 노드의 인덱스
     * @param target 재배치 할 노드
     */
    private void siftUp(int idx, E target) {

        //comparator가 존재할 경우 comparator 을 인자로 넘김
        if (comparator != null) {
            siftUpComparator(idx, target, comparator);
        } else {
            siftUpComparable(idx, target);
        }

    }

    /**
     * Comparator을 이용한 sift-up
     *
     * @param : 외부로부터 로직 주입
     */
    @SuppressWarnings("unchecked")
    private void siftUpComparator(int idx, E target, Comparator<? super E> comp) {

        //root 노드보다 클 때까지만 탐색
        while (idx > 1) {
            int parent = getParent(idx); //삽입 노드의 부모노드 인덱스
            Object parentVal = array[parent]; //부모노드 값

            //타겟 노드 값이 부모노드보다 크면 반복문 종료
            if (comp.compare(target, (E) parentVal) >= 0) {
                break;
            }

            /*
             * 부모노드가 자식노드보다 크므로
             * 현재 삽입 될 위치에 부모노드 값으로 교체
             * 타겟 노드의 위치를 부모노드의 위치로 변경
             * */
            array[idx] = parentVal;
            idx = parent; //idx로 loop가 진행되므로 갱신
        }

        //최종적으로 삽입될 위치에 타겟 노드 값을 저장
        array[idx] = target;

    }

    /**
     * Comparable을 이용한 sift-up
     *
     * @param : 외부로부터 로직 주입x(Comparable 클래스 내부에서 처리)
     */
    @SuppressWarnings("unchecked")
    private void siftUpComparable(int idx, E target) {

        //Comparable 시그니처는 매개변수가 1개이므로, target을 comp로 만듦
        Comparable<? super E> comp = (Comparable<? super E>) target;

        while (idx > 1) {
            int parent = getParent(idx);
            Object parentVal = array[parent];

            if (comp.compareTo((E) parentVal) >= 0) {
                break;
            }

            array[idx] = parentVal;
            idx = parent;
        }

        array[idx] = comp;

    }

    @SuppressWarnings("unchecked")
    public E remove() {

        //만약 root가 비어 있을 경우 예외 던짐
        if (array[1] == null) {
            throw new NoSuchElementException();
        }

        E result = (E) array[1]; //삭제될 요소 임시 보관
        E target = (E) array[size]; //타겟이 될 요소
        array[size] = null; //타겟 노드를 비움

        //삭제할 노드의 인덱스와 재배치할 타겟 노드 넘김
        siftDown(1, target); //루트 노드가 삭제되므로 1을 넘김

        return result;

    }

    /**
     * @param idx    삭제할 노드의 인덱스
     * @param target 재배치할 노드
     */
    private void siftDown(int idx, E target) {

        //comparator가 존재할 경우 comparator 을 인자로 넘김
        if (comparator != null) {
            siftDownComparator(idx, target, comparator);
        } else {
            siftDownComparable(idx, target);
        }

    }

    /**
     * Comparator을 이용한 sift-up
     *
     * @param : 외부로부터 로직 주입
     */
    @SuppressWarnings("unchecked")
    private void siftDownComparator(int idx, E target, Comparator<? super E> comp) {

        array[idx] = null; //노드 삭제
        size--;

        int parent = idx; //삭제 노드의 위치 = 재배치가 시작되는 인덱스
        int child; //교환될 자식 인덱스

        // 왼쪽 자식 노드의 인덱스가 요소의 개수보다 작을 때까지 반복
        while ((child = getLeftChild(parent)) <= size) {
            int right = getRightChild(parent); //오른쪽 자식 노드 인덱스

            Object childVal = array[child]; //왼쪽 자식 노드 인덱스

            /*
            * 왼쪽 자식이 오른쪽 자식보다 큰 경우
            * 왼쪽 자식으로 비교를 할 것이므로 오른쪽 자식으로 교체
            * */
            if (right <= size && comp.compare((E) childVal, (E) array[right]) > 0) {
                child = right;
                childVal = array[child];
            }

            //재배치할 노드가 자식 노드보다 작을 경우 반복문 종료
            if (comp.compare(target, (E) childVal) <= 0) {
                break;
            }

            /*
            * 재배치할 노드가 자식 노드보다 큰 경우
            * 부모와 자식 노드 스왑
            * */
            array[parent] = childVal;
            parent = child; //다음 비교를 위해 parent 갱신
        }

        //최종적으로 재배치되는 위치에 타겟 값을 넣음
        array[parent] = target;

        /*
        * 요소의 개수가 전체 1/4 미만이 된 경우
        * 용적 절반 감소시킴(단, Baseline = 최소 용적)
        * */
        if (array.length > DEFAULT_CAPACITY && size < array.length / 4) {
            resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
        }

    }

    /**
     * Comparable을 이용한 sift-up
     *
     * @param : 외부로부터 로직 주입x(Comparable 클래스 내부에서 처리)
     */
    @SuppressWarnings("unchecked")
    private void siftDownComparable(int idx, E target) {

        //Comparable 시그니처는 매개변수가 1개이므로, target을 comp로 만듦
        Comparable<? super E> comp = (Comparable<? super E>) target;

        array[idx] = null;
        size--;

        int parent = idx;
        int child;

        while ((child = getLeftChild(parent)) <= size) {
            int right = getRightChild(parent);

            Object childVal = array[child];

            if (right <= size && ((Comparable<? super E>) childVal).compareTo((E) array[right]) > 0) {
                child = right;
                childVal = array[child];
            }

            if (comp.compareTo((E) childVal) <= 0) {
                break;
            }

            array[parent] = childVal;
            parent = child;
        }

        array[parent] = comp;

        if (array.length > DEFAULT_CAPACITY && size < array.length / 4) {
            resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
        }

    }

    public int size() {
        return this.size;
    }

    @SuppressWarnings("unchecked")
    public E peek() {
        if (array[1] == null) {
            throw new NoSuchElementException();
        }
        return (E) array[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Object[] toArray() {
        return Arrays.copyOf(array, size + 1);
    }

}
