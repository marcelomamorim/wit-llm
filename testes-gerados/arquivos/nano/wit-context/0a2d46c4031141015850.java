/* @test    @summary Test deserialization from DerInputStream for SerialNumber    @run main SerialNumberWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {

    public static void main(String[] args) {
        testSerialNumberFromDerInputStream();
    }

    private static void testSerialNumberFromDerInputStream() {
        // Test case: valid DER-encoded integer
        byte[] validDerBytes = {0x02, 0x01, 0x05}; // INTEGER 5
        try {
            DerInputStream dis = new DerInputStream(validDerBytes);
            SerialNumber sn = new SerialNumber(dis);
            assert sn.getNumber().equals(BigInteger.valueOf(5)) : "Expected serial number 5";
            System.out.println("Test passed: valid DER input.");
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for valid DER input", e);
        }

        // Test case: DER-encoded integer with extra data (should throw IOException)
        byte[] invalidExtraData = {0x02, 0x01, 0x05, 0x00};
        try {
            DerInputStream dis = new DerInputStream(invalidExtraData);
            new SerialNumber(dis);
            throw new AssertionError("Expected IOException due to extra data");
        } catch (IOException e) {
            System.out.println("Test passed: caught expected IOException for extra data.");
        }

        // Test case: empty input (should throw IOException)
        byte[] emptyBytes = {};
        try {
            DerInputStream dis = new DerInputStream(emptyBytes);
            new SerialNumber(dis);
            throw new AssertionError("Expected IOException for empty input");
        } catch (IOException e) {
            System.out.println("Test passed: caught expected IOException for empty input.");
        }
    }
}
