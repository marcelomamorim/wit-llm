/* @test
   @summary Testa o método remove() da classe java.util.Scanner.
   @run main ScannerWitupTest_P7dacb7038c2f
 */
import java.util.*;
public class ScannerWitupTest_P7dacb7038c2f {
    public static void main(String[] args) {
        Scanner scanner = new Scanner("exemplo");
        try {
            scanner.remove();
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // esperado
        }
    }
}
