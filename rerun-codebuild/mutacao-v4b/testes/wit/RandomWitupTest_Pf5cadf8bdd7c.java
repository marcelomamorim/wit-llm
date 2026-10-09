/* @test
   @summary Verifica comportamento do método nextInt com entrada inválida.
   @run main RandomWitupTest_Pf5cadf8bdd7c
 */
import java.util.Random;
public class RandomWitupTest_Pf5cadf8bdd7c {
    public static void main(String[] args) {
        Random rand = new Random();
        try {
            rand.nextInt(0);
            throw new AssertionError("Expected IllegalArgumentException for bound=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            rand.nextInt(-5);
            throw new AssertionError("Expected IllegalArgumentException for bound=-5");
        } catch (IllegalArgumentException e) {
            // expected
        }
        // Test com valor válido para garantir comportamento normal
        int result = rand.nextInt(1);
        if (result != 0) {
            throw new AssertionError("Expected 0 for nextInt(1), got: " + result);
        }
        result = rand.nextInt(10);
        if (result < 0 || result >= 10) {
            throw new AssertionError("Result out of range: " + result);
        }
    }
}
