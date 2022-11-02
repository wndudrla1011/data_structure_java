package interface_form;

public interface StackInterface<E> {

    /**
     * 스택의 맨 위에 요소를 추가
     *
     * @param item 스택에 추가할 요소
     * @return 스택의 추가된 요소
     * */
    E push(E item);

    /**
     * 스택의 맨 위에 있는 요소를 제거하고 제거된 요소를 반환
     *
     * @return 제거 된 요소
     * */
    E pop();

    /**
     * 스택의 맨 위에 있는 요소를 제거하지 않고 반환
     *
     * @return 스택의 맨 위에 있는 요소
     */
    E peek();

    /**
     * 스택의 상단으로부터 특정 요소가 몇 번째 위치에 있는지를 반환
     * 중복되는 원소가 있을 경우, 가장 위에 있는 요소의 위치가 반환됨
     *
     * @param value 스택에서 위치를 찾을 요소
     * @return 스택의 상단으로부터 처음으로 요소와 일치하는 위치를 반환
     * 만약 일치하는 요소가 없을 경우 -1을 반환
     */
    /*
     *         ________
     *         | a    |
     * idx 3   |______|   search("w")
     *         | e    |   --> 상단(idx 3)으로 부터 3번 째에 위치
     * idx 2   |______|       == return 되는 값 : 3
     *         | w    |
     * idx 1   |______|
     *         | k    |
     * idx 0   |______|
     *
     */
    int search(Object value);

    /**
     * 스택의 요소 개수를 반환
     *
     * @return 스택에 있는 요소 개수를 반환
     * */
    int size();

    /*
     * 스택에 있는 모든 요소를 삭제
     * */
    void clear();

    /**
     * 스택에 요소가 비어있는지를 반환
     *
     * @return 스택에 요소가 없을 경우 {<code>true</code>}, 그 외의 경우 {<code>false</code>} 반환
     * */
    boolean empty();

}
