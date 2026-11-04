/*
 * @test
 * @summary Test Collections.forEach method for null and valid Consumer
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachWithValidConsumer();
    }

    static void testForEachWithNullConsumer() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        try {
            Collections.forEach(list, null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testForEachWithValidConsumer() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i);
        Collections.forEach(list, consumer);
        if (!"123".equals(sb.toString())) {
            System.err.println("Consumer did not process list correctly: " + sb.toString());
            System.exit(1);
        }
    }
}
