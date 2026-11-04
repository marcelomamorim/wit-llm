/* @test    @summary Test hashCode() method of ArrayList for correctness and consistency    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testEmptyArrayListHashCode();
        testArrayListWithElementsHashCode();
        testArrayListAfterModificationHashCode();
    }

    private static void testEmptyArrayListHashCode() {
        ArrayList<Integer> list = new ArrayList<>();
        int hash1 = list.hashCode();
        int hash2 = list.hashCode();
        if (hash1 != hash2) {
            throw new AssertionError("Hash codes differ for empty list: " + hash1 + " != " + hash2);
        }
    }

    private static void testArrayListWithElementsHashCode() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        int hash1 = list.hashCode();
        int hash2 = list.hashCode();
        if (hash1 != hash2) {
            throw new AssertionError("Hash codes differ for same list state: " + hash1 + " != " + hash2);
        }
    }

    private static void testArrayListAfterModificationHashCode() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        int hashBefore = list.hashCode();
        list.add(2);
        int hashAfter = list.hashCode();
        if (hashBefore == hashAfter) {
            throw new AssertionError("Hash code did not change after modification: " + hashAfter);
        }
    }
}
