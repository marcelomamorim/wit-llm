/* @test    @summary Test Inflater.setInput(ByteBuffer) with non-null ByteBuffer    @run main InflaterWitupTest_Peb693fa73c15 */

import java.util.zip.Inflater;
import java.nio.ByteBuffer;

public class InflaterWitupTest_Peb693fa73c15 {

    public static void main(String[] args) {
        testSetInputWithValidByteBuffer();
        testSetInputWithNullByteBuffer();
    }

    private static void testSetInputWithValidByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        Inflater inflater = new Inflater();
        try {
            inflater.setInput(buffer);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid ByteBuffer", e);
        }
    }

    private static void testSetInputWithNullByteBuffer() {
        try {
            Inflater inflater = new Inflater();
            inflater.setInput(null);
            throw new AssertionError("Expected NullPointerException for null ByteBuffer");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
