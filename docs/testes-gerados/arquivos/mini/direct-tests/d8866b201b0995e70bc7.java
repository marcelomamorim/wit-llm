/*
 * @test
 * @summary Test java.util.Collections forEach method delegation
 * @run main CollectionsWitupTest
 */

import java.util.*;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachDelegation();
        testForEachWithNullAction();
    }

    static void testForEachDelegation() {
        final StringBuilder sb = new StringBuilder();
        Collection<String> coll = new ArrayList<>();
        coll.add("a");
        coll.add("b");
        coll.add("c");

        // Wrap collection with Collections.checkedCollection to get a Collections instance with forEach
        Collection<String> checked = Collections.checkedCollection(coll, String.class);

        // Use forEach to append elements to sb
        checked.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });

        String result = sb.toString();
        if (!result.contains("a") || !result.contains("b") || !result.contains("c")) {
            throw new RuntimeException("forEach did not iterate all elements: " + result);
        }
    }

    static void testForEachWithNullAction() {
        Collection<String> coll = new ArrayList<>();
        coll.add("x");
        Collection<String> checked = Collections.checkedCollection(coll, String.class);

        try {
            checked.forEach(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
