/* @test    @summary Test reset() method of BufferedInputStream for correct behavior and exception handling    @run main BufferedInputStreamWitupTest_P051b10ebcd1c */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest_P051b10ebcd1c {

    public static void main(String[] args) throws IOException {
        testResetWithValidMark();
        testResetWithoutMark();
        testResetAfterClose();
    }

    private static void testResetWithValidMark() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        bis.mark(10);
        int readByte = bis.read();
        bis.reset();
        int positionAfterReset = bis.read();
        if (readByte != positionAfterReset) {
            throw new AssertionError("Failed: reset() did not restore position correctly.");
        }
    }

    private static void testResetWithoutMark() {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        try {
            bis.reset();
            throw new AssertionError("Expected IOException when resetting without mark.");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testResetAfterClose() throws IOException {
        byte[] data = {7, 8, 9};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        bis.close();
        try {
            bis.reset();
            throw new AssertionError("Expected IOException when resetting after close.");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
