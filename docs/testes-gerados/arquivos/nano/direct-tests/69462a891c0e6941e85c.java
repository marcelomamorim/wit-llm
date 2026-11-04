/* @test    @summary Test ASMifier.visitArray method for correct output and behavior    @run main ASMifierWitupTest */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitArrayWithValidName();
        testVisitArrayWithEmptyName();
        testVisitArrayWithNullName();
    }

    private static void testVisitArrayWithValidName() {
        ASMifier asmifier = new ASMifier();
        String name = "TestArray";
        ASMifier result = asmifier.visitArray(name);
        String textOutput = result.getText();
        if (textOutput == null || !textOutput.contains("visitArray")) {
            throw new AssertionError("Expected output to contain 'visitArray' call") ;
        }
        if (!textOutput.contains(name)) {
            throw new AssertionError("Expected output to contain the name") ;
        }
        System.out.println("testVisitArrayWithValidName passed.");
    }

    private static void testVisitArrayWithEmptyName() {
        ASMifier asmifier = new ASMifier();
        String name = "";
        ASMifier result = asmifier.visitArray(name);
        String textOutput = result.getText();
        if (textOutput == null || !textOutput.contains("visitArray")) {
            throw new AssertionError("Expected output to contain 'visitArray' call") ;
        }
        if (!textOutput.contains(name)) {
            throw new AssertionError("Expected output to contain the empty name") ;
        }
        System.out.println("testVisitArrayWithEmptyName passed.");
    }

    private static void testVisitArrayWithNullName() {
        ASMifier asmifier = new ASMifier();
        String name = null;
        try {
            asmifier.visitArray(name);
            throw new AssertionError("Expected NullPointerException for null name") ;
        } catch (NullPointerException e) {
            System.out.println("testVisitArrayWithNullName passed.");
        }
    }
}
