/*
 * @test
 * @summary Test Properties.add(Map.Entry) throws UnsupportedOperationException
 * @run main java.util.PropertiesWitupTest
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

        boolean thrown = false;
        try {
            props.add(entry);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException not thrown by Properties.add");
        }
    }
}
