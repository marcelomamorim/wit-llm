/*
 * @test
 * @summary Test the push method of Frame class for correct behavior.
 * @run main FrameWitupTest
 */

import java.util.Arrays;
import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

public class FrameWitupTest {

    public static void main(String[] args) {
        testPushNormal();
        testPushOverflow();
    }

    static void testPushNormal() {
        Frame<Object> frame = new Frame<>(5, 5);
        try {
            frame.push("value1");
            frame.push("value2");
            if (frame.values[0] == "value1" && frame.values[1] == "value2") {
                System.out.println("testPushNormal passed");
            } else {
                System.out.println("testPushNormal failed: values not stored correctly");
            }
        } catch (Exception e) {
            System.out.println("testPushNormal failed with exception: " + e);
        }
    }

    static void testPushOverflow() {
        Frame<Object> frame = new Frame<>(2, 2);
        try {
            frame.push("value1");
            frame.push("value2");
            boolean exceptionThrown = false;
            try {
                frame.push("value3");
            } catch (IndexOutOfBoundsException e) {
                exceptionThrown = true;
            }
            if (exceptionThrown) {
                System.out.println("testPushOverflow passed");
            } else {
                System.out.println("testPushOverflow failed: exception not thrown");
            }
        } catch (Exception e) {
            System.out.println("testPushOverflow failed with exception: " + e);
        }
    }
}
