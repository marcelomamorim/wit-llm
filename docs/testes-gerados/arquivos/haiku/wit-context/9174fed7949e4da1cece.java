/*
 * @test
 * @summary Witup-derived checks for IntStream.iterate spliterator tryAdvance
 * @run main IntStreamWitupTest
 */
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class IntStreamWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testSequenceAndNoStateCorruptionAfterNullAction();
    }

    static void testNullActionThrowsNullPointerException() {
        Spliterator.OfInt sp = IntStream.iterate(0, x -> x + 1).spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testSequenceAndNoStateCorruptionAfterNullAction() {
        Spliterator.OfInt sp = IntStream.iterate(0, x -> x + 1).spliterator();
        int[] got = {-1};
        IntConsumer c = v -> got[0] = v;

        check(sp.tryAdvance(c), "first tryAdvance should return true");
        check(got[0] == 0, "first element should be seed 0, was " + got[0]);

        boolean thrown = false;
        try {
            sp.tryAdvance((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action after start");

        check(sp.tryAdvance(c), "tryAdvance after null failure should return true");
        check(got[0] == 1, "element after failed call should be 1, was " + got[0]);

        check(sp.tryAdvance(c), "second valid tryAdvance should return true");
        check(got[0] == 2, "next element should be 2, was " + got[0]);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
