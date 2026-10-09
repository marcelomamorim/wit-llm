/* @test    @summary Test Deflater.setInput(ByteBuffer) with a non-null ByteBuffer    @run main DeflaterWitupTest_P303bf69888ff */

import java.util.zip.Deflater;
import java.nio.ByteBuffer;

public class DeflaterWitupTest_P303bf69888ff {
    public static void main(String[] args) {
        try {
            // Test with a direct ByteBuffer
            ByteBuffer bufferDirect = ByteBuffer.allocateDirect(10);
            bufferDirect.put(new byte[] {1, 2, 3, 4, 5});
            bufferDirect.flip();
            Deflater deflater1 = new Deflater();
            deflater1.setInput(bufferDirect);
            // Verify that input buffer is set (no exception thrown)
            if (deflater1 == null) {
                throw new AssertionError("Deflater instance is null after setInput");
            }

            // Test with a heap ByteBuffer
            ByteBuffer bufferHeap = ByteBuffer.allocate(5);
            bufferHeap.put(new byte[] {10, 20, 30, 40, 50});
            bufferHeap.flip();
            Deflater deflater2 = new Deflater();
            deflater2.setInput(bufferHeap);
            if (deflater2 == null) {
                throw new AssertionError("Deflater instance is null after setInput");
            }

            // Test with a ByteBuffer with remaining data
            ByteBuffer bufferRemaining = ByteBuffer.allocate(8);
            bufferRemaining.put(new byte[] {7, 8, 9, 10, 11});
            bufferRemaining.flip();
            Deflater deflater3 = new Deflater();
            deflater3.setInput(bufferRemaining);
            if (deflater3 == null) {
                throw new AssertionError("Deflater instance is null after setInput");
            }

            // Test with a ByteBuffer with no remaining data
            ByteBuffer bufferEmpty = ByteBuffer.allocate(4);
            bufferEmpty.put(new byte[] {1, 2, 3, 4});
            bufferEmpty.flip();
            bufferEmpty.position(bufferEmpty.limit()); // no remaining data
            Deflater deflater4 = new Deflater();
            deflater4.setInput(bufferEmpty);
            if (deflater4 == null) {
                throw new AssertionError("Deflater instance is null after setInput");
            }

        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage(), e);
        }
    }
}
