/* @test    @summary Test for UnicodeSet.add(CharSequence s) method to verify exception paths and behavior.    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testAddWithFrozenSet();
        testAddWithInvalidCodePoint();
    }

    private static void testAddWithFrozenSet() {
        UnicodeSet set = new UnicodeSet();
        set.freeze();
        try {
            set.add("test");
            // Expecting UnsupportedOperationException due to frozen set
            System.out.println("FAIL: Expected UnsupportedOperationException not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testAddWithInvalidCodePoint() {
        UnicodeSet set = new UnicodeSet();
        try {
            // Creating a CharSequence with an invalid code point (e.g., negative)
            CharSequence invalidSeq = new String(new int[] { -1 }, 0, 1);
            set.add(invalidSeq);
            System.out.println("FAIL: Expected IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }
}
