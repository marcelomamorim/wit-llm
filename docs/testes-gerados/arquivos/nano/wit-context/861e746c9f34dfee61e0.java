/* @test    @summary Test for UnicodeSet.clear() method to verify behavior when set is frozen.    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testClearOnFrozenSet();
        testClearOnUnfrozenSet();
    }

    private static void testClearOnFrozenSet() {
        UnicodeSet set = new UnicodeSet();
        // Prepare set and freeze it to simulate frozen state
        set.applyPattern("[a-z]");
        set.freeze();
        try {
            set.clear();
            // If no exception, test passes for this scenario
            System.out.println("testClearOnFrozenSet: PASSED");
        } catch (UnsupportedOperationException e) {
            System.out.println("testClearOnFrozenSet: FAILED - Unexpected exception: " + e);
        } catch (Exception e) {
            System.out.println("testClearOnFrozenSet: FAILED - Unexpected exception: " + e);
        }
    }

    private static void testClearOnUnfrozenSet() {
        UnicodeSet set = new UnicodeSet();
        // Prepare set in unfrozen state
        set.applyPattern("[0-9]");
        try {
            UnicodeSet result = set.clear();
            if (result == set) {
                System.out.println("testClearOnUnfrozenSet: PASSED");
            } else {
                System.out.println("testClearOnUnfrozenSet: FAILED - Return value mismatch");
            }
        } catch (Exception e) {
            System.out.println("testClearOnUnfrozenSet: FAILED - Unexpected exception: " + e);
        }
    }
}
