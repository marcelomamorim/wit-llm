/* @test    @summary Test complement method of UnicodeSet for valid and invalid inputs    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testComplementValidRange();
        testComplementInvalidStart();
        testComplementInvalidEnd();
        testComplementStartGreaterThanEnd();
    }

    private static void testComplementValidRange() {
        UnicodeSet set = new UnicodeSet();
        try {
            set.complement(0x0041, 0x005A); // A-Z
            System.out.println("testComplementValidRange passed");
        } catch (Exception e) {
            System.err.println("testComplementValidRange failed: " + e);
        }
    }

    private static void testComplementInvalidStart() {
        UnicodeSet set = new UnicodeSet();
        try {
            set.complement(-1, 0x0041); // invalid start
            System.err.println("testComplementInvalidStart failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testComplementInvalidStart passed");
        } catch (Exception e) {
            System.err.println("testComplementInvalidStart failed: " + e);
        }
    }

    private static void testComplementInvalidEnd() {
        UnicodeSet set = new UnicodeSet();
        try {
            set.complement(0x0041, 0x110000); // invalid end
            System.err.println("testComplementInvalidEnd failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testComplementInvalidEnd passed");
        } catch (Exception e) {
            System.err.println("testComplementInvalidEnd failed: " + e);
        }
    }

    private static void testComplementStartGreaterThanEnd() {
        UnicodeSet set = new UnicodeSet();
        try {
            set.complement(0x005A, 0x0041); // start > end
            System.out.println("testComplementStartGreaterThanEnd passed");
        } catch (Exception e) {
            System.err.println("testComplementStartGreaterThanEnd failed: " + e);
        }
    }
}
