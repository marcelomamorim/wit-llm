/*
 * @test
 * @summary Test sun.security.x509.OtherName constructor with ObjectIdentifier and byte[]
 * @run main OtherNameWitupTest
 */

import sun.security.x509.OtherName;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.util.Arrays;

public class OtherNameWitupTest {
    public static void main(String[] args) throws Exception {
        testNullOidThrows();
        testNullValueThrows();
        testValidConstruction();
    }

    private static void testNullOidThrows() {
        byte[] dummyValue = new byte[] {0x01, 0x02};
        try {
            new OtherName(null, dummyValue);
            throw new RuntimeException("Expected NullPointerException for null oid");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testNullValueThrows() {
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 3});
        try {
            new OtherName(oid, null);
            throw new RuntimeException("Expected NullPointerException for null value");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testValidConstruction() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 3});
        byte[] value = new byte[] {0x01, 0x02, 0x03};
        OtherName otherName = new OtherName(oid, value);

        // Check that getOID returns the same oid instance (or equal)
        ObjectIdentifier returnedOid = otherName.getOID();
        if (!returnedOid.equals(oid)) {
            throw new RuntimeException("Returned OID does not match input OID");
        }

        // Check that getNameValue returns a clone equal to input
        byte[] returnedValue = otherName.getNameValue();
        if (!Arrays.equals(returnedValue, value)) {
            throw new RuntimeException("Returned nameValue does not match input value");
        }

        // Check that returnedValue is a clone, not the same array
        if (returnedValue == value) {
            throw new RuntimeException("getNameValue did not return a clone of the input array");
        }
    }
}
