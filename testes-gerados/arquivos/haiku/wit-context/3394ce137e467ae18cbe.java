/*
 * @test
 * @summary BufferedInputStream(InputStream, int) rejects non-positive buffer sizes and accepts a positive size
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        InputStream src = new ByteArrayInputStream(new byte[] {1, 2, 3});

        expectIllegalArgument(src, 0);
        expectIllegalArgument(src, -1);
        expectIllegalArgument(src, Integer.MIN_VALUE);

        BufferedInputStream b = new BufferedInputStream(src, 1);
        if (b.read() != 1 || b.read() != 2 || b.read() != 3 || b.read() != -1) {
            throw new RuntimeException("Unexpected data read from BufferedInputStream with size 1");
        }
    }

    private static void expectIllegalArgument(InputStream in, int size) {
        try {
            new BufferedInputStream(in, size);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for size " + size);
    }
}
