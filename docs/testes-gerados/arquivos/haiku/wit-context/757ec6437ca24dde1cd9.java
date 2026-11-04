/*
 * @test
 * @summary Observable behavior of public DerInputStream constructors (bounds guard and basic construction)
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidConstructionWithOffsetAndLength();
        testOutOfBoundsLengthThrowsIOException();
        testOffsetLeavingLessThanTwoBytesThrowsIOException();
    }

    static void testValidConstructionWithOffsetAndLength() throws Exception {
        byte[] data = {0x05, 0x00};
        DerInputStream in = new DerInputStream(data, 0, data.length, true);
        if (in == null) {
            throw new AssertionError("constructor returned null");
        }
    }

    static void testOutOfBoundsLengthThrowsIOException() {
        byte[] data = {0x05, 0x00, 0x00};
        boolean thrown = false;
        try {
            new DerInputStream(data, 0, 10, true);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IOException when len + offset exceeds data.length");
        }
    }

    static void testOffsetLeavingLessThanTwoBytesThrowsIOException() {
        byte[] data = {0x05, 0x00, 0x00};
        boolean thrown = false;
        try {
            new DerInputStream(data, 2, 1, true);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IOException when fewer than 2 bytes remain after offset");
        }
    }
}
