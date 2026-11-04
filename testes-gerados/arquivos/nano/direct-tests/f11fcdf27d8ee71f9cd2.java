/*
 * @test
 * @summary Test ASMifier.visitAnnotationDefault() method for correctness and regressions
 * @run main ASMifierWitupTest
 */

import java.io.StringWriter;
import java.io.PrintWriter;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationDefault();
    }

    public static void testVisitAnnotationDefault() {
        jdk.internal.org.objectweb.asm.util.ASMifier asmifier = new jdk.internal.org.objectweb.asm.util.ASMifier();
        jdk.internal.org.objectweb.asm.util.ASMifier result = asmifier.visitAnnotationDefault();
        String output = result.getText();
        if (output == null || output.isEmpty()) {
            throw new AssertionError("visitAnnotationDefault() returned empty or null text");
        }
        if (!output.contains("visitAnnotationDefault")) {
            throw new AssertionError("Output does not contain 'visitAnnotationDefault'" );
        }
        System.out.println("visitAnnotationDefault() output:");
        System.out.println(output);
    }
}
