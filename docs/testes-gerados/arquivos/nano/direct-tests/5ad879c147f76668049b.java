/* @test    @summary Test the Properties.put method for basic key-value insertion and overwrite behavior.    @run main PropertiesWitupTest */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testPutNewEntry();
        testOverwriteEntry();
    }

    private static void testPutNewEntry() {
        Properties props = new Properties();
        Object previous = props.put("key1", "value1");
        assert previous == null : "Expected null for new key, but got: " + previous;
        Object retrieved = props.get("key1");
        assert "value1".equals(retrieved) : "Expected 'value1', but got: " + retrieved;
    }

    private static void testOverwriteEntry() {
        Properties props = new Properties();
        props.put("key2", "initial");
        Object previous = props.put("key2", "overwritten");
        assert "initial" == previous : "Expected 'initial' as previous value, but got: " + previous;
        Object retrieved = props.get("key2");
        assert "overwritten".equals(retrieved) : "Expected 'overwritten', but got: " + retrieved;
    }
}
