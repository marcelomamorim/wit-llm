/*
 * @test
 * @summary Witup-derived checks for BufferedInputStream.reset() exception paths and mark/reset behavior
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testResetWithoutMarkThrows();
        testResetAfterCloseThrows();
        testResetRewindsToMark();
    }

    static void testResetWithoutMarkThrows() throws IOException {
        BufferedInputStream in = new BufferedInputStream(
                new ByteArrayInputStream(new byte[] {1, 2, 3}));
        boolean thrown = false;
        try {
            in.reset();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IOException on reset without mark");
        }
    }

    static void testResetAfterCloseThrows() throws IOException {
        BufferedInputStream in = new BufferedInputStream(
                new ByteArrayInputStream(new byte[] {1, 2, 3}));
        in.mark(10);
        in.close();
        boolean thrown = false;
        try {
            in.reset();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IOException on reset after close");
        }
    }

    static void testResetRewindsToMark() throws IOException {
        byte[] data = {10, 20, 30, 40};
        BufferedInputStream in = new BufferedInputStream(
                new ByteArrayInputStream(data), 8);
        if (in.read() != 10) {
            throw new RuntimeException("unexpected first byte");
        }
        in.mark(10);
        if (in.read() != 20) {
            throw new RuntimeException("unexpected byte after mark");
        }
        if (in.read() != 30) {
            throw new RuntimeException("unexpected second byte after mark");
        }
        in.reset();
        if (in.read() != 20) {
            throw new RuntimeException("reset did not rewind to mark");
        }
        if (in.read() != 30) {
            throw new RuntimeException("unexpected byte after reset");
        }
        if (in.read() != 40) {
            throw new RuntimeException("unexpected trailing byte");
        }
        if (in.read() != -1) {
            throw new RuntimeException("expected end of stream");
        }
    }
}
