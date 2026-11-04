/*
 * @test
 * @summary Test CopyOnWriteArrayList.forEach with valid and null Consumer
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
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});
        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };
        list.forEach(consumer);
        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new AssertionError("Expected concatenated string 'abc' but got '" + result + "'");
        }
    }

    private static void testForEachWithNullConsumer() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        try {
            list.forEach(null);
            throw new AssertionError("Expected NullPointerException when passing null Consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
