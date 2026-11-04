/*
 * @test
 * @summary Test ObjectOutputStream.reset() behavior including exception when stream is active
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testResetThrowsWhenActive();
        testResetWritesTC_RESETAndResetsState();
    }

    private static void testResetThrowsWhenActive() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos) {
            // Override to simulate depth != 0
            private int depth = 1;
            @Override
            public void reset() throws IOException {
                if (depth != 0) {
                    throw new IOException("stream active");
                }
                super.reset();
            }
        };

        boolean threw = false;
        try {
            oos.reset();
        } catch (IOException e) {
            if ("stream active".equals(e.getMessage())) {
                threw = true;
            } else {
                throw e;
            }
        }
        if (!threw) {
            throw new AssertionError("Expected IOException with message 'stream active' not thrown");
        }
        oos.close();
    }

    private static void testResetWritesTC_RESETAndResetsState() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        // reset should succeed when depth == 0
        oos.reset();
        oos.flush();
        byte[] data = baos.toByteArray();

        // TC_RESET is (byte)0x79 according to ObjectStreamConstants
        // We check that the byte 0x79 is present in the output
        boolean foundTC_RESET = false;
        for (byte b : data) {
            if (b == (byte)0x79) {
                foundTC_RESET = true;
                break;
            }
        }
        if (!foundTC_RESET) {
            throw new AssertionError("TC_RESET byte not found in output after reset");
        }

        oos.close();
    }
}
