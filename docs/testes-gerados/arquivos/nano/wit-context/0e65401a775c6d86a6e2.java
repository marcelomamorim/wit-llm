/* @test    @summary Test for NamedParameterSpec constructor with null argument    @run main NamedParameterSpecWitupTest */

import java.security.spec.NamedParameterSpec;

public class NamedParameterSpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithNull();
    }

    public static void testConstructorWithNull() {
        try {
            new NamedParameterSpec(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            String message = e.getMessage();
            if (message == null || !message.equals("stdName must not be null")) {
                System.err.println("Failed: Exception message mismatch: " + message);
                System.exit(1);
            }
            // Test passed
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
