/*
 * @test
 * @summary Verifies tryAdvance of the wrapping spliterator returned by Stream.spliterator() on intermediate pipelines
 * @run main StreamSpliteratorsWitupTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testAdvanceConsumesOneElementAtATime();
        testReturnsFalseWhenExhausted();
        System.out.println("PASSED");
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator<Integer> sp = Stream.of(1, 2, 3).filter(x -> true).spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) must throw NullPointerException");
    }

    static void testAdvanceConsumesOneElementAtATime() {
        Spliterator<Integer> sp = Stream.of(1, 2, 3).map(x -> x * 2).spliterator();
        List<Integer> got = new ArrayList<>();
        check(sp.tryAdvance(got::add), "first tryAdvance must return true");
        check(got.equals(Arrays.asList(2)), "after first advance expected [2] but was " + got);
        check(sp.tryAdvance(got::add), "second tryAdvance must return true");
        check(got.equals(Arrays.asList(2, 4)), "after second advance expected [2, 4] but was " + got);
    }

    static void testReturnsFalseWhenExhausted() {
        Spliterator<Integer> sp = Stream.of(1, 2, 3).map(x -> x * 2).spliterator();
        List<Integer> got = new ArrayList<>();
        while (sp.tryAdvance(got::add)) {
            // drain
        }
        check(got.equals(Arrays.asList(2, 4, 6)), "drained elements expected [2, 4, 6] but was " + got);
        boolean result = sp.tryAdvance(x -> {
            throw new AssertionError("consumer must not be invoked when exhausted");
        });
        check(!result, "tryAdvance on exhausted spliterator must return false");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
