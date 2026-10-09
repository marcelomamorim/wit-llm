/* @test    @summary Test for Inflater.setInput(ByteBuffer) handling null input    @run main InflaterWitupTest_Pa228ea0a2ba4 */

import java.util.zip.Inflater;
import java.nio.ByteBuffer;

public class InflaterWitupTest_Pa228ea0a2ba4 {

    public static void main(String[] args) {
        testSetInputWithNullBuffer();
    }

    public static void testSetInputWithNullBuffer() {
        Inflater inflater = new Inflater();
        try {
            inflater.setInput((ByteBuffer) null);
            // Se nenhuma exceção for lançada, o teste falha
            System.err.println("Failed: Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Exceção esperada, teste passa
            System.out.println("Passed: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            // Qualquer outra exceção indica falha
            System.err.println("Failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
