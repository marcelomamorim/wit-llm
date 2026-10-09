/* @test
   @summary Tests the put method of java.util.Hashtable.
   @run main HashtableWitupTest_P2fd8f6cd8cd3
 */
import java.util.Hashtable;
import java.util.Objects;

public class HashtableWitupTest_P2fd8f6cd8cd3 {
    public static void main(String[] args) {
        testPutWithValidKeyAndValue();
        testPutWithNullValueThrows();
        testPutWithExistingKeyReplacesValue();
    }

    private static void testPutWithValidKeyAndValue() {
        Hashtable<String, Integer> table = new Hashtable<>();
        Integer previous = table.put("one", 1);
        assert previous == null : "Expected null for new key";
        Integer retrieved = table.get("one");
        assert retrieved != null && retrieved == 1 : "Value not correctly inserted";
    }

    private static void testPutWithNullValueThrows() {
        Hashtable<String, String> table = new Hashtable<>();
        boolean threw = false;
        try {
            table.put("nullValue", null);
        } catch (NullPointerException e) {
            threw = true;
        }
        assert threw : "Expected NullPointerException when putting null value";
    }

    private static void testPutWithExistingKeyReplacesValue() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key", "initial");
        String oldValue = table.put("key", "replaced");
        assert oldValue.equals("initial") : "Old value should be 'initial'";
        String newValue = table.get("key");
        assert newValue.equals("replaced") : "New value should be 'replaced'";
    }
}
