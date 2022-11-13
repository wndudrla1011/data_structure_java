package set.hashSet;

import interface_form.SetInterface;

import java.util.Arrays;

public class HashSet<E> implements SetInterface<E> {

    //최소 기본 용적이며 2^n 꼴 형태가 좋음
    private final static int DEFAULT_CAPACITY = 1 << 4; //Node 배열의 기본 및 최소 용적(16)

    // 3 / 4이상 채워질 경우 resize
    private final static float LOAD_FACTOR = 0.75f;

    Node<E>[] table; //해시 테이블(요소 정보 저장)
    private int size; //요소의 개수

    @SuppressWarnings("unchecked")
    public HashSet() {
        table = (Node<E>[]) new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    //보조 해시 함수 (상속 방지를 위해 private static final 선언)
    private static final int hash(Object key) {

        int hash;
        if (key == null) {
            return 0;
        } else {
            //hashCode()의 경우 음수가 나올 수 있으므로 절댓값을 통해 양수로 변환
            return Math.abs(hash = key.hashCode()) ^ (hash >>> 16);
        }

    }

    @SuppressWarnings("unchecked")
    private void resize() {

        int newCapacity = table.length * 2;

        //기존 테이블의 2배 용적으로 생성
        final Node<E>[] newTable = (Node<E>[]) new Node[newCapacity];

        //0번째 index부터 차례대로 출력
        for (int i = 0; i < table.length; i++) {
            //각 인덱스의 첫 번째 노드(head)
            Node<E> value = table[i]; //value는 link를 타고 노드를 순회

            //해당 값이 없을 경우 다음으로
            if (value == null) {
                continue;
            }

            table[i] = null; //gc

            Node<E> nextNode; //현재 노드의 다음 노드

            //현재 인덱스에 연결된 노드들을 순회
            while (value != null) {
                int idx = value.hash % newCapacity; //새로운 index

                /*
                * 새로 담을 index에 요소(노드)가 존재하는 경우
                * == 새로 담을 newTable에 index값이 겹칠 경우 (해시 충돌)
                * */
                if (newTable[idx] != null) {
                    Node<E> tail = newTable[idx];

                    //가장 마지막 노드로 이동
                    while (tail.next != null) {
                        tail = tail.next;
                    }
                    /*
                     * 반드시 value가 참조하고 있는 다음 노드와의 연결을 끊어주어야 함
                     * 안하면 각 인덱스의 마지막 노드(tail)도 다른 노드를 참조하게 되어
                     * 잘못된 참조가 발생할 수 있음
                     * */
                    nextNode = value.next; //value 갱신용
                    value.next = null; //링크 제거
                    tail.next = value; //새 테이블의 해당 인덱스에 링크 연결
                }
                //충돌되지 않는다면(=빈 공간이라면 해당 노드 추가)
                else {
                    /*
                    * 반드시 value가 참조하고 있는 다음 노드와의 연결을 끊어주어야 함
                    * 안하면 각 인덱스의 마지막 노드(tail)도 다른 노드를 참조하게 되어
                    * 잘못된 참조가 발생할 수 있음
                    * */
                    nextNode = value.next; //value 갱신용
                    value.next = null; //링크 제거
                    newTable[idx] = value; //새 테이블에 삽입
                }

                value = nextNode; //다음 노드로 이동(value 갱신)
            }
        }
        table = null; //table gc 처리
        table = newTable; //table 교체

    }

    @Override
    public boolean add(E e) {
        //key(e)에 대해 만들어두었던 보조해시함수의 값과 key(데이터 e)를 보냄
        return add(hash(e), e) == null;
    }

    private E add(int hash, E key) {

        int idx = hash % table.length;

        //table[idx]가 비어있을 경우 새 노드 생성
        if (table[idx] == null) {
            table[idx] = new Node<E>(hash, key, null);
        }

        /*
         * table[idx]에 요소가 이미 존재할 경우(==해시 충돌)
         *
         * 두 가지 경우의 수
         * 1. 객체가 같은 경우
         * 2. 객체는 같지 않으나 얻어진 index가 같은 경우
         * */
        else {
            Node<E> temp = table[idx]; //현재 위치 노드
            Node<E> prev = null; //temp의 이전 노드

            //첫 노드(table[idx])부터 탐색
            while (temp != null) {
                /*
                * 만약 현재 노드의 객체가 같은 경우(hash값이 같으면서 key가 같을 경우)는
                * HashSet은 중복을 허용하지 않으므로 key를 반납(반환)
                * Key가 같은 경우는 주소가 같거나 객체가 같은 경우가 존재
                * */
                if ((temp.hash == hash) && (temp.key == key || temp.key.equals(key))) {
                    return key;
                }
                prev = temp;
                temp = temp.next;
            }

            //마지막 노드에 새 노드 연결
            prev.next = new Node<E>(hash, key, null);
        }
        size++;

        //데이터의 개수가 현재 table 용적의 75%를 넘어가는 경우 용적 늘림
        if (size >= LOAD_FACTOR * table.length) {
            resize();
        }

        return null; //정상 추가되었을 경우

    }

    @Override
    public boolean remove(Object o) {
        //null이 아니라는 것은 노드가 정상 삭제되었다는 의미
        return remove(hash(o), o) != null;
    }

    private Object remove(int hash, Object key) {

        int idx = hash % table.length;

        Node<E> node = table[idx]; //indx의 head 노드
        Node<E> removedNode = null; //삭제 대상 노드를 담을 변수
        Node<E> prev = null; //이전 노드를 담을 변수

        //삭제할 노드가 없음
        if (node == null) {
            return null;
        }

        //삭제할 노드 탐색
        while (node != null) {
            //같은 노드를 찾았다면
            if (node.hash == hash && (node.key == key || node.key.equals(key))) {
                removedNode = node; //삭제되는 노드를 반환하기 위한 변수

                //해당 노드의 이전 노드가 존재하지 않는 경우(head 노드인 경우)
                if (prev == null) {
                    table[idx] = node.next; //head의 다음 노드가 head가 됨
                } else {
                    prev.next = node.next; //삭제 노드 기준, 앞 뒤 노드 연결
                    node = null; //노드 삭제
                }
                size--;
                break; //탐색 종료
            }
            //탐색 실패 후 다음 노드로 이동
            prev = node;
            node = node.next;
        }

        return removedNode;

    }

    @Override
    public boolean contains(Object o) {

        int idx = hash(o) % table.length;
        Node<E> temp = table[idx];

        /*
        * 같은 객체 내용이어도 hash값은 다를 수 있음
        * 하지만, 내용이 같은지를 알아보고 싶을 때 쓰는 메소드이기에
        * hash값은 따로 비교 안해주어도 큰 지장 없음
        * 단, o가 null인지는 확인해야 함
        * */
        while (temp != null) {
            //같은 객체를 찾앗을 경우
            if ((o == temp.key) || (o != null && o.equals(temp.key))) {
                return true;
            }
            temp = temp.next;
        }

        return false;

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {

        if (table != null && size > 0) {
            for (int i = 0; i < table.length; i++) {
                table[i] = null;
            }
            size = 0;
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {

        //만약 파라미터 객체가 현재 객체와 동일한 객체라면
        if (obj == this) {
            return true;
        }

        //만약 obj 객체가 HashSet이 아닌 경우
        if (!(obj instanceof HashSet)) {
            return false;
        }

        HashSet<E> oSet;

        /*
        * Object로부터 HashSet<E>로 캐스팅되어야 비교 가능
        * 만약 캐스팅이 불가능할 경우 ClassCastException 발생
        * 이 경우 false를 return
        * */
        try {
            //HashSet 타입으로 캐스팅
            oSet = (HashSet<E>) obj;
            //사이즈(요소 개수)가 다르다는 것은 명백히 다른 개체
            if (oSet.size() != size) {
                return false;
            }

            //HashSet의 각 index 검사
            for (int i = 0; i < oSet.table.length; i++) {
                Node<E> oTable = oSet.table[i];

                //각 index(연결리스트)의 구성 요소 일치 확인
                while (oTable != null) {
                    /*
                     * 서로 Capacity가 다를 수 있기 때문에 index에 연결된 원소들을
                     * 비교하는 것이 아닌 contains로 원소의 존재 여부를 확인해야 함
                     * */
                    if (!contains(oTable)) {
                        return false; //하나라도 존재하지 않으면 다른 객체이므로 false
                    }
                    oTable = oTable.next;
                }
            }
        } catch (ClassCastException e) {
            return false;
        }
        //위 검사가 모두 완료되면 같은 객체임이 증명됨
        return true;

    }

    public Object[] toArray() {

        if (table == null) {
            return null;
        }

        Object[] ret = new Object[size];
        int index = 0;

        for (int i = 0; i < table.length; i++) {
            Node<E> node = table[i];

            //해당 인덱스에 연결된 모든 노드를 하나씩 담음
            while (node != null) {
                ret[index] = node.key;
                index++;
                node = node.next;
            }
        }

        return ret;

    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {

        Object[] copy = toArray(); //toArray() 통해 먼저 배열을 얻음

        //들어온 배열이 copy된 요소 개수보다 작을 경우 size에 맞게 늘려주면서 복사
        if (a.length < size) {
            return (T[]) Arrays.copyOf(copy, size, a.getClass());
        }

        //그 외에는 copy배열을 a에 0번째부터 채움
        System.arraycopy(copy, 0, a, 0, size);

        return a;

    }

}
