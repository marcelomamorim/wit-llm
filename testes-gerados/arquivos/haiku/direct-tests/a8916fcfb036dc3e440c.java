/*
 * @test
 * @summary Smoke tests for ASMifier.visitOpen with null, empty and non-empty module varargs
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asm = new ASMifier();

        asm.visitOpen("pkg/a", 0);
        asm.visitOpen("pkg/b", 0, (String[]) null);
        asm.visitOpen("pkg/c", 0, new String[0]);
        asm.visitOpen("pkg/d", 0, "m1");
        asm.visitOpen("pkg/e", 0, "m1", "m2", "m3");

        ASMifier other = new ASMifier();
        other.visitOpen("pkg/f", 0, "mod");

        if (asm == null || other == null) {
            throw new AssertionError("ASMifier instance unexpectedly null");
        }
    }
}
