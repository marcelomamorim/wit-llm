/*
 * @test
 * @summary Test ASMifier.visitTypeInsn method behavior
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        // Create ASMifier instance with public constructor
        ASMifier asmifier = new ASMifier();

        // The 'name' field is protected final String, initialized to "cw" in default constructor
        // We test visitTypeInsn with valid opcode and type

        // Clear text list before test
        asmifier.text.clear();

        // Use opcode 1 (ACONST_NULL) which is valid in OPCODES array
        int opcode = 1;
        String type = "java/lang/String";

        asmifier.visitTypeInsn(opcode, type);

        // The text list should have one entry
        if (asmifier.text.size() != 1) {
            throw new AssertionError("Expected text size 1 but was " + asmifier.text.size());
        }

        String output = asmifier.text.get(0);

        // Expected output contains name, opcode name and type string
        if (!output.contains("cw.visitTypeInsn")) {
            throw new AssertionError("Output does not contain visitor name: " + output);
        }
        if (!output.contains("ACONST_NULL")) {
            throw new AssertionError("Output does not contain opcode name ACONST_NULL: " + output);
        }
        if (!output.contains(type)) {
            throw new AssertionError("Output does not contain type string: " + output);
        }

        // Test with opcode 0 (NOP) and null type (allowed, no exception expected)
        asmifier.text.clear();
        asmifier.visitTypeInsn(0, null);
        if (asmifier.text.size() != 1) {
            throw new AssertionError("Expected text size 1 after null type but was " + asmifier.text.size());
        }
        String outputNullType = asmifier.text.get(0);
        if (!outputNullType.contains("NOP")) {
            throw new AssertionError("Output does not contain opcode name NOP: " + outputNullType);
        }
        if (!outputNullType.contains("null")) {
            throw new AssertionError("Output does not contain 'null' for null type: " + outputNullType);
        }

        // Test with opcode out of bounds (negative) - expect ArrayIndexOutOfBoundsException
        boolean caught = false;
        try {
            asmifier.visitTypeInsn(-1, "java/lang/Object");
        } catch (ArrayIndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative opcode");
        }

        // Test with opcode out of bounds (too large) - expect ArrayIndexOutOfBoundsException
        caught = false;
        try {
            asmifier.visitTypeInsn(1000, "java/lang/Object");
        } catch (ArrayIndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for large opcode");
        }

        System.out.println("All ASMifier.visitTypeInsn tests passed.");
    }
}
