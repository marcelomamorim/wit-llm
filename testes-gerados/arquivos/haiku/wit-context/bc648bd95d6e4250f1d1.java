/*
 * @test
 * @summary Verifies observable skip(long) behavior of InputStream for the null stream and the default implementation
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {

    static class Bounded extends InputStream {
        private int remaining;

        Bounded(int n) {
            remaining = n;
        }

        @Override
        public int read() {
            if (remaining <= 0) {
                return -1;
            }
            remaining--;
            return 7;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        InputStream nullIn = InputStream.nullInputStream();
        check(nullIn.skip(5) == 0, "nullInputStream skip(5) should return 0");
        check(nullIn.skip(0) == 0, "nullInputStream skip(0) should return 0");
        check(nullIn.skip(Long.MAX_VALUE) == 0, "nullInputStream skip(MAX) should return 0");

        InputStream closed = InputStream.nullInputStream();
        closed.close();
        closed.close();
        boolean thrown = false;
        try {
            closed.skip(1);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "skip on closed nullInputStream should throw IOException");

        InputStream fresh = new Bounded(0);
        check(fresh.skip(0) == 0, "default skip(0) should return 0");

        InputStream bounded = new Bounded(3);
        check(bounded.skip(10) == 3, "default skip(10) with 3 bytes should return 3");
        check(bounded.skip(10) == 0, "default skip at end of stream should return 0");

        InputStream bounded2 = new Bounded(5);
        check(bounded2.skip(2) == 2, "default skip(2) should return 2");
        check(bounded2.read() == 7, "read after skip should return next byte");
    }
}
