/*
 * @test
 * @summary Checks that ASMifier.visitExport accepts varargs and null module arrays without throwing and can be called repeatedly on the same instance.
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier v = new ASMifier();
        v.visitExport("pkg/a", 0);
        v.visitExport("pkg/b", 0, (String[]) null);
        v.visitExport("pkg/c", 0, "m1", "m2");
        v.visitExport("pkg/d", 0, new String[0]);
        v.visitExport("pkg/e", 0, "m");

        for (int i = 0; i < 100; i++) {
            ASMifier w = new ASMifier();
            w.visitExport("pkg/loop" + i, 0, "x", "y");
            w.visitExport("pkg/loop2" + i, 0);
            w.visitExport("pkg/loop3" + i, 0, (String[]) null);
        }

        for (int i = 0; i < 50; i++) {
            v.visitExport("pkg/repeat" + i, 0, "mod" + i);
        }
    }
}
