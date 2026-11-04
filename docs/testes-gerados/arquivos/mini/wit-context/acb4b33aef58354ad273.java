/*
 * @test
 * @summary Test Collections.SingletonList.removeIf throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSingletonListRemoveIfThrows();
    }

    private static void testSingletonListRemoveIfThrows() {
        java.util.List<String> singletonList = Collections.singletonList("test");
        Predicate<String> alwaysTrue = new Predicate<String>() {
            public boolean test(String s) {
                return true;
            }
        };

        boolean thrown = false;
        try {
            singletonList.removeIf(alwaysTrue);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown by removeIf on SingletonList");
        }
    }
}
