/*
 * @test
 * @summary Verify BufferedInputStream.available() before reads, after a read, after close, and with the Integer.MAX_VALUE cap
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws IOException {
        byte[] data = new byte[10];

        BufferedInputStream b = new BufferedInputStream(new ByteArrayInputStream(data));
        check(b.available() == 10, "initial available() should be 10");

        check(b.read() != -1, "read() should return a byte");
        check(b.available() == 9, "available() after one read should be 9");

        b.close();
        boolean thrown = false;
        try {
            b.available();
        } catch (IOException expected) {
            thrown = true;
        }
        check(thrown, "available() on closed stream should throw IOException");

        InputStream big = new ByteArrayInputStream(data) {
            @Override
            public int available() {
                return Integer.MAX_VALUE;
            }
        };
        BufferedInputStream b2 = new BufferedInputStream(big);
        check(b2.available() == Integer.MAX_VALUE, "available() should cap at Integer.MAX_VALUE");
        check(b2.read() != -1, "read() should return a byte");
        check(b2.available() == Integer.MAX_VALUE, "available() after read should cap at Integer.MAX_VALUE");
    }
}
