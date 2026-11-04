/* @test    @summary Test for Frame.merge method behavior and regressions    @run main FrameWitupTest */

import java.util.Arrays;
import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

public class FrameWitupTest {

    public static void main(String[] args) {
        testMergeChangesDetected();
        testMergeNoChanges();
        testMergeWithDifferentFrames();
    }

    static void testMergeChangesDetected() {
        Frame<Object> frame1 = new Frame<>(3, 2);
        Frame<Object> frame2 = new Frame<>(3, 2);
        boolean[] access = {true, true, true, true, true};
        frame1.values = new Object[] {"a", "b", "c"};
        frame2.values = new Object[] {"a", "b", "d"};
        boolean result = frame1.merge(frame2, access);
        assert result : "Expected merge to detect changes";
        assert "d".equals(frame1.values[2]) : "Expected value at index 2 to be updated to "d"";
    }

    static void testMergeNoChanges() {
        Frame<Object> frame1 = new Frame<>(2, 2);
        Frame<Object> frame2 = new Frame<>(2, 2);
        boolean[] access = {true, true, true, true};
        frame1.values = new Object[] {"x", "y"};
        frame2.values = new Object[] {"x", "y"};
        boolean result = frame1.merge(frame2, access);
        assert !result : "Expected merge to detect no changes";
        assert "x".equals(frame1.values[0]) : "Value at index 0 should remain 'x'";
        assert "y".equals(frame1.values[1]) : "Value at index 1 should remain 'y'";
    }

    static void testMergeWithDifferentFrames() {
        Frame<Object> frame1 = new Frame<>(4, 2);
        Frame<Object> frame2 = new Frame<>(4, 2);
        boolean[] access = {true, false, true, false, true, false};
        frame1.values = new Object[] {"a", "b", "c", "d"};
        frame2.values = new Object[] {"a", "x", "c", "y"};
        boolean result = frame1.merge(frame2, access);
        assert result : "Expected merge to detect changes with selective access";
        assert "a".equals(frame1.values[0]) : "Index 0 should remain 'a'";
        assert "c".equals(frame1.values[2]) : "Index 2 should remain 'c'";
        assert "x".equals(frame1.values[1]) : "Index 1 should be updated to 'x'";
        assert "y".equals(frame1.values[3]) : "Index 3 should be updated to 'y'";
    }
}
