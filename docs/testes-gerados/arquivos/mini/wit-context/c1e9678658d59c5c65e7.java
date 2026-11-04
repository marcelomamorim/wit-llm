/*
 * @test
 * @summary Test java.util.Collections.CheckedCollection.forEachRemaining behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    static void testForEachRemainingNullAction() {
        // Create a CheckedCollection wrapping a List
        List<String> list = new ArrayList<>();
        list.add("a");
        Collection<String> checked = Collections.checkedCollection(list, String.class);

        Iterator<String> it = checked.iterator();

        // The CheckedCollection iterator's forEachRemaining should throw NullPointerException if action is null
        try {
            it.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingNormal() {
        List<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        Collection<String> checked = Collections.checkedCollection(list, String.class);

        Iterator<String> it = checked.iterator();

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        it.forEachRemaining(action);

        if (!"xy".equals(sb.toString())) {
            throw new RuntimeException("forEachRemaining did not process all elements correctly");
        }

        // After forEachRemaining, iterator should have no more elements
        if (it.hasNext()) {
            throw new RuntimeException("Iterator should have no more elements after forEachRemaining");
        }
    }
}
