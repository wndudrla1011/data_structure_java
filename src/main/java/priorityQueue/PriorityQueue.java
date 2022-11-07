package priorityQueue;

import interface_form.QueueInterface;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class PriorityQueue<E> implements QueueInterface<E> {

    private final Comparator<? super E> comparator;
    private static final int DEFAULT_CAPACITY = 10; //최소(기본) 용적 크기

    private int size; //요소 개수
    private Object[] array; //요소를 담을 배열

    //생성자1 (초기 공간 할당x)
    public PriorityQueue() {
        this(null);
    }

    public PriorityQueue(Comparator<? super E> comparator) {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.comparator = comparator;
    }

    //생성자2 (초기 공간 할당o)
    public PriorityQueue(int capacity) {
        this(capacity, null);
    }

    public PriorityQueue(int capacity, Comparator<? super E> comparator) {
        this.array = new Object[capacity];
        this.size = 0;
        this.comparator = comparator;
    }

    //받은 인덱스의 부모 노드 인덱스를 반환
    private int getParent(int index) {
        return index / 2;
    }

    //받은 인덱스의 왼쪽 자식 노드 인덱스를 반환
    private int getLeftChild(int index) {
        return index * 2;
    }

    //받은 인덱스의 오른쪽 자식 노드 인덱스를 반환
    private int getRightChild(int index) {
        return index * 2 + 1;
    }

    /**
     * @param newCapacity 새로운 용적 크기
     */
    private void resize(int newCapacity) {

        //새로 만들 배열
        Object[] newArray = new Object[newCapacity];

        //새 배열에 기존에 있던 배열의 요소들을 모두 복사
        for (int i = 1; i <= size; i++) {
            newArray[i] = array[i];
        }

        /*
        * 현재 배열은 GC 처리를 위해 null로 처리
        * 새 배열을 array에 연결
        * */
        this.array = null;
        this.array = newArray;

    }

    @Override
    public boolean offer(E value) {

        //배열 용적 Fulled -> Doubling
        if (size + 1 == array.length) {
            resize(array.length * 2);
        }

        siftUp(size + 1, value); //idx : 추가 위치, value : 추가되는 값
        size++;

        return true;

    }

    /*
     * 상향 선별
     *
     * @param idx 추가할 노드의 인덱스
     * @param target 재배치할 노드
     * */
    private void siftUp(int idx, E target) {

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

        while (idx > 1) {
            int parent = getParent(idx); //삽입할 노드의 부모 index
            Object parentVal = array[parent]; //삽입할 노드의 부모 값

            //삽입 노드 우선순위(값)이 부모보다 크면 종료
            if (comp.compare(target, (E) parentVal) >= 0) {
                break;
            }

            /*
            * 부모 노드가 타깃 노드보다 우선순위가 크므로
            * 현재 삽입될 위치에 부모노드 값으로 교체
            * 타깃 노드의 위치를 부모의 위치로 변경
            * */
            array[idx] = parentVal;
            idx = parent;
        }

        //최종적으로 삽입 될 위치에 타깃 노드 요소 저장
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

    @Override
    public E poll() {

        //root가 null인 경우
        if (array[1] == null) {
            return null;
        }

        return remove();

    }

    @SuppressWarnings("unchecked")
    public E remove() {

        //root가 null인 경우
        if (array[1] == null) {
            throw new NoSuchElementException();
        }

        E result = (E) array[1]; //삭제된 요소 임시 저장
        E target = (E) array[size]; //재배치할 요소

        array[size] = null; //타겟 노드를 비움
        size--;
        siftDown(1, target); //루트 노드가 삭제되므로 1을 넘김

        return result;

    }

    /**
    * 하향 선별
    *
    * @param idx 삭제할 노드의 인덱스
    * @param target 재배치할 노드
    * */
    private void siftDown(int idx, E target) {

        if (comparator != null) {
            siftDownComparator(idx, target, comparator);
        } else {
            siftDownComparable(idx, target);
        }

    }

    /**
     * Comparator을 이용한 sift-down
     *
     * @param : 외부로부터 로직 주입
     */
    @SuppressWarnings("unchecked")
    private void siftDownComparator(int idx, E target, Comparator<? super E> comp) {

        array[idx] = null; //노드 삭제

        int parent = idx; //삭제 노드의 위치 = 재배치가 시작되는 인덱스
        int child; //교환될 자식 인덱스

        // 왼쪽 자식 노드의 인덱스가 요소의 개수보다 작을 때까지 반복
        while ((child = getLeftChild(parent)) <= size) {
            int right = getRightChild(parent); //오른쪽 자식 노드 인덱스
            Object childVal = array[child];//왼쪽 자식 노드 값

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
            parent = child;
        }

        //최종적으로 재배치되는 위치에 타겟 값을 넣음
        array[parent] = target;

        /*
         * 요소의 개수가 전체 1/4 미만이 된 경우
         * 용적 절반 감소시킴(단, Baseline = 최소 용적)
         * */
        if (array.length > DEFAULT_CAPACITY && size < array.length / 4) {
            resize(Math.max(DEFAULT_CAPACITY, array.length) / 2);
        }

    }

    /**
     * Comparable을 이용한 sift-down
     *
     * @param : 외부로부터 로직 주입x(Comparable 클래스 내부에서 처리)
     */
    @SuppressWarnings("unchecked")
    private void siftDownComparable(int idx, E target) {

        //Comparable 시그니처는 매개변수가 1개이므로, target을 comp로 만듦
        Comparable<? super E> comp = (Comparable<? super E>) target;

        array[idx] = null;

        int parent = idx;
        int child;

        //parent << 1 : parent * 2^1
        while ((child = (parent << 1)) <= size) {
            int right = child + 1;

            Object c = array[child];

            if (right <= size && ((Comparable<? super E>) c).compareTo((E) array[right]) > 0) {
                child = right;
                c = array[child];
            }

            if (comp.compareTo((E) c) <= 0) {
                break;
            }

            array[parent] = c;
            parent = child;
        }

        array[parent] = comp;

        if (array.length > DEFAULT_CAPACITY && size < array.length / 4) {
            resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public E peek() {

        //root 요소가 null
        if (array[1] == null) {
            throw new NoSuchElementException();
        }
        return (E) array[1];

    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object value) {

        for (int i = 1; i <= size; i++) {
            if (array[i].equals(value)) {
                return true;
            }
        }

        return false;

    }

    public void clear() {
        Arrays.fill(array, null);
        size = 0;
    }

    public Object[] toArray() {
        return toArray(new Object[size]);
    }

    public <T> T[] toArray(T[] a) {
        //a 배열(붙여넣을 배열)이 size보다 작은 경우 -> 요소 전체 복사
        if (a.length <= size) {
            return (T[]) Arrays.copyOfRange(array, 1, size + 1, a.getClass());
        }
        System.arraycopy(array, 1, a, 0, size);
        return a;
    }

}
