/*
 * @test
 * @summary Test Collections.retainAll behavior via batchRemove usage
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRetainAllWithEmptyCollection();
        testRetainAllWithNoCommonElements();
        testRetainAllWithSomeCommonElements();
        testRetainAllWithAllCommonElements();
        testRetainAllWithSelf();
    }

    private static void testRetainAllWithEmptyCollection() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        boolean changed = list.retainAll(Collections.emptyList());
        if (!changed) throw new RuntimeException("Expected change when retaining with empty collection");
        if (!list.isEmpty()) throw new RuntimeException("List should be empty after retainAll with empty collection");
    }

    private static void testRetainAllWithNoCommonElements() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> retain = Arrays.asList("x", "y");
        boolean changed = list.retainAll(retain);
        if (!changed) throw new RuntimeException("Expected change when no common elements");
        if (!list.isEmpty()) throw new RuntimeException("List should be empty after retainAll with no common elements");
    }

    private static void testRetainAllWithSomeCommonElements() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> retain = Arrays.asList("b", "c", "x");
        boolean changed = list.retainAll(retain);
        if (!changed) throw new RuntimeException("Expected change when some elements retained");
        if (list.size() != 2) throw new RuntimeException("List size should be 2 after retainAll");
        if (!list.contains("b") || !list.contains("c")) throw new RuntimeException("List should contain 'b' and 'c'");
    }

    private static void testRetainAllWithAllCommonElements() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> retain = Arrays.asList("a", "b", "c");
        boolean changed = list.retainAll(retain);
        if (changed) throw new RuntimeException("Expected no change when all elements retained");
        if (list.size() != 3) throw new RuntimeException("List size should remain 3 after retainAll");
    }

    private static void testRetainAllWithSelf() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        boolean changed = list.retainAll(list);
        if (changed) throw new RuntimeException("Expected no change when retaining with self");
        if (list.size() != 3) throw new RuntimeException("List size should remain 3 after retainAll with self");
    }
}
