/*
 * @test
 * @summary Smoke and regression checks for ASMifier.visitRequire with valid inputs
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier a = new ASMifier();
        a.visitRequire("java.base", 0, "11");
        a.visitRequire("java.base", 0x8000, "11");
        a.visitRequire("java.sql", 0, "");
        a.visitRequire("", 0, "1.0");
        a.visitRequire("java.logging", 0x0020, "11.0.1");

        ASMifier b = new ASMifier();
        for (int i = 0; i < 100; i++) {
            b.visitRequire("m" + i, i, "v" + i);
        }

        ASMifier c = new ASMifier();
        c.visitRequire("java.base", 0, "11");
        c.visitRequire("java.base", 0, "11");
    }
}
