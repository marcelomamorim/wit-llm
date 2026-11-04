/*
 * @test
 * @summary Verifies StringUTF16.CharsSpliterator.tryAdvance through the public String.chars() spliterator
 * @run main StringUTF16WitupTest
 */

import java.util.Spliterator;
import java.util.function.IntConsumer;

public class StringUTF16WitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        testUtf16BmpChars();
        testSurrogatePairChars();
        testNullActionThrowsNpe();
    }

    static void testUtf16BmpChars() {
        String s = "\u4e2d\u6587";
        Spliterator.OfInt sp = s.chars().spliterator();
        int[] buf = new int[4];
        int[] n = {0};
        IntConsumer c = v -> buf[n[0]++] = v;

        check(sp.tryAdvance(c), "first tryAdvance should be true");
        check(buf[0] == 0x4e2d, "first char mismatch");
        check(sp.tryAdvance(c), "second tryAdvance should be true");
        check(buf[1] == 0x6587, "second char mismatch");
        check(!sp.tryAdvance(c), "tryAdvance at end should be false");
        check(!sp.tryAdvance(c), "repeated tryAdvance at end should be false");
        check(n[0] == 2, "action should be called exactly twice");
    }

    static void testSurrogatePairChars() {
        String s = "\uD83D\uDE00";
        Spliterator.OfInt sp = s.chars().spliterator();
        int[] buf = new int[3];
        int[] n = {0};
        IntConsumer c = v -> buf[n[0]++] = v;

        check(sp.tryAdvance(c), "surrogate high tryAdvance should be true");
        check(buf[0] == 0xD83D, "high surrogate mismatch");
        check(sp.tryAdvance(c), "surrogate low tryAdvance should be true");
        check(buf[1] == 0xDE00, "low surrogate mismatch");
        check(!sp.tryAdvance(c), "tryAdvance after surrogate pair should be false");
        check(n[0] == 2, "surrogate pair should yield two code units");
    }

    static void testNullActionThrowsNpe() {
        String s = "\u4e2d\u6587";
        Spliterator.OfInt sp = s.chars().spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action should throw NullPointerException");
    }
}
