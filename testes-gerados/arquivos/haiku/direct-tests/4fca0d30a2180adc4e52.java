/*
 * @test
 * @summary Verify ECPoint affine constructor stores coordinates and rejects null coordinates
 * @run main ECPointWitupTest
 */
import java.math.BigInteger;
import java.security.spec.ECPoint;

public class ECPointWitupTest {
    public static void main(String[] args) {
        BigInteger x = BigInteger.valueOf(3);
        BigInteger y = BigInteger.valueOf(7);

        ECPoint p = new ECPoint(x, y);
        if (!x.equals(p.getAffineX())) {
            throw new RuntimeException("affine x mismatch");
        }
        if (!y.equals(p.getAffineY())) {
            throw new RuntimeException("affine y mismatch");
        }

        boolean npeForNullX = false;
        try {
            new ECPoint(null, y);
        } catch (NullPointerException e) {
            npeForNullX = true;
        }
        if (!npeForNullX) {
            throw new RuntimeException("expected NullPointerException for null x");
        }

        boolean npeForNullY = false;
        try {
            new ECPoint(x, null);
        } catch (NullPointerException e) {
            npeForNullY = true;
        }
        if (!npeForNullY) {
            throw new RuntimeException("expected NullPointerException for null y");
        }

        boolean npeForBothNull = false;
        try {
            new ECPoint(null, null);
        } catch (NullPointerException e) {
            npeForBothNull = true;
        }
        if (!npeForBothNull) {
            throw new RuntimeException("expected NullPointerException for null x and y");
        }

        ECPoint q = new ECPoint(BigInteger.valueOf(3), BigInteger.valueOf(7));
        if (!p.equals(q)) {
            throw new RuntimeException("equal coordinates must produce equal points");
        }
        if (p.hashCode() != q.hashCode()) {
            throw new RuntimeException("equal points must have equal hash codes");
        }

        ECPoint r = new ECPoint(BigInteger.valueOf(4), y);
        if (p.equals(r)) {
            throw new RuntimeException("points with different x must not be equal");
        }
    }
}
