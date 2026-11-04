/*
 * @test
 * @summary Checks observable membership behavior of the UnicodeSet(String pattern) constructor
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testSimpleCharacterList();
        testRange();
        testNegation();
        testDigitRange();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSimpleCharacterList() {
        UnicodeSet set = new UnicodeSet("[abc]");
        check(set.contains('a'), "'a' should be in [abc]");
        check(set.contains('b'), "'b' should be in [abc]");
        check(set.contains('c'), "'c' should be in [abc]");
        check(!set.contains('d'), "'d' should not be in [abc]");
    }

    static void testRange() {
        UnicodeSet set = new UnicodeSet("[a-c]");
        check(set.contains('a'), "'a' should be in [a-c]");
        check(set.contains('b'), "'b' should be in [a-c]");
        check(set.contains('c'), "'c' should be in [a-c]");
        check(!set.contains('d'), "'d' should not be in [a-c]");
        check(!set.contains('A'), "'A' should not be in [a-c]");
    }

    static void testNegation() {
        UnicodeSet set = new UnicodeSet("[^a]");
        check(!set.contains('a'), "'a' should not be in [^a]");
        check(set.contains('b'), "'b' should be in [^a]");
    }

    static void testDigitRange() {
        UnicodeSet set = new UnicodeSet("[0-9]");
        check(set.contains('0'), "'0' should be in [0-9]");
        check(set.contains('5'), "'5' should be in [0-9]");
        check(set.contains('9'), "'9' should be in [0-9]");
        check(!set.contains('x'), "'x' should not be in [0-9]");
    }
}
