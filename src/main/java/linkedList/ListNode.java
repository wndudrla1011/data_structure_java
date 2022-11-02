package linkedList;

public class ListNode {
    private String data; //데이터 저장
    public ListNode link; //참조 링크

    public ListNode() {
        this.data = null;
        this.link = null;
    }

    public ListNode(String data) {
        this.data = data;
        this.link = null;
    }

    public ListNode(String data, ListNode link) {
        this.data = data;
        this.link = link;
    }

    public String getData() {
        return data;
    }

}
