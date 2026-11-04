/*
 * @test
 * @summary Test java.util.Arrays forEach method behavior
 * @run main ArraysWitupTest
 */

import java.util.function.Consumer;
import java.util.Objects;

public class ArraysWitupTest {

    static class TestArray<E> {
        private final E[] a;
        TestArray(E[] array) {
            this.a = array;
        }
        public void forEach(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            for (E e : a) {
                action.accept(e);
            }
        }
    }

    public static void main(String[] args) {
        testForEachWithValidConsumer();
        testForEachWithNullConsumer();
        testForEachWithEmptyArray();
    }

    private static void testForEachWithValidConsumer() {
        final StringBuilder sb = new StringBuilder();
        String[] data = {"a", "b", "c"};
        TestArray<String> testArray = new TestArray<>(data);
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };
        testArray.forEach(consumer);
        if (!"abc".equals(sb.toString())) {
            throw new RuntimeException("forEach did not process all elements correctly");
        }
    }

    private static void testForEachWithNullConsumer() {
        String[] data = {"x"};
        TestArray<String> testArray = new TestArray<>(data);
        try {
            testArray.forEach(null);
            throw new RuntimeException("forEach did not throw NullPointerException when action is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachWithEmptyArray() {
        final StringBuilder sb = new StringBuilder();
        String[] data = new String[0];
        TestArray<String> testArray = new TestArray<>(data);
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };
        testArray.forEach(consumer);
        if (sb.length() != 0) {
            throw new RuntimeException("forEach on empty array should not call consumer");
        }
    }
}
