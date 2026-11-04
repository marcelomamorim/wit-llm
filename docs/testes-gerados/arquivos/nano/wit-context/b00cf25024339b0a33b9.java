/* @test    @summary Test complement method of UnicodeSet for exception paths    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testComplementWithInvalidStart();
        testComplementWithInvalidEnd();
        testComplementWithValidRange();
    }

    private static void testComplementWithInvalidStart() {
        UnicodeSet us = new UnicodeSet();
        try {
            us.complement(Integer.MIN_VALUE - 1, 1000);
            System.out.println("Failed: Expected IllegalArgumentException for start below MIN_VALUE");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testComplementWithInvalidEnd() {
        UnicodeSet us = new UnicodeSet();
        try {
            us.complement(0, Integer.MAX_VALUE + 1);
            System.out.println("Failed: Expected IllegalArgumentException for end above MAX_VALUE");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testComplementWithValidRange() {
        UnicodeSet us = new UnicodeSet();
        try {
            us.complement(0, 100);
            System.out.println("Complement with valid range executed without exception.");
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception for valid range: " + e);
        }
    }
}
