/*
 * @test
 * @summary Test Collections.SingletonSet.removeIf throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // SingletonSet is package-private, but we can get an instance via Collections.singleton
        // The removeIf method in SingletonSet throws UnsupportedOperationException unconditionally
        java.util.Set<String> singletonSet = Collections.singleton("test");

        try {
            singletonSet.removeIf(new Predicate<String>() {
                public boolean test(String s) {
                    return true;
                }
            });
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
