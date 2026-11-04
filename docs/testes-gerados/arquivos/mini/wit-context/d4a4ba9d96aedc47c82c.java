/*
 * @test
 * @summary Basic tests for Textifier.visitTypeInsn to check opcode and type handling
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitTypeInsnWithValidOpcodeAndType();
        testVisitTypeInsnWithNullType();
        testVisitTypeInsnWithNegativeOpcode();
    }

    private static void testVisitTypeInsnWithValidOpcodeAndType() {
        Textifier textifier = new Textifier();
        // Use a valid opcode from Opcodes range (e.g., NEW = 187)
        int opcode = 187; // NEW opcode
        String type = "java/lang/String";
        textifier.visitTypeInsn(opcode, type);
        // The text list should contain one string with opcode name and type descriptor
        List<String> text = textifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after visitTypeInsn");
        }
        String line = text.get(text.size() - 1);
        if (!line.contains("NEW") || !line.contains(type)) {
            throw new AssertionError("Output line should contain opcode name and type: " + line);
        }
    }

    private static void testVisitTypeInsnWithNullType() {
        Textifier textifier = new Textifier();
        int opcode = 187; // NEW opcode
        String type = null;
        // The method does not specify throwing NullPointerException explicitly,
        // so we test that it handles null gracefully or throws NPE.
        try {
            textifier.visitTypeInsn(opcode, type);
            // If no exception, check that text contains "null" string
            List<String> text = textifier.getText();
            String line = text.get(text.size() - 1);
            if (!line.contains("null")) {
                throw new AssertionError("Output line should contain 'null' when type is null: " + line);
            }
        } catch (NullPointerException npe) {
            // Acceptable behavior
        }
    }

    private static void testVisitTypeInsnWithNegativeOpcode() {
        Textifier textifier = new Textifier();
        int opcode = -1; // invalid opcode
        String type = "java/lang/Object";
        try {
            textifier.visitTypeInsn(opcode, type);
            // The method uses OPCODES[opcode], which may throw ArrayIndexOutOfBoundsException
            // So we check if exception is thrown
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for invalid opcode");
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            // Expected exception
        }
    }
}
