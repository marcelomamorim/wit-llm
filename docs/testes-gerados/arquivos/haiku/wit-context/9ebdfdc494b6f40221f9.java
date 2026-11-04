/*
 * @test
 * @summary Smoke test for ASMifier.visitAnnotationEnd on a freshly constructed printer
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {
    public static void main(String[] args) {
        ASMifier printer = new ASMifier();
        printer.visitAnnotationEnd();
        printer.visitAnnotationEnd();
    }
}
