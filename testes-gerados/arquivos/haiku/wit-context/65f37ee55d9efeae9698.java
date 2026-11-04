/*
 * @test
 * @summary Verifies that adding to the entry set view of Properties throws UnsupportedOperationException and leaves the table unchanged
 * @run main PropertiesWitupTest
 */
import java.util.AbstractMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        checkAddThrowsAndLeavesEmpty(new Properties());
        checkAddThrowsAndLeavesEmpty(new Properties(16));
        checkAddThrowsAndLeavesEmpty(new Properties(new Properties()));
        checkAddDoesNotExposeEntry();
    }

    private static void checkAddThrowsAndLeavesEmpty(Properties props) {
        Map.Entry<Object, Object> entry = new AbstractMap.SimpleEntry<Object, Object>("k", "v");
        boolean thrown = false;
        try {
            props.entrySet().add(entry);
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from entrySet().add");
        }
        if (!props.isEmpty()) {
            throw new RuntimeException("Properties must remain empty after failed add");
        }
        if (props.getProperty("k") != null) {
            throw new RuntimeException("Key must not be present after failed add");
        }
    }

    private static void checkAddDoesNotExposeEntry() {
        Properties props = new Properties();
        props.setProperty("existing", "1");
        Map.Entry<Object, Object> entry = new AbstractMap.SimpleEntry<Object, Object>("k", "v");
        boolean thrown = false;
        try {
            props.entrySet().add(entry);
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from entrySet().add");
        }
        if (props.size() != 1 || !"1".equals(props.getProperty("existing"))) {
            throw new RuntimeException("Existing contents must be unchanged after failed add");
        }
        if (props.getProperty("k") != null) {
            throw new RuntimeException("Added entry must not be visible");
        }
    }
}
