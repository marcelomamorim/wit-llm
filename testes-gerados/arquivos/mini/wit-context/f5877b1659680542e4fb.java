/*
 * @test
 * @summary Test java.util.Collections.EmptyList.replaceAll(UnaryOperator) for NPE on null operator
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullOperator();
    }

    private static void testReplaceAllNullOperator() {
        Collections.EmptyList<String> emptyList = (Collections.EmptyList<String>) Collections.emptyList();
        try {
            emptyList.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
