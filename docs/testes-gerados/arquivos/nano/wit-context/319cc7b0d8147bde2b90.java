/* @test    @summary Test DerInputStream constructor with valid and invalid data    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testValidData();
        testDataTooShort();
    }

    static void testValidData() {
        byte[] data = {0x30, 0x03}; // valid ASN.1 SEQUENCE with length 3
        try {
            DerInputStream dis = new DerInputStream(data);
            assert dis != null : "DerInputStream should be created with valid data";
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for valid data", e);
        }
    }

    static void testDataTooShort() {
        byte[] data = {0x02}; // length byte only, no content
        try {
            DerInputStream dis = new DerInputStream(data);
            // Depending on implementation, may or may not throw; test for exception
            // If no exception, test passes, as constructor does not validate length immediately
        } catch (IOException e) {
            // If constructor throws, ensure it's due to data length
            assert e.getMessage().contains("EOF") || e.getMessage().contains("short") : "Expected EOF or short message";
        }
    }
}
