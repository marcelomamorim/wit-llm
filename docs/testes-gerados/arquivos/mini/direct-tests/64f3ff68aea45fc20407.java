/*
 * @test
 * @summary Test that Collections.removeIf throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        try {
            Collections.removeIf(new Predicate<Object>() {
                public boolean test(Object o) {
                    return true;
                }
            });
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
