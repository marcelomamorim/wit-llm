/*
 * @test
 * @summary Test ASMifier.visitAnnotation method for correct output
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotation();
    }

    public static void testVisitAnnotation() {
        ASMifier asmifier = new ASMifier();
        String desc = "Ljava/lang/Override;";
        boolean visible = true;
        ASMifier result = asmifier.visitAnnotation(desc, visible);
        String textOutput = result.getText();
        if (textOutput == null || !textOutput.contains("visitAnnotation")) {
            throw new AssertionError("visitAnnotation output does not contain expected method call") ;
        }
        if (!textOutput.contains(desc)) {
            throw new AssertionError("Output does not contain description string") ;
        }
        if (!textOutput.contains("true")) {
            throw new AssertionError("Output does not contain visibility boolean") ;
        }
        System.out.println("testVisitAnnotation passed.");
    }
}
