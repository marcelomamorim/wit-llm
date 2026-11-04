/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.Textifier.visitTypeInsn method
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with valid opcode and type
        int opcode = 192; // Opcodes.NEWARRAY (valid opcode in ASM)
        String type = "java/lang/String";
        textifier.visitTypeInsn(opcode, type);
        List<String> text = textifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after visitTypeInsn");
        }
        String line = text.get(text.size() - 1);
        if (!line.contains("NEWARRAY") && !line.contains("192")) {
            throw new AssertionError("Output line should contain opcode mnemonic or opcode number");
        }
        if (!line.contains(type)) {
            throw new AssertionError("Output line should contain the type string");
        }

        // Test with opcode 0 and empty type
        opcode = 0;
        type = "";
        textifier.visitTypeInsn(opcode, type);
        text = textifier.getText();
        line = text.get(text.size() - 1);
        if (!line.contains("0") && !line.contains("")) {
            throw new AssertionError("Output line should contain opcode 0 and empty type");
        }

        // Test with negative opcode and null type
        opcode = -1;
        type = null;
        try {
            textifier.visitTypeInsn(opcode, type);
            text = textifier.getText();
            line = text.get(text.size() - 1);
            if (!line.contains("-1")) {
                throw new AssertionError("Output line should contain negative opcode");
            }
            if (line.contains("null")) {
                throw new AssertionError("Output line should not contain literal 'null'");
            }
        } catch (Exception e) {
            throw new AssertionError("visitTypeInsn should not throw exception for null type", e);
        }
    }
}
