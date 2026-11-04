/*
 * @test    @summary Test synchronizedCollection method for null and non-null input
 * @run main CollectionsWitupTest
 */

import java.util.Collection;
import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullInput();
        testValidInput();
    }

    static void testNullInput() {
        try {
            Collection<?> result = Collections.synchronizedCollection(null);
            // Expecting a NullPointerException
            assert false : "Expected NullPointerException for null input";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testValidInput() {
        Collection<Integer> original = java.util.Arrays.asList(1, 2, 3);
        Collection<Integer> result = Collections.synchronizedCollection(original);
        assert result != null : "Result should not be null";
        assert result.contains(1) : "Result should contain 1";
        assert result.contains(2) : "Result should contain 2";
        assert result.contains(3) : "Result should contain 3";
        // Verify that the returned collection is a SynchronizedCollection instance
        assert result.getClass().getName().contains("SynchronizedCollection") : "Result should be an instance of SynchronizedCollection";
    }
}
