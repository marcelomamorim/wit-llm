/*
 * @test
 * @summary Test visitSource method for normal and null inputs
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitSourceNormal();
        testVisitSourceNullFile();
        testVisitSourceNullDebug();
    }

    private static void testVisitSourceNormal() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitSource("TestFile.java", "DebugInfo");
            System.out.println("testVisitSourceNormal passed");
        } catch (Throwable t) {
            System.err.println("testVisitSourceNormal failed: " + t);
        }
    }

    private static void testVisitSourceNullFile() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitSource(null, "DebugInfo");
            System.out.println("testVisitSourceNullFile passed");
        } catch (Throwable t) {
            System.err.println("testVisitSourceNullFile failed: " + t);
        }
    }

    private static void testVisitSourceNullDebug() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitSource("TestFile.java", null);
            System.out.println("testVisitSourceNullDebug passed");
        } catch (Throwable t) {
            System.err.println("testVisitSourceNullDebug failed: " + t);
        }
    }
}
