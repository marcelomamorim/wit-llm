/*
 * @test
 * @summary Verify that the AVA constructor rejects null type and null value with NullPointerException
 * @modules java.base/sun.security.x509
 * @run main AVAWitupTest
 */

import sun.security.x509.AVA;

public class AVAWitupTest {

    public static void main(String[] args) {
        boolean thrown = false;
        try {
            new AVA(null, null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException when type and val are null");
        }
    }
}
