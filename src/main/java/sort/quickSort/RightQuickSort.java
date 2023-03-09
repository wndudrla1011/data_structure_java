package sort.quickSort;

public class RightQuickSort {

    public static void sort(int[] a) {
        r_pivot_sort(a, 0, a.length - 1);}

    /*
     * 오른쪽 피벗 선택 방식
     * @param a : 정렬할 배열
     * @param lo : 현재 부분배열의 왼쪽
     * @param hi : 현재 부분배열의 오른쪽
     * */
    private static void r_pivot_sort(int[] a, int lo, int hi) {
        /*
        * lo가 hi보다 크거나 같다면
        * 정렬할 원소가 1개 이하이므로
        * 정렬하지 않고 리턴
        * */
        if (lo >= hi) return;

        /*
        * 피벗을 기준으로 요소들이 왼쪽과 오른쪽으로 약하게 정렬된 상태로
        * 만들어 준 뒤, 최종적으로 pivot의 위치를 얻는다.
        *
        * 그리고나서 해당 피벗을 기준으로
        * 왼쪽 부분리스트와 오른쪽 부분리스트로 나누어 분할 정복
        *
        * [과정]
        *
        * Partitioning:
        *
        *         left part                right part       a[right]
        * +---------------------------------------------------------+
        * |    element < pivot    |    element >= pivot   |  pivot  |
        * +---------------------------------------------------------+
        *
        * result After Partitioning:
        *
        *         left part         a[hi]          right part
        * +---------------------------------------------------------+
        * |   element < pivot    |  pivot  |    element >= pivot    |
        * +---------------------------------------------------------+
        *
        * result : pivot = hi
        *
        * Recursion:
        *
        * r_pivot_sort(a, lo, pivot - 1)     r_pivot_sort(a, pivot + 1, hi)
        *
        *         left part                           right part
        * +-----------------------+             +-----------------------+
        * |   element <= pivot    |    pivot    |    element > pivot    |
        * +-----------------------+             +-----------------------+
        * lo                pivot - 1        pivot + 1                 hi
        *
        * */
        int pivot = partition(a, lo, hi);

        r_pivot_sort(a, lo, pivot - 1);
        r_pivot_sort(a, pivot + 1, hi);
    }

    /*
    * pivot을 기준으로 파티션을 나누기 위한 약한 정렬 메소드
    *
    * @param a : 정렬할 배열
    * @param left : 현재 배열의 가장 왼쪽 부분
    * @param right : 현재 배열의 가장 오른쪽 부분
    * @return : 최종적으로 위치한 피벗의 위치(hi)
    * */
    private static int partition(int[] a, int left, int right) {
        int lo = left;
        int hi = right;
        int pivot = a[right]; //부분리스트의 오른쪽 요소를 피벗으로 설정

        while (lo < hi) {
            //lo 방향에서 pivot보다 크거나 같은 요소 탐색
            while (a[lo] < pivot && lo < hi) lo++;
            /*
            * hi 방향에서 pivot보다 작은 요소 탐색
            * 크거나 같은 이유는 pivot의 시작 위치가 hi와 같기 때문
            * */
            while (a[hi] >= pivot && lo < hi) hi--;

            swap(a, lo, hi);
        }

        /*
        * 마지막으로 맨 처음 pivot으로 설정했던 위치(a[right])의 원소와
        * hi가 가리키는 원소를 바꾼다.
        * */
        swap(a, right, hi);

        return hi;
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

}
