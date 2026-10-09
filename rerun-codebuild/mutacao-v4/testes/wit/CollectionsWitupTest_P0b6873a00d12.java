/* @test
   @summary Testa o método Collections.nCopies para entradas válidas e inválidas.
   @run main CollectionsWitupTest_P0b6873a00d12
 */
import java.util.List;
import java.util.Collections;
public class CollectionsWitupTest_P0b6873a00d12 {
    public static void main(String[] args) {
        testNCopiesWithValidInput();
        testNCopiesWithNegativeInput();
    }
    private static void testNCopiesWithValidInput() {
        List<String> list = Collections.nCopies(3, "test");
        if (list.size() != 3) {
            throw new AssertionError("Expected size 3, got " + list.size());
        }
        for (String s : list) {
            if (!"test".equals(s)) {
                throw new AssertionError("Expected element 'test', got " + s);
            }
        }
    }
    private static void testNCopiesWithNegativeInput() {
        try {
            List<String> list = Collections.nCopies(-1, "test");
            if (list.size() != -1) {
                throw new AssertionError("Expected size -1, got " + list.size());
            }
            throw new AssertionError("Expected IllegalArgumentException for n < 0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
