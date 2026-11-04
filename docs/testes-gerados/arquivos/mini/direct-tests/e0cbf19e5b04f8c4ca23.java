/*
 * @test
 * @summary Test UnicodeSet.set(UnicodeSet) method for correct copying and mutation
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;
import java.util.TreeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        // Create a UnicodeSet with a range
        UnicodeSet set1 = new UnicodeSet(0x41, 0x5A); // A-Z

        // Create another UnicodeSet with a different range
        UnicodeSet set2 = new UnicodeSet(0x61, 0x7A); // a-z

        // Add a string to set2's strings set to test strings copying
        set2.strings.add("test");

        // Copy set2 into set1 using set()
        set1.set(set2);

        // Verify that set1 now contains the same ranges as set2
        if (!set1.list.equals(set2.list)) {
            throw new AssertionError("list field was not copied correctly");
        }

        // Verify that len field is copied correctly
        if (set1.len != set2.len) {
            throw new AssertionError("len field was not copied correctly");
        }

        // Verify that strings set is copied and is a different instance
        if (set1.strings == set2.strings) {
            throw new AssertionError("strings field was not cloned properly (same instance)");
        }

        if (!set1.strings.equals(set2.strings)) {
            throw new AssertionError("strings field content was not copied correctly");
        }

        // Modify set2 strings and verify set1 strings do not change
        set2.strings.add("another");
        if (set1.strings.contains("another")) {
            throw new AssertionError("strings set in set1 changed after modifying set2");
        }

        // Test that set() returns this
        UnicodeSet returned = set1.set(set2);
        if (returned != set1) {
            throw new AssertionError("set() did not return this");
        }

        // Test that calling set() on a frozen set throws UnsupportedOperationException
        UnicodeSet frozenSet = new UnicodeSet(0x30, 0x39); // 0-9
        frozenSet.freeze();
        boolean threw = false;
        try {
            frozenSet.set(set1);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("set() did not throw on frozen UnicodeSet");
        }
    }
}
