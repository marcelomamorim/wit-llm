/*
 * @test
 * @summary Verifies that the GeneralName(GeneralNameInterface) constructor rejects null with a NullPointerException
 * @modules java.base/sun.security.x509
 * @run main GeneralNameWitupTest
 */

import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;

public class GeneralNameWitupTest {

    public static void main(String[] args) {
        try {
            new GeneralName((GeneralNameInterface) null);
            throw new RuntimeException("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (!"GeneralName must not be null".equals(msg)) {
                throw new RuntimeException("Unexpected NPE message: " + msg);
            }
        }
    }
}
