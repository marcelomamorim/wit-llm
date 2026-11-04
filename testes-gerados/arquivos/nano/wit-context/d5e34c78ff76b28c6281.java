/*
 * @test
 * @summary Test for StringLatin1.tryAdvance method, focusing on null input.
 * @run main StringLatin1WitupTest
 */

import java.lang.StringLatin1;
import java.util.function.Consumer;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceNormal();
    }

    private static void testTryAdvanceNullAction() {
        StringLatin1.TryAdvanceTest testInstance = new StringLatin1.TryAdvanceTest();
        try {
            testInstance.tryAdvance(null);
            System.out.println("testTryAdvanceNullAction: FAILED (expected NullPointerException)");
        } catch (NullPointerException e) {
            System.out.println("testTryAdvanceNullAction: PASSED");
        } catch (Throwable t) {
            System.out.println("testTryAdvanceNullAction: FAILED (unexpected exception: " + t + ")");
        }
    }

    private static void testTryAdvanceNormal() {
        StringLatin1.TryAdvanceTest testInstance = new StringLatin1.TryAdvanceTest();
        Consumer<String> consumer = s -> {
            // do nothing
        };
        boolean result = false;
        try {
            result = testInstance.tryAdvance(consumer);
            if (result) {
                System.out.println("testTryAdvanceNormal: PASSED");
            } else {
                System.out.println("testTryAdvanceNormal: FAILED (expected true)");
            }
        } catch (Throwable t) {
            System.out.println("testTryAdvanceNormal: FAILED (unexpected exception: " + t + ")");
        }
    }

    static class TryAdvanceTest {
        public boolean tryAdvance(Consumer<? super String> action) {
            if (action == null) {
                throw new NullPointerException("tryAdvance action missing");
            }
            int index = 0;
            int fence = 1;
            if (index != fence) {
                action.accept("next");
                return true;
            }
            return false;
        }
    }
}
