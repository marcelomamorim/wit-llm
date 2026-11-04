/*
 * @test
 * @summary Test Properties.add(Map.Entry) throws UnsupportedOperationException
 * @run main PropertiesWitupTest
 */

import java.util.Map;
import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();
        Map.Entry<Object, Object> entry = new Map.Entry<Object, Object>() {
            public Object getKey() { return "key"; }
            public Object getValue() { return "value"; }
            public Object setValue(Object value) { throw new UnsupportedOperationException(); }
        };

        try {
            props.add(entry);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
