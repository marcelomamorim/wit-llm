/*
 * @test
 * @summary Verifies DerInputStream.getBytes(byte[]) reads exact octets, handles zero-length arrays, and throws IOException on short reads
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadsExactBytes();
        testReadsFromOffsetConstructor();
        testZeroLengthArrayDoesNotThrow();
        testShortReadThrowsIOException();
        System.out.println("PASS");
    }

    static void testReadsExactBytes() throws IOException {
        byte[] data = {1, 2, 3};
        DerInputStream in = new DerInputStream(data);
        byte[] out = new byte[3];
        in.getBytes(out);
        check(out[0] == 1 && out[1] == 2 && out[2] == 3, "exact read mismatch");
    }

    static void testReadsFromOffsetConstructor() throws IOException {
        byte[] data = {9, 20, 30, 40};
        DerInputStream in = new DerInputStream(data, 1, 2, true);
        byte[] out = new byte[2];
        in.getBytes(out);
        check(out[0] == 20 && out[1] == 30, "offset read mismatch");
    }

    static void testZeroLengthArrayDoesNotThrow() throws IOException {
        byte[] data = {4};
        DerInputStream in = new DerInputStream(data);
        in.getBytes(new byte[0]);
    }

    static void testShortReadThrowsIOException() {
        byte[] data = {1, 2};
        DerInputStream in;
        try {
            in = new DerInputStream(data);
        } catch (IOException e) {
            throw new RuntimeException("unexpected constructor failure", e);
        }
        boolean thrown = false;
        try {
            in.getBytes(new byte[4]);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "short read did not throw IOException");
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
