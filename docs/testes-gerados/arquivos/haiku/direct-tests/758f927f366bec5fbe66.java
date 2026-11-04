/*
 * @test
 * @summary Checks public stream traversal and toArray over Collection sources, which exercise Nodes collection nodes indirectly
 * @run main NodesWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class NodesWitupTest {

    public static void main(String[] args) {
        testForEachPreservesOrder();
        testToArrayPreservesOrder();
        testEmptyCollection();
    }

    static void testForEachPreservesOrder() {
        List<String> src = new ArrayList<>();
        src.add("a");
        src.add("b");
        src.add("c");
        List<String> seen = new ArrayList<>();
        src.stream().forEach(seen::add);
        check(seen.equals(src), "forEach order mismatch: " + seen);
    }

    static void testToArrayPreservesOrder() {
        List<Integer> src = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            src.add(i);
        }
        Object[] arr = src.stream().toArray();
        check(arr.length == 10, "toArray length: " + arr.length);
        for (int i = 0; i < arr.length; i++) {
            check(arr[i].equals(i), "toArray element " + i + ": " + arr[i]);
        }
    }

    static void testEmptyCollection() {
        List<String> src = new ArrayList<>();
        List<String> seen = new ArrayList<>();
        src.stream().forEach(seen::add);
        check(seen.isEmpty(), "forEach on empty source produced elements");
        Object[] arr = src.stream().toArray();
        check(arr.length == 0, "toArray on empty source length: " + arr.length);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
