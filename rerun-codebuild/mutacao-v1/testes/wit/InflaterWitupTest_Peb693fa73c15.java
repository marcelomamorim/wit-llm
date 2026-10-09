/* @test    @summary Testa o método setInput(ByteBuffer) de java.util.zip.Inflater    @run main InflaterWitupTest_Peb693fa73c15 */

import java.nio.ByteBuffer;

public class InflaterWitupTest_Peb693fa73c15 {

    public static void main(String[] args) {
        testSetInputWithNullBuffer();
        testSetInputWithValidBuffer();
    }

    private static void testSetInputWithNullBuffer() {
        java.util.zip.Inflater inflater = new java.util.zip.Inflater();
        boolean exceptionThrown = false;
        try {
            inflater.setInput((ByteBuffer) null);
        } catch (NullPointerException npe) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when input is null");
        }
    }

    private static void testSetInputWithValidBuffer() {
        java.util.zip.Inflater inflater = new java.util.zip.Inflater();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        try {
            inflater.setInput(buffer);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid ByteBuffer", e);
        }
    }
}
