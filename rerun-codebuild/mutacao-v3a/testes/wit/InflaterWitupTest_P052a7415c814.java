/* @test    @summary Testa o método setInput(ByteBuffer) de java.util.zip.Inflater    @run main InflaterWitupTest_P052a7415c814 */

import java.nio.ByteBuffer;

public class InflaterWitupTest_P052a7415c814 {

    public static void main(String[] args) {
        testSetInputWithNullBuffer();
        testSetInputWithValidBuffer();
    }

    private static void testSetInputWithNullBuffer() {
        java.util.zip.Inflater inflater = new java.util.zip.Inflater();
        try {
            inflater.setInput((ByteBuffer) null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception thrown: " + t);
        }
    }

    private static void testSetInputWithValidBuffer() {
        java.util.zip.Inflater inflater = new java.util.zip.Inflater();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        try {
            inflater.setInput(buffer);
            System.out.println("PASS: setInput with valid ByteBuffer executed without exception");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception thrown: " + t);
        }
    }
}
