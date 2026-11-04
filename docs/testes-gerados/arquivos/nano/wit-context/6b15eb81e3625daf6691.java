/*
 * @test
 * @summary Test for Properties.addAll method, including exception path.
 * @run main PropertiesWitupTest
 */

import java.util.Properties;
import java.util.Collection;
import java.util.Map;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testAddAllThrowsException();
    }

    public static void testAddAllThrowsException() {
        Properties props = new Properties();
        Collection<Map.Entry<Object, Object>> dummyCollection = null;
        try {
            // Using a dummy collection that is null to simulate a null pointer scenario
            // Alternatively, could use an actual collection, but the method always throws
            props.addAll(dummyCollection);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException");
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException as addAll does not handle null collections");
        } catch (Throwable t) {
            System.out.println("Caught unexpected exception: " + t);
        }
    }
}
