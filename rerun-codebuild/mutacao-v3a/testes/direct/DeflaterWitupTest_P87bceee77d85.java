/* @test    @summary Test Deflater.setInput(ByteBuffer) with non-null ByteBuffer    @run main DeflaterWitupTest_P87bceee77d85 */

import java.util.zip.Deflater;
import java.nio.ByteBuffer;

public class DeflaterWitupTest_P87bceee77d85 {

    public static void main(String[] args) {
        testSetInputWithNonNullByteBuffer();
    }

    public static void testSetInputWithNonNullByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        try {
            Deflater deflater = new Deflater();
            deflater.setInput(buffer);
            System.out.println("setInput(ByteBuffer) executed successfully with non-null ByteBuffer.");
        } catch (Exception e) {
            throw new AssertionError("setInput(ByteBuffer) threw an exception with non-null ByteBuffer", e);
        }
    }
}
