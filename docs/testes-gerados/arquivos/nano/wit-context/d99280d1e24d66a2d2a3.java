/* @test    @summary Test for Hashtable.add method to verify exception behavior.    @run main HashtableWitupTest */

import java.util.Hashtable;
import java.util.Map;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testAddThrowsUnsupportedOperationException();
    }

    private static void testAddThrowsUnsupportedOperationException() {
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
        try {
            table.add(entry);
            // If no exception, test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
    }
}
