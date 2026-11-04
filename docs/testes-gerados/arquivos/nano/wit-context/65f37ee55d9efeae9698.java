/*
 * @test
 * @summary Test for Properties.add method to verify UnsupportedOperationException is thrown
 * @run main PropertiesWitupTest
 */

import java.util.Properties;
import java.util.Map;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testAddMethodThrowsException();
    }

    public static void testAddMethodThrowsException() {
        Properties props = new Properties();
        Map.Entry<Object, Object> entry = new Map.Entry<Object, Object>() {
            @Override
            public Object getKey() {
                return "key";
            }

            @Override
            public Object getValue() {
                return "value";
            }

            @Override
            public Object setValue(Object value) {
                throw new UnsupportedOperationException();
            }
        };
        try {
            props.add(entry);
            // If no exception, test fails
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("PASS: UnsupportedOperationException was correctly thrown.");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }
}
