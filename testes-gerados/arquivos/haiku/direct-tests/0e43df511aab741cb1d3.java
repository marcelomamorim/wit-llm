/*
 * @test
 * @summary Verifies skip() on the Reader returned by Reader.nullReader(): returns 0 while open and throws IOException after close
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;

public class ReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Reader r = Reader.nullReader();
        if (r.skip(10L) != 0L) {
            throw new RuntimeException("skip(10) on open nullReader should return 0");
        }
        if (r.skip(0L) != 0L) {
            throw new RuntimeException("skip(0) on open nullReader should return 0");
        }
        r.close();
        boolean thrown = false;
        try {
            r.skip(5L);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("skip() after close should throw IOException");
        }
    }
}
