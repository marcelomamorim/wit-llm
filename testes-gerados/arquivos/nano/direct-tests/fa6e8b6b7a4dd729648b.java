/*
 * @test
 * @summary Test ASMifier.visitPackage method for correct output
 * @run main ASMifierWitupTest
 */

import java.io.StringWriter;
import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitPackage();
    }

    public static void testVisitPackage() {
        ASMifier asmifier = new ASMifier();
        String packageName = "com/example/test";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        // Redirect output to StringWriter
        asmifier.setPrintWriter(pw);
        asmifier.visitPackage(packageName);
        pw.flush();
        String output = sw.toString();
        String expectedStart = "mdv.visitPackage(";
        if (output.startsWith(expectedStart) && output.contains("");
            && output.contains(packageName)) {
            System.out.println("visitPackage output is correct.");
        } else {
            throw new AssertionError("Unexpected output: " + output);
        }
    }
}
