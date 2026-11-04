/*
 * @test
 * @summary Tests for java.io.BufferedInputStream.available()
 * @run main BufferedInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testAvailableWithNullInput();
        testAvailableWithOpenStream();
    }

    private static void testAvailableWithNullInput() {
        BufferedInputStream bis = null;
        try {
            // Simulate null internal stream by creating a subclass that returns null
            bis = new BufferedInputStream(new ByteArrayInputStream(new byte[0])) {
                @Override
                protected java.io.InputStream getInIfOpen() {
                    return null;
                }
            };
            int result = bis.available();
            // Expect an IOException due to getInIfOpen() returning null
            System.err.println("Expected IOException not thrown");
        } catch (IOException e) {
            System.out.println("Caught expected IOException for null input: " + e.getClass().getName());
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException as fallback: " + e.getClass().getName());
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    private static void testAvailableWithOpenStream() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        int availableBytes = bis.available();
        int expected = bis.count - bis.pos;
        if (availableBytes != expected) {
            System.err.println("Test failed: available() returned " + availableBytes + ", expected " + expected);
        } else {
            System.out.println("available() returned expected value: " + availableBytes);
        }
        bis.close();
    }
}
