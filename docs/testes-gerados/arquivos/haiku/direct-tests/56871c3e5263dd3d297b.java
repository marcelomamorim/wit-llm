/*
 * @test
 * @summary StackFrame.getMethodType returns a consistent MethodType or throws UnsupportedOperationException
 * @run main StackWalkerWitupTest
 */

import java.lang.invoke.MethodType;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        StackWalker.StackFrame frame = walker.walk(s -> s.filter(f -> "main".equals(f.getMethodName())).findFirst())
                .orElseThrow(() -> new RuntimeException("main frame not found"));
        try {
            MethodType mt = frame.getMethodType();
            check(mt != null, "getMethodType returned null");
            check(mt.parameterCount() == 1, "unexpected parameter count: " + mt.parameterCount());
            check(mt.parameterType(0) == String[].class, "unexpected parameter type: " + mt.parameterType(0));
            check(mt.returnType() == void.class, "unexpected return type: " + mt.returnType());
        } catch (UnsupportedOperationException e) {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
