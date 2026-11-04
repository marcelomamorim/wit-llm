/*
 * @test
 * @summary Verify DerInputStream(byte[], int, int) reads the DER tag from the requested window of the buffer
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testOffsetSelectsTag();
        testLenWindowTagAtStart();
        testFullBufferTag();
    }

    static void testOffsetSelectsTag() throws IOException {
        // INTEGER (0x02) starts at index 1; index 0 is a leading 0x30 that must be skipped.
        byte[] data = {0x30, 0x02, 0x01, 0x05};
        DerInputStream in = new DerInputStream(data, 1, 3, true);
        if (in.tag != 0x02) {
            throw new RuntimeException("Expected tag 0x02 at offset 1, got " + in.tag);
        }
    }

    static void testLenWindowTagAtStart() throws IOException {
        // Window of length 3 starting at 0 covers the INTEGER TLV exactly.
        byte[] data = {0x02, 0x01, 0x05, 0x04};
        DerInputStream in = new DerInputStream(data, 0, 3, true);
        if (in.tag != 0x02) {
            throw new RuntimeException("Expected tag 0x02 for window at 0, got " + in.tag);
        }
    }

    static void testFullBufferTag() throws IOException {
        byte[] data = {0x04, 0x01, 0x41};
        DerInputStream in = new DerInputStream(data, 0, data.length, true);
        if (in.tag != 0x04) {
            throw new RuntimeException("Expected OCTET STRING tag 0x04, got " + in.tag);
        }
    }
}
