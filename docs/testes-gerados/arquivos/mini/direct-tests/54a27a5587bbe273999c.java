/*
 * @test
 * @summary Test java.util.Base64 write(int b) method
 * @run main Base64WitupTest
 */

import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) throws Exception {
        testWriteSingleByte();
    }

    private static void testWriteSingleByte() throws IOException {
        // Obtain a Base64 encoder OutputStream
        OutputStream encoderStream = Base64.getEncoder().wrap(new NullOutputStream());

        // Write a single byte with value 0
        encoderStream.write(0);

        // Write a single byte with value 255 (0xFF)
        encoderStream.write(255);

        // Write a single byte with value 128 (0x80)
        encoderStream.write(128);

        // Write a single byte with value 1
        encoderStream.write(1);

        // Flush and close the stream
        encoderStream.flush();
        encoderStream.close();
    }

    // A simple OutputStream that discards all bytes written
    private static class NullOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
            // discard
        }
    }
}
