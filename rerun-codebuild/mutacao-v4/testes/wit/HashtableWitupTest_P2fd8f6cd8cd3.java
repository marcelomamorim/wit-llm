/* @test
   @summary Verifica comportamento do método put em Hashtable, especialmente quando o valor é null.
   @run main HashtableWitupTest_P2fd8f6cd8cd3
 */
import java.util.Hashtable;
public class HashtableWitupTest_P2fd8f6cd8cd3 {
    public static void main(String[] args) {
        Hashtable<String, String> table = new Hashtable<>();
        boolean exceptionThrown = false;
        try {
            table.put("key1", null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("NullPointerException was not thrown when putting null value");
        }
        // Testa inserção normal
        String previous = table.put("key2", "value2");
        if (previous != null) {
            throw new AssertionError("Expected previous value to be null for new key");
        }
        // Testa substituição de valor existente
        previous = table.put("key2", "value3");
        if (!"value2".equals(previous)) {
            throw new AssertionError("Previous value mismatch: expected 'value2', got '" + previous + "'");
        }
        // Verifica se o valor foi atualizado
        String current = table.get("key2");
        if (!"value3".equals(current)) {
            throw new AssertionError("Current value mismatch: expected 'value3', got '" + current + "'");
        }
    }
}
