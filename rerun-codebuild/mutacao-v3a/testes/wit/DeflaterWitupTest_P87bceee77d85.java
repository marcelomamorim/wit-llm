/* @test    @summary Test for Deflater.setInput(ByteBuffer) method handling null input    @run main DeflaterWitupTest_P87bceee77d85 */

import java.nio.ByteBuffer;

public class DeflaterWitupTest_P87bceee77d85 {

    public static void main(String[] args) {
        testSetInputWithNullBuffer();
    }

    public static void testSetInputWithNullBuffer() {
        java.util.zip.Deflater deflater = new java.util.zip.Deflater();
        try {
            deflater.setInput((ByteBuffer) null);
            // If no exception, test fails
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
