/*
 * @test
 * @summary Test java.util.zip.Inflater.setInput(ByteBuffer) method
 * @run main InflaterWitupTest
 */

import java.util.zip.Inflater;
import java.nio.ByteBuffer;
import java.util.Objects;

public class InflaterWitupTest {
    public static void main(String[] args) throws Exception {
        testSetInputWithNonNullByteBuffer();
        testSetInputWithNullByteBuffer();
    }

    private static void testSetInputWithNonNullByteBuffer() {
        Inflater inflater = new Inflater();
        byte[] data = new byte[] {1, 2, 3, 4};
        ByteBuffer buffer = ByteBuffer.wrap(data);

        inflater.setInput(buffer);

        // No direct getter for input, but no exception means success
        // We test that calling setInput again with another buffer also works
        ByteBuffer buffer2 = ByteBuffer.allocate(10);
        inflater.setInput(buffer2);
    }

    private static void testSetInputWithNullByteBuffer() {
        Inflater inflater = new Inflater();
        try {
            inflater.setInput(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        }
    }
}
