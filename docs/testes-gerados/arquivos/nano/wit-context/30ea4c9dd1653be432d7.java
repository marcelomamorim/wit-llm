/*
 * @test
 * @summary Test for Collections.removeIf with null predicate
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithValidPredicate();
    }

    static void testRemoveIfWithNullPredicate() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        try {
            // Expect NullPointerException when predicate is null
            Collections.removeIf(list, null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        }
    }

    static void testRemoveIfWithValidPredicate() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        boolean result = Collections.removeIf(list, s -> s.equals("b"));
        if (result && list.size() == 2 && !list.contains("b")) {
            System.out.println("PASS: removeIf with predicate removed 'b'");
        } else {
            System.out.println("FAIL: removeIf with predicate did not behave as expected");
        }
    }
}
