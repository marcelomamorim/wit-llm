/*
 * @test
 * @summary Smoke test for ASMifier.visitMethodEnd() via public and protected constructors
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    static final class Sub extends ASMifier {
        Sub() {
            super(Opcodes.ASM5, "mv", 0);
        }
    }

    public static void main(String[] args) {
        ASMifier pub = new ASMifier();
        pub.visitMethodEnd();
        pub.visitMethodEnd();

        Sub sub = new Sub();
        sub.visitMethodEnd();
        sub.visitMethodEnd();
    }
}
