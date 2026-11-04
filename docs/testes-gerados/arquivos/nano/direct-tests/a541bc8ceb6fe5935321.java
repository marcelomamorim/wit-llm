/* @test    @summary Test ASMifier.visitSource method for correct output and behavior    @run main ASMifierWitupTest */

import java.io.StringWriter;
import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitSourceWithValidInputs();
        testVisitSourceWithNullFile();
        testVisitSourceWithNullDebug();
        testVisitSourceWithBothNull();
    }

    private static void testVisitSourceWithValidInputs() {
        ASMifier asmifier = new ASMifier();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        asmifier.visitSource("TestFile.java", "DebugInfo");
        String output = sw.toString();
        if (!output.contains("cw.visitSource")) {
            throw new AssertionError("Expected output to contain 'cw.visitSource'");
        }
        if (!output.contains("\"TestFile.java\"")) {
            throw new AssertionError("Expected output to contain the file name string literal");
        }
        if (!output.contains("\"DebugInfo\"")) {
            throw new AssertionError("Expected output to contain the debug string literal");
        }
    }

    private static void testVisitSourceWithNullFile() {
        ASMifier asmifier = new ASMifier();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        asmifier.visitSource(null, "DebugInfo");
        String output = sw.toString();
        if (!output.contains("null")) {
            throw new AssertionError("Expected output to contain 'null' for null file");
        }
    }

    private static void testVisitSourceWithNullDebug() {
        ASMifier asmifier = new ASMifier();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        asmifier.visitSource("TestFile.java", null);
        String output = sw.toString();
        if (!output.contains("null")) {
            throw new AssertionError("Expected output to contain 'null' for null debug");
        }
    }

    private static void testVisitSourceWithBothNull() {
        ASMifier asmifier = new ASMifier();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        asmifier.visitSource(null, null);
        String output = sw.toString();
        if (!output.contains("null")) {
            throw new AssertionError("Expected output to contain 'null' for both null inputs");
        }
    }
}
