/*
 * @test
 * @summary Test ECFieldF2m constructor with valid and invalid 'm' values
 * @run main ECFieldF2mWitupTest
 */
import java.security.spec.ECFieldF2m;
public class ECFieldF2mWitupTest {
    public static void main(String[] args) {
        testValidM();
        testInvalidMZero();
        testInvalidMNegative();
    }
    static void testValidM() {
        try {
            ECFieldF2m field = new ECFieldF2m(5);
            assert field != null : "ECFieldF2m instance should not be null";
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid m", e);
        }
    }
    static void testInvalidMZero() {
        try {
            new ECFieldF2m(0);
            throw new AssertionError("Expected IllegalArgumentException for m=0");
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("m is not positive") : "Unexpected message: " + e.getMessage();
        }
    }
    static void testInvalidMNegative() {
        try {
            new ECFieldF2m(-3);
            throw new AssertionError("Expected IllegalArgumentException for negative m");
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("m is not positive") : "Unexpected message: " + e.getMessage();
        }
    }
}