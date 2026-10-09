/* @test
   @summary Test the contains method of Hashtable for existing and non-existing values.
   @run main HashtableWitupTest_P2a76941a6755
 */
import java.util.Hashtable;
public class HashtableWitupTest_P2a76941a6755 {
    public static void main(String[] args) {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        table.put("key2", "value2");
        
        // Test for a value that exists
        boolean containsValue1 = table.contains("value1");
        if (!containsValue1) {
            throw new AssertionError("Expected to find value1 in the hashtable");
        }
        
        // Test for a value that does not exist
        boolean containsValue3 = table.contains("value3");
        if (containsValue3) {
            throw new AssertionError("Did not expect to find value3 in the hashtable");
        }
        
        // Test for null value, should throw NullPointerException
        try {
            table.contains(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
