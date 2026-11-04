/*
 * @test
 * @summary Verifies StackFrameInfo.getDeclaringClass() returns the class declaring the walked frame when RETAIN_CLASS_REF is enabled
 * @run main StackFrameInfoWitupTest
 */

import java.lang.StackWalker.StackFrame;
import java.util.Optional;

public class StackFrameInfoWitupTest {

    static class Inner {
        static StackFrame topFrame() {
            StackWalker sw = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REF);
            Optional<StackFrame> f = sw.walk(s -> s.findFirst());
            return f.get();
        }
    }

    static StackFrame frameOfThisMethod() {
        StackWalker sw = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REF);
        Optional<StackFrame> f = sw.walk(s -> s.findFirst());
        return f.get();
    }

    public static void main(String[] args) {
        StackFrame frame = frameOfThisMethod();

        if (!"frameOfThisMethod".equals(frame.getMethodName())) {
            throw new RuntimeException("Unexpected top frame: " + frame.getMethodName());
        }

        Class<?> declaring = frame.getDeclaringClass();
        if (declaring != StackFrameInfoWitupTest.class) {
            throw new RuntimeException("Expected StackFrameInfoWitupTest but got " + declaring);
        }

        if (frame.getDeclaringClass() != declaring) {
            throw new RuntimeException("getDeclaringClass() returned inconsistent results");
        }

        if (!declaring.getName().equals(frame.getClassName())) {
            throw new RuntimeException("getClassName() inconsistent with getDeclaringClass()");
        }

        StackFrame innerFrame = Inner.topFrame();
        Class<?> innerDeclaring = innerFrame.getDeclaringClass();
        if (innerDeclaring != Inner.class) {
            throw new RuntimeException("Expected Inner but got " + innerDeclaring);
        }
    }
}
