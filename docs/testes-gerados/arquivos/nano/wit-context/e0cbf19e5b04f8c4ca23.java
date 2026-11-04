/*
 * @test
 * @summary Test UnicodeSet.set(UnicodeSet) for supported behaviors and exceptions
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testSetWithFrozen();
        testSetWithUnfrozen();
    }

    private static void testSetWithFrozen() {
        UnicodeSet set1 = new UnicodeSet();
        UnicodeSet set2 = new UnicodeSet();
        try {
            set1.set(set2);
            System.out.println("set with unfrozen set succeeded as expected.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Unexpected exception for unfrozen set: " + e);
        }
        // Freeze set2 to simulate frozen state
        set2.checkFrozen();
        // Attempt to set from a frozen set2
        try {
            set1.set(set2);
            System.out.println("Error: Expected exception not thrown when setting from frozen set.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException when setting from frozen set.");
        }
    }

    private static void testSetWithUnfrozen() {
        UnicodeSet set1 = new UnicodeSet();
        UnicodeSet set2 = new UnicodeSet();
        // Initialize set2 with some characters
        try {
            set2.add(0x0041, 0x005A); // A-Z
            set2.add(0x0061, 0x007A); // a-z
        } catch (Exception e) {
            System.out.println("Unexpected exception during set initialization: " + e);
        }
        // Ensure set2 is not frozen
        // Call set
        try {
            set1.set(set2);
            System.out.println("set from unfrozen set succeeded.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Unexpected exception when setting from unfrozen set: " + e);
        }
        // Verify that set1 now contains the same characters as set2
        boolean containsAtoZ = true;
        for (int c = 0x0041; c <= 0x005A; c++) {
            if (!set1.contains(c)) {
                containsAtoZ = false;
                break;
            }
        }
        boolean containsaToz = true;
        for (int c = 0x0061; c <= 0x007A; c++) {
            if (!set1.contains(c)) {
                containsaToz = false;
                break;
            }
        }
        if (containsAtoZ && containsaToz) {
            System.out.println("set1 correctly contains A-Z and a-z.");
        } else {
            System.out.println("Error: set1 does not contain expected characters.");
        }
    }
}
