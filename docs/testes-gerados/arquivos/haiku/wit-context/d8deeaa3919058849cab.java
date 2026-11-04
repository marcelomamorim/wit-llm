/*
 * @test
 * @summary Verifies that Collections.newSetFromMap views delegate forEach to the backing key set and reject a null action
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachVisitsAllElementsInOrder();
        testForEachOnEmptySetDoesNotInvokeAction();
        testForEachNullActionThrowsNullPointerException();
    }

    private static void testForEachVisitsAllElementsInOrder() {
        Map<String, Boolean> map = new TreeMap<String, Boolean>();
        Set<String> set = Collections.newSetFromMap(map);
        set.add("b");
        set.add("a");
        set.add("c");

        final List<String> seen = new ArrayList<String>();
        set.forEach(s -> seen.add(s));

        if (seen.size() != 3) {
            throw new RuntimeException("Expected 3 elements, got " + seen.size());
        }
        if (!"a".equals(seen.get(0)) || !"b".equals(seen.get(1)) || !"c".equals(seen.get(2))) {
            throw new RuntimeException("Unexpected iteration result: " + seen);
        }
    }

    private static void testForEachOnEmptySetDoesNotInvokeAction() {
        Map<String, Boolean> map = new TreeMap<String, Boolean>();
        Set<String> set = Collections.newSetFromMap(map);

        final int[] calls = new int[1];
        set.forEach(s -> calls[0]++);

        if (calls[0] != 0) {
            throw new RuntimeException("Action invoked " + calls[0] + " times on empty set");
        }
    }

    private static void testForEachNullActionThrowsNullPointerException() {
        Map<String, Boolean> map = new TreeMap<String, Boolean>();
        Set<String> set = Collections.newSetFromMap(map);
        set.add("x");

        boolean thrown = false;
        try {
            set.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action");
        }
    }
}
