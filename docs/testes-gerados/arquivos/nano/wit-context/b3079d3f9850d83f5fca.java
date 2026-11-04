/* @test    @summary Test Integer.valueOf(String s, int radix) for exception paths    @run main IntegerWitupTest */

import java.lang.Integer;

public class IntegerWitupTest {

    public static void main(String[] args) {
        testNullString();
        testRadixGreaterThan36();
        testRadixLessThan2();
    }

    private static void testNullString() {
        try {
            Integer.valueOf(null, 10);
            System.err.println("Failed: Expected NumberFormatException for null string");
            System.exit(1);
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }

    private static void testRadixGreaterThan36() {
        try {
            Integer.valueOf("123", 37);
            System.err.println("Failed: Expected NumberFormatException for radix > 36");
            System.exit(1);
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }

    private static void testRadixLessThan2() {
        try {
            Integer.valueOf("123", 1);
            System.err.println("Failed: Expected NumberFormatException for radix < 2");
            System.exit(1);
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }
}
