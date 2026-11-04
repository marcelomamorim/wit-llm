/* @test    @summary Test UnicodeSet constructor with pattern string    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testEmptyPattern();
        testSimplePattern();
        testComplexPattern();
        testInvalidPattern();
    }

    private static void testEmptyPattern() {
        try {
            UnicodeSet set = new UnicodeSet("");
            System.out.println("testEmptyPattern passed");
        } catch (Exception e) {
            System.err.println("testEmptyPattern failed: " + e);
        }
    }

    private static void testSimplePattern() {
        try {
            UnicodeSet set = new UnicodeSet("[a-z]");
            if (set.contains('a') && set.contains('z') && !set.contains('A')) {
                System.out.println("testSimplePattern passed");
            } else {
                System.err.println("testSimplePattern failed: contains() check");
            }
        } catch (Exception e) {
            System.err.println("testSimplePattern failed: " + e);
        }
    }

    private static void testComplexPattern() {
        try {
            UnicodeSet set = new UnicodeSet("[\u0400-\u04FF\u0500-\u052F]");
            boolean containsCyrillic = set.contains('Ж');
            boolean notContainsLatin = !set.contains('A');
            if (containsCyrillic && notContainsLatin) {
                System.out.println("testComplexPattern passed");
            } else {
                System.err.println("testComplexPattern failed: contains() check");
            }
        } catch (Exception e) {
            System.err.println("testComplexPattern failed: " + e);
        }
    }

    private static void testInvalidPattern() {
        try {
            UnicodeSet set = new UnicodeSet("[a-\");
            System.err.println("testInvalidPattern failed: exception expected");
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidPattern passed");
        } catch (Exception e) {
            System.err.println("testInvalidPattern failed: " + e);
        }
    }
}
