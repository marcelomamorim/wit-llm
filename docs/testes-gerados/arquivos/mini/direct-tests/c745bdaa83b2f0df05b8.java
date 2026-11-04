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
        testValidOidAndValue();
        testUnrecognizedOid();
    }

    private static void testNullOidThrows() {
        byte[] dummyValue = new byte[] {0x01, 0x02};
        try {
            new OtherName(null, dummyValue);
            throw new RuntimeException("Expected NullPointerException for null oid");
        } catch (NullPointerException e) {
            if (!"parameters may not be null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message", e);
            }
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
            if (!"parameters may not be null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message", e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testValidOidAndValue() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 3});
        byte[] value = new byte[] {0x01, 0x02, 0x03};
        OtherName otherName = new OtherName(oid, value);
        if (!oid.equals(otherName.getOID())) {
            throw new RuntimeException("OID mismatch");
        }
        byte[] returnedValue = otherName.getNameValue();
        if (!Arrays.equals(value, returnedValue)) {
            throw new RuntimeException("Value mismatch");
        }
        String name = otherName.toString();
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Name string is null or empty");
        }
    }

    private static void testUnrecognizedOid() throws IOException {
        // Use an OID unlikely to be recognized by OIDMap
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {9, 9, 9, 9, 9});
        byte[] value = new byte[] {0x0A, 0x0B};
        OtherName otherName = new OtherName(oid, value);
        String expectedPrefix = "Unrecognized ObjectIdentifier: ";
        String name = otherName.toString();
        if (!name.startsWith(expectedPrefix)) {
            throw new RuntimeException("Expected name to start with '" + expectedPrefix + "' but was: " + name);
        }
    }
}