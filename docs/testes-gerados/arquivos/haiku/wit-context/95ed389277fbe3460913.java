/*
 * @test
 * @summary Witup-generated checks for CharBufferSpliterator.forEachRemaining through the public CharBuffer.chars() API
 * @run main CharBufferSpliteratorWitupTest
 */

import java.nio.CharBuffer;
import java.util.Spliterator;
import java.util.function.IntConsumer;

public class CharBufferSpliteratorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testForEachRemainingVisitsRemainingChars() {
        CharBuffer buf = CharBuffer.wrap("abcdef", 1, 4);
        Spliterator.OfInt sp = buf.chars().spliterator();
        StringBuilder sb = new StringBuilder();
        sp.forEachRemaining((IntConsumer) c -> sb.append((char) c));
        check(sb.toString().equals("bcd"),
              "expected remaining chars 'bcd' but got '" + sb + "'");
    }

    static void testTryAdvanceReturnsFalseAfterForEachRemaining() {
        CharBuffer buf = CharBuffer.wrap("xyz");
        Spliterator.OfInt sp = buf.chars().spliterator();
        sp.forEachRemaining((IntConsumer) c -> { });
        boolean advanced = sp.tryAdvance((IntConsumer) c -> { });
        check(!advanced, "tryAdvance should return false after forEachRemaining");
    }

    static void testEmptyRangeDoesNotInvokeAction() {
        CharBuffer buf = CharBuffer.wrap("abc", 2, 2);
        Spliterator.OfInt sp = buf.chars().spliterator();
        int[] count = new int[1];
        sp.forEachRemaining((IntConsumer) c -> count[0]++);
        check(count[0] == 0, "action should not be invoked for an empty range");
    }

    static void testNullActionThrowsNPE() {
        CharBuffer buf = CharBuffer.wrap("abc");
        Spliterator.OfInt sp = buf.chars().spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) should throw NullPointerException");
    }

    public static void main(String[] args) {
        testForEachRemainingVisitsRemainingChars();
        testTryAdvanceReturnsFalseAfterForEachRemaining();
        testEmptyRangeDoesNotInvokeAction();
        testNullActionThrowsNPE();
    }
}
