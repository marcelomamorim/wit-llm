/* @test    @summary Test for AVA constructor handling null parameters    @run main AVAWitupTest */

import sun.security.x509.AVA;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerValue;

public class AVAWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullType();
        testConstructorWithNullValue();
    }

    private static void testConstructorWithNullType() {
        try {
            DerValue dummyDer = new DerValue(new byte[] {0x05, 0x00});
            new AVA(null, dummyDer);
            System.err.println("Failed: Expected NullPointerException for null type");
        } catch (NullPointerException expected) {
            System.out.println("Passed: NullPointerException thrown for null type");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null type: " + e);
        }
    }

    private static void testConstructorWithNullValue() {
        try {
            ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
            new AVA(oid, null);
            System.err.println("Failed: Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            System.out.println("Passed: NullPointerException thrown for null value");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null value: " + e);
        }
    }
}
