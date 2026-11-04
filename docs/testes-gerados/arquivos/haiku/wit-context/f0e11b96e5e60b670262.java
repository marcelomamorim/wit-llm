/*
 * @test
 * @summary Verifies that forEachRemaining on empty spliterators rejects null consumers and accepts valid ones
 * @run main SpliteratorsWitupTest
 */
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {
    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    static void expectNpe(String name, Runnable r) {
        try {
            r.run();
            check(false, name + " should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } catch (RuntimeException e) {
            check(false, name + " threw unexpected " + e);
        }
    }

    static void testRefNull() {
        expectNpe("ref forEachRemaining(null)",
                () -> Spliterators.emptySpliterator().forEachRemaining(null));
    }

    static void testIntNull() {
        expectNpe("int forEachRemaining(null)",
                () -> Spliterators.emptyIntSpliterator().forEachRemaining((IntConsumer) null));
    }

    static void testLongNull() {
        expectNpe("long forEachRemaining(null)",
                () -> Spliterators.emptyLongSpliterator().forEachRemaining((LongConsumer) null));
    }

    static void testDoubleNull() {
        expectNpe("double forEachRemaining(null)",
                () -> Spliterators.emptyDoubleSpliterator().forEachRemaining((DoubleConsumer) null));
    }

    static void testRefNonNullNoElements() {
        Spliterator<String> s = Spliterators.emptySpliterator();
        final int[] calls = {0};
        s.forEachRemaining(x -> calls[0]++);
        check(calls[0] == 0, "empty spliterator must not invoke consumer");
        check(!s.tryAdvance(x -> { }), "empty spliterator tryAdvance must return false");
    }

    public static void main(String[] args) {
        testRefNull();
        testIntNull();
        testLongNull();
        testDoubleNull();
        testRefNonNullNoElements();
        if (failures > 0) {
            throw new RuntimeException(failures + " failure(s)");
        }
    }
}
