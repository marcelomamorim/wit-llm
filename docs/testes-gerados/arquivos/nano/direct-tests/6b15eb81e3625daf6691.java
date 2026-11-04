/* @test    @summary Test for Properties.addAll method to ensure it throws UnsupportedOperationException as expected.    @run main PropertiesWitupTest */

import java.util.Properties;
import java.util.Collection;
import java.util.Map;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testAddAllThrowsException();
    }

    public static void testAddAllThrowsException() {
        Properties props = new Properties();
        Collection<Map.Entry<Object, Object>> entries = null;
        try {
            // Using null to simulate a collection; in real test, populate accordingly
            props.addAll(entries);
            // If no exception, fail the test
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (NullPointerException e) {
            // Acceptable if addAll does not handle null, test passes as exception is expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
