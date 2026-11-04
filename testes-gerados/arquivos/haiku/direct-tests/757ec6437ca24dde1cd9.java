/*
 * @test
 * @summary Verifies that DerInputStream public constructors initialize the buffer so available() reflects the input length and offset
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] data = new byte[] {0x05, 0x00};

        DerInputStream full = new DerInputStream(data);
        if (full.available() != 2) {
            throw new RuntimeException("Expected available()==2, got " + full.available());
        }

        DerInputStream empty = new DerInputStream(new byte[0]);
        if (empty.available() != 0) {
            throw new RuntimeException("Expected available()==0 for empty input, got " + empty.available());
        }

        byte[] padded = new byte[] {0x09, 0x05, 0x00, 0x08};
        DerInputStream slice = new DerInputStream(padded, 1, 2, true);
        if (slice.available() != 2) {
            throw new RuntimeException("Expected available()==2 for slice, got " + slice.available());
        }

        DerInputStream strict = new DerInputStream(data, 0, data.length, false);
        if (strict.available() != 2) {
            throw new RuntimeException("Expected available()==2 with allowBER=false, got " + strict.available());
        }
    }
}
