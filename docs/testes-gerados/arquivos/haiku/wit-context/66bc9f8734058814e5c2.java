/*
 * @test
 * @summary Witup-derived checks for DerInputStream.getSet(int): tag mismatch and basic SET parsing
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWrongTagThrowsIOException();
        testEmptyInputThrowsIOException();
        testEmptySet();
        testSingleIntegerElementSet();
    }

    static void testWrongTagThrowsIOException() throws Exception {
        DerInputStream in = new DerInputStream(new byte[] {0x02, 0x01, 0x00});
        try {
            in.getSet(0);
            throw new AssertionError("expected IOException for non-SET tag");
        } catch (IOException expected) {
        }
    }

    static void testEmptyInputThrowsIOException() throws Exception {
        DerInputStream in = new DerInputStream(new byte[0]);
        try {
            in.getSet(0);
            throw new AssertionError("expected IOException for empty input");
        } catch (IOException expected) {
        }
    }

    static void testEmptySet() throws Exception {
        DerInputStream in = new DerInputStream(new byte[] {0x31, 0x00});
        DerValue[] values = in.getSet(0);
        if (values == null || values.length != 0) {
            throw new AssertionError("expected empty DerValue array");
        }
    }

    static void testSingleIntegerElementSet() throws Exception {
        DerInputStream in = new DerInputStream(
                new byte[] {0x31, 0x03, 0x02, 0x01, 0x05});
        DerValue[] values = in.getSet(1);
        if (values == null || values.length != 1) {
            throw new AssertionError("expected one element in SET");
        }
        if (values[0] == null) {
            throw new AssertionError("element must not be null");
        }
    }
}
