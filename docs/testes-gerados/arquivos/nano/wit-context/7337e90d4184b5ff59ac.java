/* @test    @summary Test for UnicodeSet.retainAll method to verify exception handling and behavior.    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testRetainAllWithFrozenSet();
        testRetainAllWithNull();
        testRetainAllWithDifferentSets();
    }

    private static void testRetainAllWithFrozenSet() {
        UnicodeSet set1 = new UnicodeSet("[a-z]");
        UnicodeSet set2 = new UnicodeSet("[a-z]");
        set1.freeze();
        try {
            set1.retainAll(set2);
            System.out.println("FAIL: Expected UnsupportedOperationException when retainAll on frozen set.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException on frozen set.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testRetainAllWithNull() {
        UnicodeSet set1 = new UnicodeSet("[a-z]");
        try {
            set1.retainAll(null);
            System.out.println("FAIL: Expected NullPointerException when retainAll with null.");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException with null argument.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testRetainAllWithDifferentSets() {
        UnicodeSet set1 = new UnicodeSet("[a-c]");
        UnicodeSet set2 = new UnicodeSet("[d-f]");
        try {
            UnicodeSet result = set1.retainAll(set2);
            if (result == set1) {
                System.out.println("PASS: retainAll returned the same instance.");
            } else {
                System.out.println("FAIL: retainAll did not return the same instance.");
            }
            // After retainAll, set1 should be empty because no common elements
            if (set1.isEmpty()) {
                System.out.println("PASS: set1 is empty after retainAll with disjoint sets.");
            } else {
                System.out.println("FAIL: set1 is not empty after retainAll with disjoint sets.");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception during retainAll with different sets: " + e);
        }
    }
}
