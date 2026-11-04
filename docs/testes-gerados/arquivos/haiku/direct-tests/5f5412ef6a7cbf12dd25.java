/*
 * @test
 * @summary Verify that UCharacterIterator.getInstf(CharacterIterator) returns a non-null iterator wrapper for a given CharacterIterator
 * @modules java.base/sun.text.normalizer
 * @run main UCharacterIteratorWitupTest
 */

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import sun.text.normalizer.UCharacterIterator;

public class UCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testReturnsNonNull();
        testReturnsNewWrapperPerCall();
        testDoneConstant();
    }

    static void testReturnsNonNull() {
        CharacterIterator ci = new StringCharacterIterator("abc");
        UCharacterIterator uci = UCharacterIterator.getInstance(ci);
        check(uci != null, "getInstance(CharacterIterator) must not return null");
    }

    static void testReturnsNewWrapperPerCall() {
        CharacterIterator ci = new StringCharacterIterator("xyz");
        UCharacterIterator first = UCharacterIterator.getInstance(ci);
        UCharacterIterator second = UCharacterIterator.getInstance(ci);
        check(first != null && second != null, "both calls must return non-null");
        check(first != second, "each call must create a new UCharacterIterator wrapper");
    }

    static void testDoneConstant() {
        check(UCharacterIterator.DONE == -1, "DONE must be -1");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
