/* @test    @summary Test Collections.removeIf method for default behavior and null predicate handling    @run main CollectionsWitupTest */

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNonEmptyList();
        testRemoveIfWithEmptyList();
        testRemoveIfWithNullPredicate();
    }

    private static void testRemoveIfWithNonEmptyList() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        boolean result = Collections.removeIf(list, s -> s.equals("b"));
        assert result == false : "Expected removeIf to return false";
        assert list.size() == 3 : "List size should remain 3";
        assert list.contains("b") : "List should still contain 'b'";
    }

    private static void testRemoveIfWithEmptyList() {
        List<String> list = new ArrayList<>();
        boolean result = Collections.removeIf(list, s -> s.equals("b"));
        assert result == false : "Expected removeIf to return false on empty list";
        assert list.isEmpty() : "List should be empty";
    }

    private static void testRemoveIfWithNullPredicate() {
        List<String> list = new ArrayList<>();
        list.add("a");
        try {
            Collections.removeIf(list, null);
            assert false : "Expected NullPointerException when predicate is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
