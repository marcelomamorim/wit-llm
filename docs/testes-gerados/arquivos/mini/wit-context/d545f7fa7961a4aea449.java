/*
 * @test
 * @summary Test CopyOnWriteArrayList.forEach method behavior including null action
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.Objects;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        testForEachWithValidConsumer();
        testForEachWithNullConsumer();
    }

    private static void testForEachWithValidConsumer() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add(null);

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s == null ? "null" : s);
            }
        };

        list.forEach(consumer);

        String result = sb.toString();
        if (!"abnull".equals(result)) {
            throw new AssertionError("forEach did not process elements correctly, got: " + result);
        }
    }

    private static void testForEachWithNullConsumer() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("x");

        try {
            list.forEach(null);
            throw new AssertionError("Expected NullPointerException when passing null consumer");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
