/*
 * @test
 * @summary Test for Collections.retainAll to observe behavior and exception handling.
 * @run main CollectionsWitupTest
 */

import java.util.*;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRetainAllWithUnmodifiableCollection();
    }

    private static void testRetainAllWithUnmodifiableCollection() {
        List<String> modifiableList = new ArrayList<>();
        modifiableList.add("a");
        modifiableList.add("b");
        List<String> unmodifiableList = Collections.unmodifiableList(modifiableList);
        try {
            boolean result = unmodifiableList.retainAll(Arrays.asList("a"));
            System.out.println("retainAll on unmodifiable list result: " + result);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException");
        }
    }
}
