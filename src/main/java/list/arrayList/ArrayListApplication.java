package list.arrayList;

import java.util.Arrays;

public class ArrayListApplication {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws CloneNotSupportedException {

        ArrayList<Integer> original = new ArrayList<>();
        original.add(10);

        ArrayList<Integer> copy = original;
        ArrayList<Integer> clone = (ArrayList<Integer>) original.clone();

        copy.add(20);
        clone.add(30);

        System.out.println("original list");
        for (int i = 0; i < original.size(); i++) {
            System.out.println("index " + i + " data = " + original.get(i));
        }

        System.out.println("\ncopy list");
        for (int i = 0; i < original.size(); i++) {
            System.out.println("index " + i + " data = " + copy.get(i));
        }

        System.out.println("\nclone list");
        for (int i = 0; i < original.size(); i++) {
            System.out.println("index " + i + " data = " + clone.get(i));
        }

        System.out.println("\noriginal list reference : " + original);
        System.out.println("copy list reference : " + copy);
        System.out.println("clone list reference : " + clone);

        ArrayList<String> bucket = new ArrayList<>();
        bucket.add("apple");
        bucket.add("banana");
        bucket.add("orange");
        bucket.add(1, "strawberry");

        System.out.println("\nArray : " + Arrays.toString(bucket.toArray()));
        System.out.println("\nstrawberry 인덱스 = " + bucket.indexOf("strawberry"));
        System.out.println("bucket.get(2) = " + bucket.get(2));
        System.out.println("bucket.size() = " + bucket.size());

        System.out.println("---remove index 1---");
        System.out.println("removed value : " + bucket.remove(1));

        System.out.println("\nArray : " + Arrays.toString(bucket.toArray()));

        bucket.set(1, "grape");
        System.out.println("\nArray : " + Arrays.toString(bucket.toArray()));
        System.out.println("banana is existing? " + bucket.contains("banana"));

        bucket.remove("apple");
        System.out.println("\nArray : " + Arrays.toString(bucket.toArray()));

        bucket.clear();
        System.out.println("\nArray : " + Arrays.toString(bucket.toArray()));

    }

}
