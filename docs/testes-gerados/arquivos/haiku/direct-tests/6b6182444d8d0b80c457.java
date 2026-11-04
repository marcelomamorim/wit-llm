/*
 * @test
 * @summary Verifies that ASMifier.visitAnnotation returns a new non-null nested ASMifier visitor on each call
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asm = new ASMifier();

        ASMifier a1 = asm.visitAnnotation("Ljava/lang/Deprecated;", true);
        if (a1 == null) {
            throw new RuntimeException("visitAnnotation returned null for visible annotation");
        }
        if (a1 == asm) {
            throw new RuntimeException("visitAnnotation must return a nested visitor, not this");
        }

        ASMifier a2 = asm.visitAnnotation("LFoo;", false);
        if (a2 == null) {
            throw new RuntimeException("visitAnnotation returned null for invisible annotation");
        }
        if (a2 == a1) {
            throw new RuntimeException("Successive visitAnnotation calls must return distinct visitors");
        }

        ASMifier a3 = asm.visitAnnotation("LBar;", true);
        if (a3 == null || a3 == a1 || a3 == a2) {
            throw new RuntimeException("Third visitAnnotation call returned an invalid visitor");
        }
    }
}
