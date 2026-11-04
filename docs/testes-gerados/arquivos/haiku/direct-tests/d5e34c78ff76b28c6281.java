/*
 * @test
 * @summary Verifies StringLatin1 lines spliterator tryAdvance behavior: null action rejection, sequential element delivery, exhaustion, and empty input
 * @run main StringLatin1WitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testSequentialAdvanceAndExhaustion();
        testEmptyStringReturnsFalse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNpe() {
        Spliterator<String> sp = "a\nb\nc".lines().spliterator();
        try {
            sp.tryAdvance(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            check("tryAdvance action missing".equals(e.getMessage()),
                  "unexpected NPE message: " + e.getMessage());
        }
        List<String> out = new ArrayList<>();
        check(sp.tryAdvance(out::add), "first element should be available after rejected null action");
        check(out.size() == 1 && "a".equals(out.get(0)),
              "expected first line 'a' but got " + out);
    }

    static void testSequentialAdvanceAndExhaustion() {
        Spliterator<String> sp = "a\nb\nc".lines().spliterator();
        List<String> out = new ArrayList<>();
        check(sp.tryAdvance(out::add), "advance 1 should return true");
        check(sp.tryAdvance(out::add), "advance 2 should return true");
        check(sp.tryAdvance(out::add), "advance 3 should return true");
        check(!sp.tryAdvance(out::add), "advance after end should return false");
        check(!sp.tryAdvance(out::add), "repeated advance after end should return false");
        check(out.size() == 3, "expected 3 elements but got " + out.size());
        check("a".equals(out.get(0)) && "b".equals(out.get(1)) && "c".equals(out.get(2)),
              "unexpected elements: " + out);
    }

    static void testEmptyStringReturnsFalse() {
        Spliterator<String> sp = "".lines().spliterator();
        List<String> out = new ArrayList<>();
        check(!sp.tryAdvance(out::add), "empty input should return false");
        check(out.isEmpty(), "action must not be invoked for empty input");
    }
}
