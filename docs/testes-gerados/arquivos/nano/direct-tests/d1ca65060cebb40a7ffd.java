/*
 * @test
 * @summary Test for Textifier.visitIntInsn method.
 * @run main TextifierWitupTest
 */
import jdk.internal.org.objectweb.asm.util.Textifier;
import jdk.internal.org.objectweb.asm.Opcodes;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitIntInsn();
    }

    public static void testVisitIntInsn() {
        Textifier textifier = new Textifier();
        // Test with opcode Opcodes.NEWARRAY and operand 4
        textifier.visitIntInsn(Opcodes.NEWARRAY, 4);
        String output = textifier.text.get(textifier.text.size() - 1);
        assert output.contains("NEWARRAY") : "Expected 'NEWARRAY' in output";
        assert output.contains("4") : "Expected operand '4' in output";

        // Test with opcode Opcodes.BIPUSH and operand 10
        textifier.visitIntInsn(Opcodes.BIPUSH, 10);
        output = textifier.text.get(textifier.text.size() - 1);
        assert output.contains("BIPUSH") : "Expected 'BIPUSH' in output";
        assert output.contains("10") : "Expected operand '10' in output";
    }
}
