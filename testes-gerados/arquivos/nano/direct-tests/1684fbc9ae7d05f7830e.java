/* @test    @summary Test ASMifier.visitAnnotation method for correct output and behavior    @run main ASMifierWitupTest */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationOutputs();
        testVisitAnnotationNullValues();
    }

    private static void testVisitAnnotationOutputs() {
        ASMifier asmifier = new ASMifier();
        String name = "TestAnnotation";
        String desc = "Lcom/example/MyAnnotation;";
        ASMifier result = asmifier.visitAnnotation(name, desc);
        String textOutput = result.getText();
        if (textOutput == null || !textOutput.contains("visitAnnotation")) {
            throw new AssertionError("Expected output to contain 'visitAnnotation' call") ;
        }
        if (!textOutput.contains(name) || !textOutput.contains(desc)) {
            throw new AssertionError("Output does not contain input parameters") ;
        }
        System.out.println("testVisitAnnotationOutputs passed.");
    }

    private static void testVisitAnnotationNullValues() {
        ASMifier asmifier = new ASMifier();
        String name = null;
        String desc = null;
        ASMifier result = asmifier.visitAnnotation(name, desc);
        String textOutput = result.getText();
        if (textOutput == null || !textOutput.contains("visitAnnotation")) {
            throw new AssertionError("Expected output to contain 'visitAnnotation' call") ;
        }
        System.out.println("testVisitAnnotationNullValues passed.");
    }
}
