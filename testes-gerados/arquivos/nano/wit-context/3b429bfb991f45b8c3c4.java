/* @test    @summary Test for MGF1ParameterSpec constructor with null argument to verify NullPointerException is thrown    @run main MGF1ParameterSpecWitupTest */

import java.security.spec.MGF1ParameterSpec;

public class MGF1ParameterSpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithNull();
    }

    public static void testConstructorWithNull() {
        try {
            new MGF1ParameterSpec(null);
            System.err.println("Failed: Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            String message = e.getMessage();
            if (message != null && message.contains("digest algorithm is null")) {
                System.out.println("Passed: NullPointerException thrown as expected.");
            } else {
                System.err.println("Failed: NullPointerException thrown, but message was unexpected.");
                System.exit(1);
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
