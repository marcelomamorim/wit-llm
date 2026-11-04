/*
 * @test
 * @summary Smoke test that ASMifier.visitAnnotationEnd() can be invoked repeatedly, directly and through a subclass, without throwing
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

public class ASMifierWitupTest {

    static class Sub extends jdk.internal.org.objectweb.asm.util.ASMifier {
        Sub() {
            super();
        }
    }

    public static void main(String[] args) {
        jdk.internal.org.objectweb.asm.util.ASMifier a = new jdk.internal.org.objectweb.asm.util.ASMifier();
        a.visitAnnotationEnd();
        a.visitAnnotationEnd();

        Sub s = new Sub();
        s.visitAnnotationEnd();

        if (a == null || s == null) {
            throw new RuntimeException("ASMifier instances must not be null");
        }
    }
}
