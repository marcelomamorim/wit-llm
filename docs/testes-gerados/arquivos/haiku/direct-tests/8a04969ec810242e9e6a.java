/*
 * @test
 * @summary Verifies ECFieldF2m(int m) rejects non-positive m and accepts positive m.
 * @run main ECFieldF2mWitupTest
 */

import java.security.spec.ECFieldF2m;

public class ECFieldF2mWitupTest {

    public static void main(String[] args) {
        testNonPositiveMThrowsIllegalArgumentException();
        testPositiveMDoesNotThrow();
        testBoundaryMOneIsAccepted();
    }

    static void testNonPositiveMThrowsIllegalArgumentException() {
        int[] invalid = {0, -1, -163, Integer.MIN_VALUE};
        for (int m : invalid) {
            boolean thrown = false;
            try {
                new ECFieldF2m(m);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for m=" + m);
            }
        }
    }

    static void testPositiveMDoesNotThrow() {
        int[] valid = {2, 3, 163, 233, 283, 409, 571};
        for (int m : valid) {
            ECFieldF2m field = new ECFieldF2m(m);
            if (field == null) {
                throw new RuntimeException("Constructor returned null for m=" + m);
            }
        }
    }

    static void testBoundaryMOneIsAccepted() {
        ECFieldF2m field = new ECFieldF2m(1);
        if (field == null) {
            throw new RuntimeException("Constructor returned null for m=1");
        }
    }
}
