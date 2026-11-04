/* @test    @summary Test UnicodeSet.add(int c) method for basic functionality and immutability.    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testAddBasic();
        testAddAfterFreeze();
    }

    private static void testAddBasic() {
        UnicodeSet set = new UnicodeSet();
        int codePoint = 65; // 'A'
        UnicodeSet result = set.add(codePoint);
        if (result != set) {
            throw new AssertionError("add() should return the same instance for mutable set.");
        }
        if (!set.contains(codePoint)) {
            throw new AssertionError("Set should contain the added code point.");
        }
    }

    private static void testAddAfterFreeze() {
        UnicodeSet set = new UnicodeSet();
        set.freeze();
        int codePoint = 66; // 'B'
        try {
            set.add(codePoint);
            throw new AssertionError("add() should throw UnsupportedOperationException after freeze.");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
