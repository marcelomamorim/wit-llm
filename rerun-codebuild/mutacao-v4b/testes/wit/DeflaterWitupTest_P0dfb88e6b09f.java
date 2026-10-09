/* @test
   @summary Testa o método setInput(ByteBuffer) do Deflater para comportamento esperado.
   @run main DeflaterWitupTest_P0dfb88e6b09f
 */
import java.util.zip.Deflater;
import java.nio.ByteBuffer;
public class DeflaterWitupTest_P0dfb88e6b09f {
    public static void main(String[] args) {
        testSetInputWithNull();
        testSetInputWithValidBuffer();
    }
    private static void testSetInputWithNull() {
        Deflater deflater = new Deflater();
        try {
            deflater.setInput((ByteBuffer) null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // Test passed
        }
    }
    private static void testSetInputWithValidBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        Deflater deflater = new Deflater();
        deflater.setInput(buffer);
        // Verifica se o buffer foi atribuído corretamente
        // Como o campo input é privado, confiamos na execução sem exceções
        // e na ausência de efeitos colaterais visíveis
    }
}
