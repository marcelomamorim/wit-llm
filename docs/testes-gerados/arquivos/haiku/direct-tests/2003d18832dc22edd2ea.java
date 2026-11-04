/*
 * @test
 * @summary Checks argument validation of the ECPublicKeySpec constructor
 * @run main ECPublicKeySpecWitupTest
 */

import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;

public class ECPublicKeySpecWitupTest {

    public static void main(String[] args) {
        testNullWAndNullParamsThrowsNpeForW();
        testInfinityWithNullParamsThrowsNpeForParams();
    }

    static void testNullWAndNullParamsThrowsNpeForW() {
        try {
            new ECPublicKeySpec(null, null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException e) {
            if (!"w is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testInfinityWithNullParamsThrowsNpeForParams() {
        try {
            new ECPublicKeySpec(ECPoint.POINT_INFINITY, null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException e) {
            if (!"params is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }
}
