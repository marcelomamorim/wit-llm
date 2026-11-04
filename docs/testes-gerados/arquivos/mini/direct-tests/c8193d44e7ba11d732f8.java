/*
 * @test
 * @summary Test that Collections.add(Map.Entry) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map.Entry<String, String> entry = new Map.Entry<String, String>() {
            public String getKey() { return "key"; }
            public String getValue() { return "value"; }
            public String setValue(String value) { throw new UnsupportedOperationException(); }
        };

        try {
            Collections.add(entry);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
