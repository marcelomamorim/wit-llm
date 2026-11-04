/*
 * @test
 * @summary Test Stream.iterate(T seed, UnaryOperator<T> f) basic behavior
 * @run main StreamWitupTest
 */

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class StreamWitupTest {
    public static void main(String[] args) {
        testIterateSimpleSequence();
        testIterateWithNullFunction();
        testIterateWithFunctionReturningNull();
    }

    private static void testIterateSimpleSequence() {
        Stream<Integer> stream = Stream.iterate(0, new UnaryOperator<Integer>() {
            public Integer apply(Integer i) {
                return i + 1;
            }
        });

        final int[] collected = new int[5];
        final AtomicInteger index = new AtomicInteger();

        stream.limit(5).forEach(new Consumer<Integer>() {
            public void accept(Integer i) {
                collected[index.getAndIncrement()] = i;
            }
        });

        for (int i = 0; i < 5; i++) {
            if (collected[i] != i) {
                throw new AssertionError("Expected " + i + " but got " + collected[i]);
            }
        }
    }

    private static void testIterateWithNullFunction() {
        try {
            Stream.iterate("seed", null);
            throw new AssertionError("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testIterateWithFunctionReturningNull() {
        Stream<String> stream = Stream.iterate("start", new UnaryOperator<String>() {
            public String apply(String s) {
                return null;
            }
        });

        try {
            stream.limit(2).forEach(new Consumer<String>() {
                public void accept(String s) {
                    if (s == null) {
                        throw new NullPointerException("Stream element is null");
                    }
                }
            });
            throw new AssertionError("Expected NullPointerException due to null element");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
