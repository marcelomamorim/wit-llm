/*
 * @test
 * @summary Verifies StackWalker.forEach rejects a null action and visits frames of the calling thread
 * @run main StackWalkerWitupTest
 */

public class StackWalkerWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testForEachVisitsCurrentClassFrame();
        testForEachVisitsAtLeastOneFrame();
    }

    static void testNullActionThrowsNPE() {
        StackWalker sw = StackWalker.getInstance();
        boolean thrown = false;
        try {
            sw.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null action");
        }
    }

    static void testForEachVisitsCurrentClassFrame() {
        StackWalker sw = StackWalker.getInstance();
        final boolean[] found = {false};
        final String testClassName = StackWalkerWitupTest.class.getName();
        sw.forEach(f -> {
            if (testClassName.equals(f.getClassName())) {
                found[0] = true;
            }
        });
        if (!found[0]) {
            throw new RuntimeException("expected a frame from " + testClassName);
        }
    }

    static void testForEachVisitsAtLeastOneFrame() {
        StackWalker sw = StackWalker.getInstance();
        final int[] count = {0};
        sw.forEach(f -> count[0]++);
        if (count[0] == 0) {
            throw new RuntimeException("expected at least one stack frame");
        }
    }
}
