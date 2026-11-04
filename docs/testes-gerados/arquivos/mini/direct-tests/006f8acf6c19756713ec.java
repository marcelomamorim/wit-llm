/*
 * @test
 * @summary Test forEachRemaining method delegation in Collections
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingDelegation();
    }

    private static void testForEachRemainingDelegation() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Iterator<String> it = list.iterator();

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        // Use forEachRemaining to consume all remaining elements
        it.forEachRemaining(consumer);

        // After forEachRemaining, iterator should be exhausted
        if (it.hasNext()) {
            throw new RuntimeException("Iterator should be exhausted after forEachRemaining");
        }

        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new RuntimeException("forEachRemaining did not process all elements correctly: " + result);
        }
    }
}
