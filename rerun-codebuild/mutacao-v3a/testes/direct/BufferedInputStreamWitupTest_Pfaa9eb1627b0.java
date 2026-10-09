/* @test    @summary Test the reset() method of BufferedInputStream for valid and invalid mark positions    @run main BufferedInputStreamWitupTest_Pfaa9eb1627b0 */

import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest_Pfaa9eb1627b0 {
    public static void main(String[] args) throws IOException {
        testResetWithValidMark();
        testResetWithInvalidMark();
    }

    private static void testResetWithValidMark() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(byteStream);
        bis.mark(10);
        bis.read(); // read one byte
        bis.reset(); // should succeed
        int firstByte = bis.read();
        if (firstByte != data[0]) {
            throw new AssertionError("Reset did not set position to mark position");
        }
    }

    private static void testResetWithInvalidMark() throws IOException {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(byteStream);
        // Do not call mark(), mark position defaults to -1
        try {
            bis.reset();
            throw new AssertionError("Expected IOException for invalid mark");
        } catch (IOException e) {
            if (!"Resetting to invalid mark".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
