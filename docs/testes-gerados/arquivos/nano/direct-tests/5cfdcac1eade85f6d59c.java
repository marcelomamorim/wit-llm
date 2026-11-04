/* @test    @summary Test hashCode() method of ArrayList for correctness and consistency    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testEmptyArrayListHashCode();
        testArrayListWithElementsHashCode();
        testArrayListWithNullElementsHashCode();
        testArrayListHashCodeConsistency();
    }

    private static void testEmptyArrayListHashCode() {
        ArrayList<String> list = new ArrayList<>();
        int hash1 = list.hashCode();
        int hash2 = list.hashCode();
        if (hash1 != hash2) {
            throw new AssertionError("Hash code of empty list should be consistent") ;
        }
    }

    private static void testArrayListWithElementsHashCode() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int hash1 = list.hashCode();
        int hash2 = list.hashCode();
        if (hash1 != hash2) {
            throw new AssertionError("Hash code should be consistent for same list state") ;
        }
    }

    private static void testArrayListWithNullElementsHashCode() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        list.add("test");
        list.add(null);
        int hash1 = list.hashCode();
        int hash2 = list.hashCode();
        if (hash1 != hash2) {
            throw new AssertionError("Hash code should be consistent even with null elements") ;
        }
    }

    private static void testArrayListHashCodeConsistency() {
        ArrayList<Double> list = new ArrayList<>();
        list.add(1.1);
        list.add(2.2);
        list.add(3.3);
        int hash1 = list.hashCode();
        int hash2 = list.hashCode();
        if (hash1 != hash2) {
            throw new AssertionError("Hash code must be consistent across multiple calls") ;
        }
    }
}
