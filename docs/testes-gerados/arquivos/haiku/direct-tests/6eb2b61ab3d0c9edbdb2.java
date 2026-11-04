/*
 * @test
 * @summary Smoke test for ASMifier.visitFieldAttribute: the method must be reachable and must not fail with an unexpected exception
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier printer = new ASMifier();
        if (printer == null) {
            throw new RuntimeException("ASMifier() returned null");
        }
        try {
            printer.visitFieldAttribute(null);
        } catch (NullPointerException expected) {
            // visitFieldAttribute delegates to visitAttribute(attr), which
            // dereferences the attribute; a null argument is not a supported input.
        }
    }
}
