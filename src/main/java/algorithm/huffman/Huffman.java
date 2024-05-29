package algorithm.huffman;

import java.util.*;

public class Huffman {

    static class Node {
        char word;
        int freq;
        Node left = null, right = null;

        Node(char word, int freq) {
            this.word = word;
            this.freq = freq;
        }

        Node(Node left, Node right) {
            this.freq = left.freq + right.freq; //항 합치기
            this.left = left;
            this.right = right;
        }
    }

    public void HuffmanCoding(String txt) {
        Map<Character, Integer> stat = new HashMap<>();
        for (char c : txt.toCharArray()) {
            stat.put(c, stat.getOrDefault(c, 0) + 1); //누적
        }

        //이진 트리 생성
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.freq));
        for (Map.Entry<Character, Integer> entry : stat.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        //빈도 수가 적은 것부터 압축
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            pq.add(new Node(left, right));
        }

        printCompression(pq.peek(), "");
    }

    private static void printCompression(Node node, String comp) {
        if (node == null) return;

        //리프 노드에 도달한 경우 출력
        if (node.left == null && node.right == null) {
            System.out.println(node.word + "-> " + comp);
        }

        //비트 압축
        printCompression(node.left, comp + "0");
        printCompression(node.right, comp + "1");
    }

    public static void main(String[] args) {
        Huffman o = new Huffman();
        o.HuffmanCoding("AAAAAABBBBCDD");
    }

}
