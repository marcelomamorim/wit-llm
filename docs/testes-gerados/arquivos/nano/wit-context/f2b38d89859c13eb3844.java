/* @test    @summary Test for RC2ParameterSpec constructor exception paths    @run main RC2ParameterSpecWitupTest */

import javax.crypto.spec.RC2ParameterSpec;

public class RC2ParameterSpecWitupTest {

    public static void main(String[] args) {
        testConstructorNullIV();
        testConstructorShortIV();
    }

    private static void testConstructorNullIV() {
        try {
            new RC2ParameterSpec(128, null, 0);
            System.err.println("Failed: Expected IllegalArgumentException for null IV");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            if (!"IV missing".equals(e.getMessage())) {
                System.err.println("Failed: Unexpected message for null IV: " + e.getMessage());
                System.exit(1);
            }
        }
    }

    private static void testConstructorShortIV() {
        byte[] shortIV = new byte[5];
        try {
            new RC2ParameterSpec(128, shortIV, 0);
            System.err.println("Failed: Expected IllegalArgumentException for short IV");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            if (!"IV too short".equals(e.getMessage())) {
                System.err.println("Failed: Unexpected message for short IV: " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
