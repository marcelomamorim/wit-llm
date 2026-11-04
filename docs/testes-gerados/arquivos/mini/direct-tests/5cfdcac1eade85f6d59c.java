/*
 * @test
 * @summary Test java.util.ArrayList.hashCode() method
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testEmptyListHashCode();
        testNonEmptyListHashCode();
        testHashCodeConsistency();
    }

    private static void testEmptyListHashCode() {
        ArrayList<Object> list = new ArrayList<>();
        int hash = list.hashCode();
        // The hashCode of empty list should be consistent and deterministic
        if (hash != 1) {
            throw new RuntimeException("Empty list hashCode expected to be 1 but was " + hash);
        }
    }

    private static void testNonEmptyListHashCode() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        int expectedHash = Arrays.asList("a", "b", "c").hashCode();
        int actualHash = list.hashCode();
        if (actualHash != expectedHash) {
            throw new RuntimeException("Non-empty list hashCode mismatch: expected " + expectedHash + " but was " + actualHash);
        }
    }

    private static void testHashCodeConsistency() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int hash1 = list.hashCode();
        int hash2 = list.hashCode();
        if (hash1 != hash2) {
            throw new RuntimeException("hashCode is not consistent on repeated calls");
        }
    }
}
