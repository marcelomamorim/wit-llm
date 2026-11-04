/*
 * @test
 * @summary Witup-derived tests for ECFieldF2m(int m) constructor argument validation
 * @run main ECFieldF2mWitupTest
 */

import java.security.spec.ECFieldF2m;

public class ECFieldF2mWitupTest {

    public static void main(String[] args) {
        testNonPositiveMThrows(0);
        testNonPositiveMThrows(-1);
        testNonPositiveMThrows(Integer.MIN_VALUE);
        testPositiveMAccepted(1);
        testPositiveMAccepted(163);
        testFieldSize(5);
        System.out.println("All tests passed");
    }

    private static void testNonPositiveMThrows(int m) {
        try {
            new ECFieldF2m(m);
            throw new RuntimeException("Expected IllegalArgumentException for m=" + m);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testPositiveMAccepted(int m) {
        ECFieldF2m field = new ECFieldF2m(m);
        if (field.getFieldSize() != m) {
            throw new RuntimeException("Unexpected field size for m=" + m
                    + ": " + field.getFieldSize());
        }
    }

    private static void testFieldSize(int m) {
        ECFieldF2m field = new ECFieldF2m(m);
        if (field.getFieldSize() != m) {
            throw new RuntimeException("getFieldSize mismatch");
        }
    }
}
