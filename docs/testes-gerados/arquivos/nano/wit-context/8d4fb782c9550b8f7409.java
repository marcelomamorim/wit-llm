/*
 * @test
 * @summary Test for Collections.replaceAll with null function
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.BiFunction;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceAllWithNullFunction();
    }

    public static void testReplaceAllWithNullFunction() {
        try {
            Collections.replaceAll((BiFunction<?, ?, ?>) null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
