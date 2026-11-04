/*
 * @test
 * @summary Test the add method of Hashtable to ensure it correctly delegates to super.add.
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.Map;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testAddReturnsExpectedResult();
        testAddWithNullEntry();
    }

    private static void testAddReturnsExpectedResult() {
        Hashtable<String, String> table = new Hashtable<>();
        Map.Entry<String, String> entry = new Map.Entry<String, String>() {
            @Override
            public String getKey() {
                return "key";
            }

            @Override
            public String getValue() {
                return "value";
            }

            @Override
            public String setValue(String value) {
                throw new UnsupportedOperationException();
            }
        };
        boolean result = table.add(entry);
        assert result : "add should return true for new entry";
        String retrieved = table.get("key");
        assert "value".equals(retrieved) : "Value should be stored in the hashtable";
    }

    private static void testAddWithNullEntry() {
        Hashtable<String, String> table = new Hashtable<>();
        try {
            table.add(null);
            assert false : "Adding null should throw NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
