package linkedList;

public class LinkedList {

    private ListNode head;

    public LinkedList() {
        head = null; //head 초기화
    }

    //Node 삽입(중간에 삽입)
    public void insertNode(ListNode preNode, String data) {

        ListNode newNode = new ListNode(data); //새로운 노드 생성

        //연결 작업
        newNode.link = preNode.link;
        preNode.link = newNode;

    }

    //Node 삽입(마지막에 삽입)
    public void insertNode(String data) {

        ListNode newNode = new ListNode(data); //새로운 노드 생성
        if (head == null) { //head가 null인 경우 새로운 노드를 참조하도록 함
            this.head = newNode;
        } else {
            ListNode tempNode = head;

            //마지막 노드 탐색
            while (tempNode.link != null) {
                tempNode = tempNode.link; //다음 노드를 참조
            }

            tempNode.link = newNode;
        }

    }

    //Node 삭제(중간 노드 삭제)
    public void deleteNode(String data) {

        ListNode preNode = head;
        ListNode tempNode = head.link;

        //첫 번째 노드 삭제
        if (data.equals(preNode.getData())) {
            head = preNode.link; //다음 노드 참조
            preNode.link = null; //연결 해제
        } else {
            while (tempNode != null) {
                //타깃 일치
                if (data.equals(tempNode.getData())) {
                    //마지막 노드 삭제
                    if (tempNode.link == null) {
                        preNode.link = null;
                    }
                    //중간 노드 삭제
                    else {
                        //연결 작업
                        preNode.link = tempNode.link;
                        tempNode.link = null;
                    }
                    break;
                }
                //타깃 불일치 -> 다음 노드로 이동
                else {
                    preNode = tempNode;
                    tempNode = tempNode.link;
                }
            }
        }
    }

    //Node 삭제(마지막 노드 삭제)
    public void deleteNode() {

        ListNode preNode;
        ListNode tempNode;

        //모든 노드가 삭제된 상황
        if (head == null) {
            return;
        }

        //노드가 1개 남았을 경우
        if (head.link == null) {
            head = null;
        } else {
            preNode = head;
            tempNode = head.link;

            //마지막 노드 탐색
            while (tempNode.link != null) {
                preNode = tempNode;
                tempNode = tempNode.link;
            }

            preNode.link = null; //마지막 노드 삭제
        }

    }

    //Node 탐색
    public ListNode searchNode(String data) {

        ListNode tempNode = this.head;

        //타깃 탐색
        while (tempNode != null) {
            //탐색 완료
            if (data.equals(tempNode.getData())) {
                return tempNode;
            } else {
                tempNode = tempNode.link; //다음 노드 할당
            }
        }

        return tempNode;

    }

    //reverse linked list
    public void reverseList() {

        ListNode nextNode = head; //head 할당
        ListNode currentNode = null;
        ListNode preNode = null;

        //전체 순회
        while (nextNode != null) {
            //다음 노드로 이동
            preNode = currentNode;
            currentNode = nextNode;
            nextNode = nextNode.link;
            currentNode.link = preNode; //현재 노드의 링크 방향을 역방향으로
        }

        //currentNode가 마지막 노드를 가리킬 때, head로 지정
        head = currentNode;

    }

    //Linked List 출력
    public void printList() {

        ListNode tempNode = this.head;

        //전체 순회
        while (tempNode != null) {
            System.out.print(tempNode.getData() + " ");
            tempNode = tempNode.link; //다음 노드 할당
        }
        System.out.println();

    }

}

