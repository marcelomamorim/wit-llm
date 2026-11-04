/* @test    @summary Test getBMPString() method of DerInputStream for valid BMP string data    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testValidBMPString();
        testEmptyBMPString();
        testInvalidBMPString();
    }

    private static void testValidBMPString() throws IOException {
        byte[] bmpData = { (byte)0xE0, (byte)0x04, (byte)0xD8, (byte)0x34, (byte)0xDD, (byte)0x1E };
        DerInputStream dis = new DerInputStream(bmpData);
        String result = dis.getBMPString();
        // Expect a valid BMP string, here just check non-null and non-empty
        if (result == null || result.isEmpty()) {
            throw new AssertionError("Expected non-empty BMP string");
        }
        System.out.println("Valid BMP string test passed: " + result);
    }

    private static void testEmptyBMPString() throws IOException {
        byte[] emptyData = {};
        try {
            DerInputStream dis = new DerInputStream(emptyData);
            dis.getBMPString();
            throw new AssertionError("Expected IOException for empty data");
        } catch (IOException e) {
            System.out.println("Empty BMP string test passed: " + e.getMessage());
        }
    }

    private static void testInvalidBMPString() throws IOException {
        byte[] invalidData = { (byte)0xFF, (byte)0xFF };
        DerInputStream dis = new DerInputStream(invalidData);
        try {
            dis.getBMPString();
            // Depending on implementation, may throw or return invalid string
            // Here, we assume it throws IOException for invalid data
            throw new AssertionError("Expected IOException for invalid BMP data");
        } catch (IOException e) {
            System.out.println("Invalid BMP string test passed: " + e.getMessage());
        }
    }
}
