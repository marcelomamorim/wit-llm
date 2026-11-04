/*
 * @test
 * @summary Tests the merge method of Frame class for exception path analysis.
 * @run main FrameWitupTest
 */

import java.util.Arrays;
import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

public class FrameWitupTest {

    public static void main(String[] args) {
        testMergeDifferentValues();
        testMergeSameValues();
        testMergeWithNullValues();
    }

    static void testMergeDifferentValues() {
        Frame<Object> frame1 = new Frame<>(2, 2);
        Frame<Object> frame2 = new Frame<>(2, 2);
        // Initialize values with different objects
        frame1.values = new Object[] {"a", "b"};
        frame2.values = new Object[] {"x", "b"};
        boolean[] access = {true, false};
        boolean result = frame1.merge(frame2, access);
        if (!result) {
            throw new AssertionError("Expected merge to return true when values differ.");
        }
        if (!"x".equals(frame1.values[0])) {
            throw new AssertionError("Expected first value to be updated to 'x'.");
        }
        if (!"b".equals(frame1.values[1])) {
            throw new AssertionError("Second value should remain 'b'.");
        }
    }

    static void testMergeSameValues() {
        Frame<Object> frame1 = new Frame<>(2, 2);
        Frame<Object> frame2 = new Frame<>(2, 2);
        Object val = "constant";
        frame1.values = new Object[] {val, val};
        frame2.values = new Object[] {val, val};
        boolean[] access = {true, true};
        boolean result = frame1.merge(frame2, access);
        if (result) {
            throw new AssertionError("Expected merge to return false when values are identical.");
        }
        if (frame1.values[0] != val || frame1.values[1] != val) {
            throw new AssertionError("Values should remain unchanged.");
        }
    }

    static void testMergeWithNullValues() {
        Frame<Object> frame1 = new Frame<>(2, 2);
        Frame<Object> frame2 = new Frame<>(2, 2);
        frame1.values = new Object[] {null, "foo"};
        frame2.values = new Object[] {null, "bar"};
        boolean[] access = {true, true};
        boolean result = frame1.merge(frame2, access);
        if (!result) {
            throw new AssertionError("Expected merge to return true when nulls are replaced.");
        }
        if (frame1.values[0] != null) {
            throw new AssertionError("First value should remain null.");
        }
        if (!"bar".equals(frame1.values[1])) {
            throw new AssertionError("Second value should be updated to 'bar'.");
        }
    }
}
