/*
 * @test
 * @summary Smoke and regression test for ASMifier.visitSource with normal, null and empty arguments
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        asmifier.visitSource("Foo.java", "debug info");
        asmifier.visitSource("", "");
        asmifier.visitSource(null, null);
        asmifier.visitSource("Bar.java", null);
        asmifier.visitSource(null, "debug");

        ASMifier second = new ASMifier();
        for (int i = 0; i < 3; i++) {
            second.visitSource("Loop" + i + ".java", "d" + i);
        }
    }
}
