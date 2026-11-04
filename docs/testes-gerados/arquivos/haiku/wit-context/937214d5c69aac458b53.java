/*
 * @test
 * @summary Witup-derived tests for BufferedInputStream.available()
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStreamWitupTest {

    interface Action {
        void run() throws Exception;
    }

    static final class MaxAvailableInputStream extends InputStream {
        private int remaining = 2;

        @Override
        public int read() {
            if (remaining <= 0) {
                return -1;
            }
            remaining--;
            return 'x';
        }

        @Override
        public int available() {
            return Integer.MAX_VALUE;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIOException(Action action) {
        try {
            action.run();
        } catch (IOException e) {
            return;
        } catch (Exception e) {
            throw new RuntimeException("unexpected exception: " + e, e);
        }
        throw new RuntimeException("expected IOException was not thrown");
    }

    public static void main(String[] args) throws Exception {
        byte[] data = new byte[10];

        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(data));
        check(bis.available() == 10, "initial available should equal underlying available");
        check(bis.read() != -1, "read should return a byte");
        check(bis.available() == 9, "available should decrease after read");

        BufferedInputStream big = new BufferedInputStream(new MaxAvailableInputStream());
        check(big.available() == Integer.MAX_VALUE, "initial available should be MAX_VALUE");
        check(big.read() == 'x', "read should return first byte");
        check(big.available() == Integer.MAX_VALUE, "available should saturate at MAX_VALUE on overflow");

        BufferedInputStream closed = new BufferedInputStream(new ByteArrayInputStream(data));
        closed.close();
        expectIOException(() -> closed.available());
    }
}
