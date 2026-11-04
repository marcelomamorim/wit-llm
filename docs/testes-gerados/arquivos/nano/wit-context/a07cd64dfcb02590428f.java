/*
 * @test
 * @summary Tests for javax.crypto.spec.RC2ParameterSpec constructors, focusing on exception paths.
 * @run main RC2ParameterSpecWitupTest
 */

import javax.crypto.spec.RC2ParameterSpec;

public class RC2ParameterSpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullIV();
        testConstructorWithShortIV();
    }

    private static void testConstructorWithNullIV() {
        try {
            new RC2ParameterSpec(128, null);
            System.err.println("Failed: Expected IllegalArgumentException for null IV");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type for null IV: " + t);
        }
    }

    private static void testConstructorWithShortIV() {
        byte[] shortIV = new byte[5]; // less than 8
        try {
            new RC2ParameterSpec(128, shortIV);
            System.err.println("Failed: Expected IllegalArgumentException for short IV");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type for short IV: " + t);
        }
    }
}
