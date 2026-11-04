/*
 * @test
 * @summary Test Collections.UnmodifiableList.Anonymous forEachRemaining method behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    static void testForEachRemainingNullAction() {
        List<String> list = new ArrayList<>();
        list.add("a");
        List<String> unmodList = Collections.unmodifiableList(list);
        Iterator<String> it = unmodList.iterator();

        boolean npeThrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when passing null action to forEachRemaining");
        }
    }

    static void testForEachRemainingNormal() {
        List<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        List<String> unmodList = Collections.unmodifiableList(list);
        Iterator<String> it = unmodList.iterator();

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        it.forEachRemaining(action);

        String result = sb.toString();
        if (!"xy".equals(result)) {
            throw new RuntimeException("forEachRemaining did not process all elements correctly, got: " + result);
        }

        // After forEachRemaining, iterator should be exhausted
        if (it.hasNext()) {
            throw new RuntimeException("Iterator should be exhausted after forEachRemaining");
        }
    }
}
