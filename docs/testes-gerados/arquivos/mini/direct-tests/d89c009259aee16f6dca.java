/*
 * @test
 * @summary Test getDeclaringClass method of StackFrameInfo
 * @run main StackFrameInfoWitupTest
 */

import java.lang.StackFrameInfo;
import java.lang.StackWalker;

public class StackFrameInfoWitupTest {
    public static void main(String[] args) {
        // Create a StackWalker with retainClassRef true to enable getDeclaringClass
        StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        StackFrameInfo sfi = new StackFrameInfo(walker);

        // Call getDeclaringClass and check it returns a non-null Class
        Class<?> cls = sfi.getDeclaringClass();
        if (cls == null) {
            throw new AssertionError("getDeclaringClass returned null");
        }

        // Check that the class name matches the class returned by getClassName
        String className = sfi.getClassName();
        if (!cls.getName().equals(className)) {
            throw new AssertionError("Class name mismatch: getDeclaringClass().getName()=" + cls.getName() + ", getClassName()=" + className);
        }
    }
}
