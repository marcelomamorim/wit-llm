/*
 * @test
 * @summary Smoke test that ASMifier.visitOpen accepts package, access and module variants without throwing
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier v = new ASMifier();
        v.visitOpen("java/lang/foo", 0);
        v.visitOpen("p", 0, (String[]) null);
        v.visitOpen("p", 0, new String[0]);
        v.visitOpen("p", 0, "a", "b");
        v.visitOpen("q", 0, "m");

        ASMifier second = new ASMifier();
        second.visitOpen("r", 0, "x");
        second.visitOpen("s", 0);
    }
}
