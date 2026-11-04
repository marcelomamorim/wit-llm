/* @test    @summary Test getPrintableString method of DerInputStream for normal and exceptional behavior    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testGetPrintableStringNormal();
        testGetPrintableStringThrowsIOException();
    }

    private static void testGetPrintableStringNormal() throws Exception {
        byte[] data = new byte[] { 0x13, 0x0 }; // Tag for PrintableString with length 0
        DerInputStream dis = new DerInputStream(data);
        String result = dis.getPrintableString();
        if (result == null) {
            throw new AssertionError("Expected non-null result");
        }
    }

    private static void testGetPrintableStringThrowsIOException() {
        byte[] data = new byte[] { 0x13, 0x01, (byte)0xFF }; // Tag for PrintableString with invalid content
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getPrintableString();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
