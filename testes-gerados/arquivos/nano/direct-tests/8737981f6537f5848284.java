/* @test    @summary Test getPrintableString() method of DerInputStream for valid ASCII data    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testGetPrintableStringValid();
        testGetPrintableStringEmpty();
        testGetPrintableStringNonPrintable();
    }

    private static void testGetPrintableStringValid() throws IOException {
        byte[] data = {0x13, 0x07, 0x50, 0x52, 0x49, 0x4E, 0x54, 0x41, 0x42, 0x4C, 0x45}; // Printable string "PRINTABLE"
        DerInputStream dis = new DerInputStream(data);
        String result = dis.getPrintableString();
        if (!"PRINTABLE".equals(result)) {
            throw new AssertionError("Expected 'PRINTABLE', got: " + result);
        }
    }

    private static void testGetPrintableStringEmpty() throws IOException {
        byte[] data = {0x13, 0x00}; // Empty printable string
        DerInputStream dis = new DerInputStream(data);
        String result = dis.getPrintableString();
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string, got: " + result);
        }
    }

    private static void testGetPrintableStringNonPrintable() {
        byte[] data = {0x13, 0x03, 0xFF, 0x00, 0x7F}; // Non-printable bytes
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getPrintableString();
            throw new AssertionError("Expected IOException for non-printable data");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
