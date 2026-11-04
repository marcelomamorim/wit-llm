/*
 * @test
 * @summary Smoke tests for ASMifier.visitSource using the public constructor
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitSourceWithFileAndDebugDoesNotThrow();
        testVisitSourceRepeatedCallsDoesNotThrow();
        testVisitSourceWithNullFileDoesNotThrow();
    }

    static void testVisitSourceWithFileAndDebugDoesNotThrow() {
        ASMifier asm = new ASMifier();
        asm.visitSource("Foo.java", "debug info");
    }

    static void testVisitSourceRepeatedCallsDoesNotThrow() {
        ASMifier asm = new ASMifier();
        asm.visitSource("Foo.java", "dbg1");
        asm.visitSource("Bar.java", "dbg2");
    }

    static void testVisitSourceWithNullFileDoesNotThrow() {
        ASMifier asm = new ASMifier();
        asm.visitSource(null, null);
    }
}
