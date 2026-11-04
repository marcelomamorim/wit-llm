/*
 * @test
 * @summary Test for visitMultiANewArrayInsn method in Textifier class
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitMultiANewArrayInsn();
    }

    public static void testVisitMultiANewArrayInsn() {
        Textifier textifier = new Textifier();
        String desc = "[I"; // array descriptor for int[]
        int dims = 2;
        try {
            textifier.visitMultiANewArrayInsn(desc, dims);
            System.out.println("visitMultiANewArrayInsn executed successfully.");
        } catch (Exception e) {
            System.err.println("Exception during visitMultiANewArrayInsn: " + e);
        }
    }
}
