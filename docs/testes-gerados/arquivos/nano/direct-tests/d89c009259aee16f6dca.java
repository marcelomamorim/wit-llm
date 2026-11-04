/* @test    @summary Test getDeclaringClass method of StackFrameInfo class    @run main StackFrameInfoWitupTest */

import java.lang.StackFrameInfo;
import static java.lang.StackWalker.Option.*;

public class StackFrameInfoWitupTest {

    public static void main(String[] args) {
        testGetDeclaringClassReturnsNonNull();
        testGetDeclaringClassReturnsExpectedClass();
    }

    private static void testGetDeclaringClassReturnsNonNull() {
        StackFrameInfo sfi = new StackFrameInfo(null);
        Class<?> cls = sfi.getDeclaringClass();
        if (cls == null) {
            throw new AssertionError("Expected non-null Class from getDeclaringClass()");
        }
    }

    private static void testGetDeclaringClassReturnsExpectedClass() {
        StackFrameInfo sfi = new StackFrameInfo(null);
        Class<?> cls = sfi.getDeclaringClass();
        String className = cls.getName();
        if (className == null || className.isEmpty()) {
            throw new AssertionError("Class name should not be null or empty");
        }
    }
}
