/*
 * @test
 * @summary Test ASMifier.visitFieldEnd() method for correct behavior.
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitFieldEnd();
    }

    public static void testVisitFieldEnd() {
        ASMifier asmifier = new ASMifier();
        asmifier.visitFieldEnd();
        String lastLine = asmifier.text.get(asmifier.text.size() - 1);
        assert lastLine.endsWith(".visitEnd();\n") : "Expected line to end with '.visitEnd();\n'";
        assert lastLine.contains(asmifier.name) : "Line should contain the visitor variable name";
        System.out.println("testVisitFieldEnd passed.");
    }
}
