/* @test    @summary Test for GeneralName constructor with null argument to verify NullPointerException is thrown.    @run main GeneralNameWitupTest */

import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;

public class GeneralNameWitupTest {

    public static void main(String[] args) {
        testConstructorWithNull();
    }

    public static void testConstructorWithNull() {
        try {
            new GeneralName((GeneralNameInterface) null);
            System.err.println("Failed: Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException was thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }
}
