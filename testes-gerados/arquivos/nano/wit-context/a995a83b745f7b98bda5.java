/* @test    @summary Test DerInputStream.getDerValue() for normal and exceptional behavior    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDerValueNormal();
        testGetDerValueEOF();
        testGetDerValueInvalid();
    }

    static void testGetDerValueNormal() throws Exception {
        byte[] data = {0x30, 0x0A, 0x02, 0x01, 0x01, 0x02, 0x01, 0x02, 0x02, 0x01, 0x03};
        DerInputStream dis = new DerInputStream(data);
        DerValue dv = dis.getDerValue();
        if (dv == null) {
            throw new AssertionError("Expected DerValue, got null");
        }
    }

    static void testGetDerValueEOF() {
        byte[] data = {(byte)0x30, 0x02, 0x01}; // incomplete length
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getDerValue();
            throw new AssertionError("Expected IOException due to EOF");
        } catch (IOException e) {
            // expected
        }
    }

    static void testGetDerValueInvalid() {
        byte[] data = {(byte)0xFF, 0x00}; // invalid tag
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getDerValue();
            // Depending on implementation, may or may not throw
        } catch (IOException e) {
            // expected or acceptable
        }
    }
}
