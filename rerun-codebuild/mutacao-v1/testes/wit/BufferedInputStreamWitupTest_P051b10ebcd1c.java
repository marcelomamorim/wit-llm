/* @test    @summary Test for BufferedInputStream.reset() behavior and exception paths    @run main BufferedInputStreamWitupTest_P051b10ebcd1c */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest_P051b10ebcd1c {

    public static void main(String[] args) throws Exception {
        testResetWithNullBuffer();
        testResetWithInvalidMark();
    }

    private static void testResetWithNullBuffer() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] {1, 2, 3});
        BufferedInputStream bis = new BufferedInputStream(bais);
        try {
            // Simulate closed stream by nullifying buf (not directly accessible, so use reflection or simulate via subclass)
            // Since buf is protected, we can create a subclass for testing
            BufferedInputStream testStream = new BufferedInputStream(bais) {
                {
                    buf = null;
                }
            };
            testStream.reset();
            throw new AssertionError("Expected IOException for null buf not thrown");
        } catch (IOException e) {
            if (!"Stream closed".equals(e.getMessage()) && !e.getClass().getSimpleName().equals("IOException")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }

    private static void testResetWithInvalidMark() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] {1, 2, 3});
        BufferedInputStream bis = new BufferedInputStream(bais);
        try {
            // Set markpos < 0 to simulate invalid mark
            BufferedInputStream testStream = new BufferedInputStream(bais) {
                {
                    markpos = -1;
                }
            };
            testStream.reset();
            throw new AssertionError("Expected IOException for invalid mark not thrown");
        } catch (IOException e) {
            if (!"Resetting to invalid mark".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
