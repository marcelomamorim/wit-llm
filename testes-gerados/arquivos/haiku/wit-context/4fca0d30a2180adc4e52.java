/*
 * @test
 * @summary Verifies ECPoint constructor preserves affine coordinates and rejects null coordinates with NullPointerException
 * @run main ECPointWitupTest
 */
import java.math.BigInteger;
import java.security.spec.ECPoint;

public class ECPointWitupTest {
    public static void main(String[] args) {
        BigInteger x = BigInteger.valueOf(1);
        BigInteger y = BigInteger.valueOf(2);
        ECPoint p = new ECPoint(x, y);
        if (p.getAffineX() != x || p.getAffineY() != y) {
            throw new RuntimeException("affine coordinates not preserved");
        }
        expectNpe(null, y);
        expectNpe(x, null);
        expectNpe(null, null);
    }

    private static void expectNpe(BigInteger x, BigInteger y) {
        try {
            new ECPoint(x, y);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected");
    }
}
