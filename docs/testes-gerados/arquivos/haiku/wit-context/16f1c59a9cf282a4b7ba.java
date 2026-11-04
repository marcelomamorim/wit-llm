/*
 * @test
 * @summary Checks that ASMifier.visitUse accepts service names without throwing, including repeated calls on the same visitor
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier v = new ASMifier();
        v.visitUse("java/lang/Runnable");
        v.visitUse("");
        v.visitUse("a.b.C$Service");

        ASMifier w = new ASMifier();
        w.visitUse("x");
        w.visitUse("y");
        w.visitUse("a much longer service name that forces the internal buffer to be reset and reused");
    }
}
