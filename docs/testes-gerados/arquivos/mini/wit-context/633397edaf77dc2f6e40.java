/*
 * @test
 * @summary Test Collections$UnmodifiableCollection$Anonymous forEachRemaining method behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Consumer;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
        System.out.println("All tests passed.");
    }

    private static void testForEachRemainingNullAction() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Iterable<String> unmodifiable = Collections.unmodifiableCollection(list);
        Iterator<String> it = unmodifiable.iterator();

        try {
            it.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testForEachRemainingNormal() {
        List<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        Iterable<String> unmodifiable = Collections.unmodifiableCollection(list);
        Iterator<String> it = unmodifiable.iterator();

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        it.forEachRemaining(consumer);

        if (!"xy".equals(sb.toString())) {
            throw new RuntimeException("forEachRemaining did not process all elements correctly");
        }
    }
}
