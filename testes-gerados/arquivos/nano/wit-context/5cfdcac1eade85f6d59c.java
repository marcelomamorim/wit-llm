/* @test    @summary Testa o método hashCode() de ArrayList para comportamento observável.    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testHashCodeWithNormalList();
        testHashCodeWithConcurrentModification();
    }

    private static void testHashCodeWithNormalList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        int hash = list.hashCode();
        System.out.println("Hash code with normal list: " + hash);
    }

    private static void testHashCodeWithConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            for (String s : list) {
                list.remove(0);
            }
            int hash = list.hashCode();
            System.out.println("Hash code after modification: " + hash);
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Caught expected ConcurrentModificationException");
        }
    }
}
