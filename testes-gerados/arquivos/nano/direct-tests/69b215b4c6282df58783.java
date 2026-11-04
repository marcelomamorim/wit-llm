/*
 * @test
 * @summary Test for ASMifier.visitTypeInsn method
 * @run main ASMifierWitupTest
 */
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {
    public static void main(String[] args) {
        testVisitTypeInsn();
    }

    public static void testVisitTypeInsn() {
        ASMifier asmifier = new ASMifier();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(baos, true);
        asmifier.setWriter(pw);
        int opcode = 1; // Example opcode
        String type = "java/lang/String";
        asmifier.visitTypeInsn(opcode, type);
        pw.flush();
        String output = baos.toString();
        if (!output.contains("visitTypeInsn")) {
            throw new AssertionError("Output does not contain visitTypeInsn");
        }
        if (!output.contains(type)) {
            throw new AssertionError("Output does not contain type: " + type);
        }
        System.out.println("testVisitTypeInsn passed.");
    }
}
