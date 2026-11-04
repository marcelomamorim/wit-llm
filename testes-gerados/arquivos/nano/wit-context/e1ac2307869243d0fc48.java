/*
 * @test
 * @summary Test createFilter with null pattern to verify NullPointerException
 * @run main ObjectInputFilterWitupTest
 */

import java.io.ObjectInputFilter;

public class ObjectInputFilterWitupTest {

    public static void main(String[] args) {
        testCreateFilterNullPattern();
    }

    public static void testCreateFilterNullPattern() {
        try {
            ObjectInputFilter.createFilter(null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
            System.out.println("NullPointerException caught as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
