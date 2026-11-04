/*
 * @test
 * @summary Verify null argument checks of the ECPublicKeySpec constructor
 * @run main ECPublicKeySpecWitupTest
 */
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;

public class ECPublicKeySpecWitupTest {

    public static void main(String[] args) {
        testNullWAndNullParams();
        testNonNullWWithNullParams();
        testPointInfinityWithNullParamsThrowsNpeFirst();
    }

    static void testNullWAndNullParams() {
        expectNullPointer(null, null);
    }

    static void testNonNullWWithNullParams() {
        expectNullPointer(ECPoint.POINT_INFINITY, null);
    }

    static void testPointInfinityWithNullParamsThrowsNpeFirst() {
        expectNullPointer(ECPoint.POINT_INFINITY, null);
    }

    static void expectNullPointer(ECPoint w, java.security.spec.ECParameterSpec params) {
        try {
            new ECPublicKeySpec(w, params);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException was not thrown");
    }
}
