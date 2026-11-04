/*
 * @test
 * @summary Test UnicodeSet(int start, int end) constructor behavior
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        // Test with a normal range where start < end
        UnicodeSet set1 = new UnicodeSet(10, 20);
        // The constructor calls complement(start, end), so the set should contain all code points except [10..20]
        // Check that 9 is contained (before start)
        if (!set1.contains(9)) {
            throw new AssertionError("Expected set1 to contain 9");
        }
        // Check that 10 is NOT contained (start of excluded range)
        if (set1.contains(10)) {
            throw new AssertionError("Expected set1 NOT to contain 10");
        }
        // Check that 15 is NOT contained (inside excluded range)
        if (set1.contains(15)) {
            throw new AssertionError("Expected set1 NOT to contain 15");
        }
        // Check that 20 is NOT contained (end of excluded range)
        if (set1.contains(20)) {
            throw new AssertionError("Expected set1 NOT to contain 20");
        }
        // Check that 21 is contained (after end)
        if (!set1.contains(21)) {
            throw new AssertionError("Expected set1 to contain 21");
        }

        // Test with start == end (single code point exclusion)
        UnicodeSet set2 = new UnicodeSet(100, 100);
        if (set2.contains(99) == false) {
            throw new AssertionError("Expected set2 to contain 99");
        }
        if (set2.contains(100)) {
            throw new AssertionError("Expected set2 NOT to contain 100");
        }
        if (set2.contains(101) == false) {
            throw new AssertionError("Expected set2 to contain 101");
        }

        // Test with start > end (should complement an empty range, effectively complement nothing)
        UnicodeSet set3 = new UnicodeSet(50, 40);
        // Since complement(start,end) with start > end likely excludes nothing, set3 should be empty complement of empty range, so contains all code points
        // Check that 45 is contained
        if (!set3.contains(45)) {
            throw new AssertionError("Expected set3 to contain 45");
        }

        System.out.println("All tests passed.");
    }
}
