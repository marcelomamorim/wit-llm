/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getBytes(byte[]) for correct behavior and exception handling
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testGetBytesNormal();
        testGetBytesEmptyArray();
        testGetBytesNullArray();
    }

    private static void testGetBytesNormal() throws Exception {
        byte[] data = {0x01, 0x02, 0x03, 0x04};
        DerInputStream der = new DerInputStream(data);
        byte[] val = new byte[data.length];
        der.getBytes(val);
        for (int i = 0; i < val.length; i++) {
            if (val[i] != data[i]) {
                throw new RuntimeException("testGetBytesNormal: byte mismatch at index " + i);
            }
        }
    }

    private static void testGetBytesEmptyArray() throws Exception {
        byte[] data = {0x01, 0x02};
        DerInputStream der = new DerInputStream(data);
        byte[] val = new byte[0];
        // Should not throw IOException for zero-length array
        der.getBytes(val);
    }

    private static void testGetBytesNullArray() throws Exception {
        // The method does not accept null, but since val is byte[], passing null would cause NullPointerException before method logic
        // So we test that passing null throws NullPointerException
        byte[] data = {0x01};
        DerInputStream der = new DerInputStream(data);
        try {
            der.getBytes(null);
            throw new RuntimeException("testGetBytesNullArray: expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
