/* @test    @summary Test DerInputStream constructor with byte array, offset, and length    @run main DerInputStreamWitupTest */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testValidConstructor();
        testEmptyArray();
        testZeroLength();
        testNegativeOffset();
        testOffsetExceedsArray();
        testLengthExceedsArray();
    }

    static void testValidConstructor() throws IOException {
        byte[] data = {0x30, 0x0A, 0x02, 0x01, 0x01, 0x02, 0x01, 0x02, 0x02, 0x01, 0x03};
        DerInputStream dis = new DerInputStream(data, 0, data.length);
        assert dis != null : "Constructor returned null";
        assert dis.tag == data[0] : "Tag mismatch";
    }

    static void testEmptyArray() throws IOException {
        byte[] data = {};
        DerInputStream dis = new DerInputStream(data, 0, 0);
        assert dis != null : "Constructor with empty array failed";
        assert dis.tag == 0 : "Tag should be 0 for empty array";
    }

    static void testZeroLength() throws IOException {
        byte[] data = {0x02, 0x01, 0x01};
        DerInputStream dis = new DerInputStream(data, 0, 0);
        assert dis != null : "Constructor with zero length failed";
        assert dis.tag == 0 : "Tag should be 0 for zero length";
    }

    static void testNegativeOffset() {
        byte[] data = {0x02, 0x01, 0x01};
        try {
            new DerInputStream(data, -1, 1);
            assert false : "Expected IOException for negative offset";
        } catch (IOException e) {
            assert e != null : "Caught expected IOException";
        }
    }

    static void testOffsetExceedsArray() {
        byte[] data = {0x02, 0x01, 0x01};
        try {
            new DerInputStream(data, 4, 1);
            assert false : "Expected IOException for offset exceeding array";
        } catch (IOException e) {
            assert e != null : "Caught expected IOException";
        }
    }

    static void testLengthExceedsArray() {
        byte[] data = {0x02, 0x01, 0x01};
        try {
            new DerInputStream(data, 0, 5);
            assert false : "Expected IOException for length exceeding array";
        } catch (IOException e) {
            assert e != null : "Caught expected IOException";
        }
    }
}
