/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getBytes(byte[] val) method
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testGetBytesExactLength();
        testGetBytesEmptyArray();
        testGetBytesShortRead();
    }

    static void testGetBytesExactLength() throws Exception {
        byte[] data = {1, 2, 3, 4, 5};
        DerInputStream der = new DerInputStream(data);
        byte[] val = new byte[data.length];
        der.getBytes(val);
        for (int i = 0; i < val.length; i++) {
            if (val[i] != data[i]) {
                throw new RuntimeException("testGetBytesExactLength: byte mismatch at index " + i);
            }
        }
    }

    static void testGetBytesEmptyArray() throws Exception {
        byte[] data = {10, 20, 30};
        DerInputStream der = new DerInputStream(data);
        byte[] val = new byte[0];
        der.getBytes(val); // should not throw and do nothing
    }

    static void testGetBytesShortRead() throws Exception {
        byte[] data = {1, 2};
        DerInputStream der = new DerInputStream(data);
        byte[] val = new byte[3];
        try {
            der.getBytes(val);
            throw new RuntimeException("testGetBytesShortRead: expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"Short read of DER octet string".equals(msg)) {
                throw new RuntimeException("testGetBytesShortRead: unexpected IOException message: " + msg);
            }
        }
    }
}
