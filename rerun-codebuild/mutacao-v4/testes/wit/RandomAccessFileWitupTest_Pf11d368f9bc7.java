/* @test
   @summary Verifies seek method throws IOException on negative position.
   @run main RandomAccessFileWitupTest_Pf11d368f9bc7
 */
import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest_Pf11d368f9bc7 {
    public static void main(String[] args) throws Exception {
        testSeekNegative();
    }

    private static void testSeekNegative() {
        try {
            // Cria um arquivo temporário para teste
            String filename = "tempfile.txt";
            RandomAccessFile raf = new RandomAccessFile(filename, "rw");
            try {
                // Tenta fazer seek para uma posição negativa
                raf.seek(-1);
                throw new AssertionError("Expected IOException for negative seek");
            } catch (IOException e) {
                // Esperado: IOException devido a posição negativa
                if (!e.getMessage().contains("Negative seek offset")) {
                    throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
                }
            } finally {
                raf.close();
            }
        } catch (IOException e) {
            throw new AssertionError("Failed to create RandomAccessFile", e);
        }
    }
}
