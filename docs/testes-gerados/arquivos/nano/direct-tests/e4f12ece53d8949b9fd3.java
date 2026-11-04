/* @test    @summary Test ASMifier.visitMaxs method for correct output and exception handling    @run main ASMifierWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitMaxsOutput();
        testVisitMaxsNullMaxStack();
        testVisitMaxsNegativeValues();
    }

    public static void testVisitMaxsOutput() {
        jdk.internal.org.objectweb.asm.util.ASMifier asmifier = new jdk.internal.org.objectweb.asm.util.ASMifier();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(baos, true, StandardCharsets.UTF_8);
        try {
            asmifier.visitMaxs(10, 20);
            asmifier.print(pw);
            pw.flush();
            String output = baos.toString(StandardCharsets.UTF_8.name());
            if (!output.contains("ASMifier.visitMaxs(10, 20);")) {
                throw new AssertionError("Output does not contain expected visitMaxs call");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during testVisitMaxsOutput", e);
        }
    }

    public static void testVisitMaxsNullMaxStack() {
        jdk.internal.org.objectweb.asm.util.ASMifier asmifier = new jdk.internal.org.objectweb.asm.util.ASMifier();
        try {
            asmifier.visitMaxs(0, 5);
            // Should not throw, test passes if no exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception with maxStack=0", e);
        }
    }

    public static void testVisitMaxsNegativeValues() {
        jdk.internal.org.objectweb.asm.util.ASMifier asmifier = new jdk.internal.org.objectweb.asm.util.ASMifier();
        try {
            asmifier.visitMaxs(-1, -1);
            // Should not throw, test passes if no exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception with negative values", e);
        }
    }
}
