/*
 * @test
 * @summary Test getDeclaringClass() throws UnsupportedOperationException if retainClassRef flag is not set
 * @run main StackFrameInfoWitupTest
 */

import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.lang.StackFrame;

public class StackFrameInfoWitupTest {
    public static void main(String[] args) {
        // Create a StackWalker with retainClassRef = false to trigger exception
        StackWalker walker = StackWalker.getInstance();
        // The StackFrameInfo constructor is package-private, so we cannot instantiate it directly.
        // Instead, we test getDeclaringClass() indirectly via a subclass or reflection is disallowed.
        // However, since StackFrameInfo is package-private and no public constructor,
        // we cannot instantiate it directly here.
        // So we test the behavior by creating a StackFrameInfo via reflection is disallowed,
        // so we test the behavior by creating a StackWalker and getting a StackFrame.

        // We create a StackFrameInfo instance via the StackWalker API is not possible directly,
        // so we test the behavior of getDeclaringClass() indirectly by creating a StackFrameInfo
        // subclass inside this test class in the same package.

        // Since the class is package-private in java.lang, and this test is in unnamed package,
        // we cannot subclass it here.

        // Therefore, we test the behavior by creating a StackWalker with retainClassRef false,
        // and then get a StackFrame and call getDeclaringClass() to check if it throws.

        StackWalker sw = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
        // The above enables retainClassRef true, so getDeclaringClass() should not throw.
        // We test that it returns a non-null class.
        StackFrame frame = sw.walk(s -> s.findFirst().get());
        Class<?> cls = frame.getDeclaringClass();
        if (cls == null) {
            throw new AssertionError("getDeclaringClass() returned null when retainClassRef is true");
        }

        // Now test with retainClassRef false
        StackWalker swNoRetain = StackWalker.getInstance();
        StackFrame frameNoRetain = swNoRetain.walk(s -> s.findFirst().get());
        try {
            frameNoRetain.getDeclaringClass();
            throw new AssertionError("Expected UnsupportedOperationException when retainClassRef is false");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        System.out.println("Test passed");
    }
}
