/*
 * @test
 * @summary Test for visitMultiANewArrayInsn method in Textifier class
 * @run main TextifierWitupTest
 */
import jdk.internal.org.objectweb.asm.util.Textifier;
import java.util.List;
import java.util.ArrayList;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitMultiANewArrayInsn();
    }

    public static void testVisitMultiANewArrayInsn() {
        Textifier textifier = new Textifier();
        String descriptor = "[I"; // array of int
        int dims = 3;
        textifier.visitMultiANewArrayInsn(descriptor, dims);
        List<String> output = textifier.text;
        boolean found = false;
        for (String line : output) {
            if (line.contains("MULTIANEWARRAY") && line.contains(descriptor) && line.contains(String.valueOf(dims))) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("Expected output not found: 'MULTIANEWARRAY' with descriptor and dims")
        }
    }
}
