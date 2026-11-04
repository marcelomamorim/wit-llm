/*
 * @test
 * @summary Verifies Collections.checkedCollection forEach behavior: null action throws NPE and non-null action visits elements in order
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testForEachVisitsElementsInOrder();
        System.out.println("PASSED");
    }

    static void testNullActionThrowsNPE() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        Collection<String> checked = Collections.checkedCollection(backing, String.class);
        boolean thrown = false;
        try {
            checked.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action");
        }
    }

    static void testForEachVisitsElementsInOrder() {
        List<String> backing = new ArrayList<>();
        backing.add("x");
        backing.add("y");
        backing.add("z");
        Collection<String> checked = Collections.checkedCollection(backing, String.class);
        List<String> visited = new ArrayList<>();
        checked.forEach(visited::add);
        if (!visited.equals(backing)) {
            throw new RuntimeException("Unexpected visit order: " + visited);
        }
    }
}
