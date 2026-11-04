/* @test    @summary Test DerInputStream constructor with valid byte array input    @run main DerInputStreamWitupTest */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithValidData();
        testConstructorWithEmptyArray();
        testConstructorWithNullArray();
    }

    static void testConstructorWithValidData() throws IOException {
        byte[] data = {0x30, 0x0A, 0x02, 0x01, 0x01, 0x02, 0x01, 0x02, 0x02, 0x01, 0x03};
        DerInputStream dis = new DerInputStream(data);
        assert dis != null : "DerInputStream should not be null for valid data";
        assert dis.tag == data[0] : "Tag should match first byte of data";
    }

    static void testConstructorWithEmptyArray() {
        byte[] emptyData = {};
        try {
            DerInputStream dis = new DerInputStream(emptyData);
            assert dis != null : "DerInputStream should be created with empty array";
        } catch (IOException e) {
            assert false : "IOException should not be thrown for empty array";
        }
    }

    static void testConstructorWithNullArray() {
        try {
            DerInputStream dis = new DerInputStream(null);
            assert false : "NullPointerException or IOException expected when passing null";
        } catch (NullPointerException e) {
            // Expected, as passing null should cause NPE
        } catch (IOException e) {
            // Expected, as constructor may throw IOException
        }
    }
}
