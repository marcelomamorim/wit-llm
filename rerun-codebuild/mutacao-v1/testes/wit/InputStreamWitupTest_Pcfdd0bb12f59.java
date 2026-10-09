/* @test    @summary Test readNBytes with negative length to trigger IllegalArgumentException    @run main InputStreamWitupTest_Pcfdd0bb12f59 */

import java.io.InputStream;
import java.io.IOException;

public class InputStreamWitupTest_Pcfdd0bb12f59 {

    public static void main(String[] args) throws IOException {
        testReadNBytesNegativeLength();
    }

    public static void testReadNBytesNegativeLength() {
        InputStream is = new InputStream() {
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return -1; // Simulate end of stream
            }
        };
        try {
            // Use reflection to invoke the method since it's protected in the real class
            java.lang.reflect.Method method = InputStream.class.getDeclaredMethod("readNBytes", int.class);
            method.setAccessible(true);
            method.invoke(is, -1);
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type: " + cause, cause);
            }
            if (!cause.getMessage().contains("len < 0")) {
                throw new AssertionError("Exception message does not contain 'len < 0'");
            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
