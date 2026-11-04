/*
 * @test
 * @summary Checks that ASMifier.visitAnnotation returns a non-null child ASMifier visitor for valid descriptors
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {
    public static void main(String[] args) {
        ASMifier printer = new ASMifier();

        ASMifier a1 = printer.visitAnnotation("Ljava/lang/Deprecated;", true);
        check(a1 != null, "visitAnnotation(visible=true) returned null");
        check(a1 != printer, "visitAnnotation should return a distinct child visitor");

        ASMifier a2 = printer.visitAnnotation("Ljava/lang/Deprecated;", false);
        check(a2 != null, "visitAnnotation(visible=false) returned null");
        check(a2 != a1, "consecutive visitAnnotation calls should return distinct visitors");

        ASMifier a3 = printer.visitAnnotation("LFoo;", true);
        check(a3 != null, "visitAnnotation with a different descriptor returned null");

        ASMifier a4 = a3.visitAnnotation("LBar;", false);
        check(a4 != null, "nested visitAnnotation returned null");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
