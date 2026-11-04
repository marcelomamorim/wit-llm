/*
 * @test
 * @summary Test CopyOnWriteArrayList.forEach with various Consumers
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.Objects;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        testForEachWithValidConsumer();
        testForEachWithNullConsumer();
        testForEachConsumerCalledCorrectly();
    }

    static void testForEachWithValidConsumer() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

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

    static void testForEachWithNullConsumer() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        try {
            list.forEach(null);
            throw new AssertionError("Expected NullPointerException when passing null Consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachConsumerCalledCorrectly() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        final int[] sum = new int[1];
        Consumer<Integer> consumer = new Consumer<Integer>() {
            public void accept(Integer i) {
                sum[0] += i;
            }
        };

        list.forEach(consumer);

        if (sum[0] != 6) {
            throw new AssertionError("Expected sum 6 but got " + sum[0]);
        }
    }
}
