/*
 * @test
 * @summary Test for CopyOnWriteArrayList.forEachRemaining with null Consumer
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
    }

    public static void testForEachRemainingWithNullConsumer() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        CopyOnWriteArrayList.COWIterator<String> iterator = list.new COWIterator<>(list);
        try {
            iterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }
}
