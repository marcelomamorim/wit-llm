/* @test    @summary Testa o método setInput(ByteBuffer) do Deflater para entrada nula.
 * @run main DeflaterWitupTest_P303bf69888ff
 */

import java.util.zip.Deflater;
import java.nio.ByteBuffer;

public class DeflaterWitupTest_P303bf69888ff {

    public static void main(String[] args) {
        testSetInputNull();
    }

    public static void testSetInputNull() {
        Deflater deflater = new Deflater();
        try {
            deflater.setInput((ByteBuffer) null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Test passou, exceção esperada
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
