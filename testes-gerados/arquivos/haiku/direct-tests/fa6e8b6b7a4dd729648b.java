/*
 * @test
 * @summary Smoke test for ASMifier.visitPackage using only the public constructor and the public visitPackage method
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier printer = new ASMifier();

        printer.visitPackage("jdk/internal/example");
        printer.visitPackage("");
        printer.visitPackage("a/b/c");

        ASMifier other = new ASMifier();
        other.visitPackage("com/example/pkg");
    }
}
