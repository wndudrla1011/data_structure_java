package linkedList;

public class LinkedListApplication {

    public static void main(String[] args) {

        LinkedList linkedList = new LinkedList();
        String str = "wed";

        linkedList.insertNode("sun");
        linkedList.insertNode("mon");
        linkedList.insertNode("tue");
        linkedList.insertNode("wed");
        linkedList.insertNode("thu");
        linkedList.insertNode("fri");
        linkedList.insertNode("sat");
        linkedList.printList();

        System.out.println("linkedList 검색 = " + linkedList.searchNode(str).getData());

        linkedList.deleteNode(linkedList.searchNode(str).getData());
        linkedList.printList();

        str = "sun";

        linkedList.deleteNode(linkedList.searchNode(str).getData());
        linkedList.printList();

        linkedList.reverseList();
        linkedList.printList();

    }

}
