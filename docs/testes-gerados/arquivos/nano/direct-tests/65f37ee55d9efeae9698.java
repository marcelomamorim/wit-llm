/* @test    @summary Test add method of Properties class that throws UnsupportedOperationException    @run main PropertiesWitupTest */

import java.util.Properties;
import java.util.Map;
import java.util.AbstractMap;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testAddMethodThrowsException();
    }

    public static void testAddMethodThrowsException() {
        Properties props = new Properties();
        Map.Entry<Object, Object> entry = new AbstractMap.SimpleEntry<>("key", "value");
        try {
            props.add(entry);
            // If no exception, test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
    }
}
