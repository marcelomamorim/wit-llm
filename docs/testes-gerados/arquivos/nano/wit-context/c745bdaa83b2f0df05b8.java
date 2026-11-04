/* @test    @summary Test for sun.security.x509.OtherName constructor with null parameters    @run main sun.security.x509.OtherNameWitupTest */

import sun.security.util.ObjectIdentifier;
import sun.security.x509.OtherName;
import java.io.IOException;

public class OtherNameWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithNullOid();
        testConstructorWithNullValue();
        testConstructorWithValidParameters();
    }

    private static void testConstructorWithNullOid() {
        try {
            byte[] dummyValue = {0x01, 0x02};
            new OtherName(null, dummyValue);
            System.err.println("Failed: Expected NullPointerException for null oid");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null oid");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null oid: " + e);
        }
    }

    private static void testConstructorWithNullValue() {
        try {
            ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
            new OtherName(oid, null);
            System.err.println("Failed: Expected NullPointerException for null value");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null value");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null value: " + e);
        }
    }

    private static void testConstructorWithValidParameters() {
        try {
            ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
            byte[] value = {0x05, 0x06};
            OtherName oname = new OtherName(oid, value);
            if (oname.getOID().equals(oid) && java.util.Arrays.equals(oname.getNameValue(), value)) {
                System.out.println("Passed: Valid constructor creates object with correct fields");
            } else {
                System.err.println("Failed: Object fields do not match input parameters");
            }
        } catch (Exception e) {
            System.err.println("Failed: Exception during valid constructor test: " + e);
        }
    }
}
