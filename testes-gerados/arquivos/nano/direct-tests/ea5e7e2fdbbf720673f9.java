/*
 * @test
 * @summary Test the remove method of java.util.Collections that throws UnsupportedOperationException
 * @run main CollectionsRemoveTest
 */

import java.util.Collections;

public class CollectionsRemoveTest {
    public static void main(String[] args) {
        Object key = new Object();
        Object value = new Object();
        boolean exceptionThrown = false;
        try {
            Collections.remove(key, value);
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
