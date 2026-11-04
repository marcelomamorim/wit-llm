/*
 * @test
 * @summary Verifies StackFrame.getDescriptor() returns method descriptors for frames obtained from StackWalker.walk
 * @run main StackWalkerWitupTest
 */

import java.util.Objects;

public class StackWalkerWitupTest {
    static String d0;
    static String d1;

    static void target() {
        d0 = descriptorOf("target");
    }

    static void withArg(String s, int i) {
        d1 = descriptorOf("withArg");
    }

    static String descriptorOf(String methodName) {
        return StackWalker.getInstance().walk(s ->
                s.filter(f -> f.getMethodName().equals(methodName))
                 .map(StackWalker.StackFrame::getDescriptor)
                 .findFirst()
                 .orElse(null));
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        target();
        withArg("x", 1);

        check(d0 != null, "descriptor for target is null");
        check(d0.equals("()V"), "unexpected descriptor for target: " + d0);

        check(d1 != null, "descriptor for withArg is null");
        check(d1.equals("(Ljava/lang/String;I)V"), "unexpected descriptor for withArg: " + d1);

        String mainDesc = StackWalker.getInstance().walk(s ->
                s.filter(f -> f.getMethodName().equals("main"))
                 .map(StackWalker.StackFrame::getDescriptor)
                 .findFirst()
                 .orElse(null));
        check(Objects.equals(mainDesc, "([Ljava/lang/String;)V"),
              "unexpected descriptor for main: " + mainDesc);
    }
}
