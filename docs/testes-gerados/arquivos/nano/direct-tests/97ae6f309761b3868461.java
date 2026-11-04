/* @test    @summary Test Collections.forEach method for null and valid Consumer    @run main CollectionsWitupTest */

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
            System.err.println("Failed: Expected NullPointerException for null consumer");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testForEachWithValidConsumer() {
        List<String> list = Arrays.asList("a", "b", "c");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        Collections.forEach(list, consumer);
        if (!"abc".equals(sb.toString())) {
            System.err.println("Failed: Unexpected result in forEach with valid consumer: " + sb.toString());
        }
    }
}
