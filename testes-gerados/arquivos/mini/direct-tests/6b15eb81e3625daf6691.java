/*
 * @test
 * @summary Test Properties.addAll throws UnsupportedOperationException
 * @run main java.util.PropertiesWitupTest
 */

import java.util.Properties;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();
        Collection<Map.Entry<Object, Object>> entries = new ArrayList<>();
        entries.add(new SimpleEntry<Object, Object>("key", "value"));
        try {
            props.addAll(entries);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
