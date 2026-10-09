/* @test
   @summary Testa o método getInstance(Set<Option> options, int estimateDepth) de StackWalker.
   @run main StackWalkerWitupTest_P5091c75e7c48
 */
import java.util.EnumSet;
import java.util.Set;
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;
import java.lang.StackWalker;

public class StackWalkerWitupTest_P5091c75e7c48 {
    public static void main(String[] args) {
        testGetInstanceWithZeroEstimateDepth();
        testGetInstanceWithNullOptions();
    }

    private static void testGetInstanceWithZeroEstimateDepth() {
        try {
            StackWalker.getInstance(null, 0);
            throw new AssertionError("Expected IllegalArgumentException for estimateDepth=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testGetInstanceWithNullOptions() {
        try {
            StackWalker.getInstance(null, 1);
            // Deve passar, pois null options é permitido, assumindo implementação padrão
        } catch (NullPointerException e) {
            throw new AssertionError("Unexpected NullPointerException when options is null");
        }
    }
}
