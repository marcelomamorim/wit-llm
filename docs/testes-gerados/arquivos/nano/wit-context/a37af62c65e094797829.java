/* @test    @summary Test for Reader.reset() method to verify IOException is thrown as expected.    @run main ReaderWitupTest */

import java.io.Reader;
import java.io.IOException;

public class ReaderWitupTest {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        Reader reader = new Reader() {
            @Override
            public int read() throws IOException {
                return -1;
            }

            @Override
            public void close() throws IOException {
                // no-op
            }
        };
        try {
            reader.reset();
            System.err.println("FAIL: Expected IOException was not thrown.");
            System.exit(1);
        } catch (IOException e) {
            if ("reset() not supported".equals(e.getMessage())) {
                System.out.println("PASS: IOException thrown as expected.");
            } else {
                System.err.println("FAIL: IOException message mismatch.");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception type: " + e);
            System.exit(1);
        }
    }
}
