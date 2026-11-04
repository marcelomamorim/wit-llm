/* @test    @summary Test for ArrayList.hashCode() method to verify behavior and exception handling    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testHashCodeNormal();
        testHashCodeWithConcurrentModification();
    }

    private static void testHashCodeNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        int result = list.hashCode();
        System.out.println("hashCode with normal list: " + result);
    }

    private static void testHashCodeWithConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            for (int i = 0; i < list.size(); i++) {
                list.remove(0);
                list.hashCode();
            }
            System.out.println("No exception thrown during concurrent modification.");
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Caught expected ConcurrentModificationException.");
        } catch (Exception e) {
            System.out.println("Caught unexpected exception: " + e);
        }
    }
}
