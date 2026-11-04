/*
 * @test
 * @summary Test ASMifier.visitIntInsn method for correct output formatting
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with a normal opcode (e.g., BIPUSH = 16) and operand 123
        asmifier.visitIntInsn(Opcodes.BIPUSH, 123);
        String output1 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output1.contains("visitIntInsn") || !output1.contains("BIPUSH") || !output1.contains("123")) {
            throw new Error("visitIntInsn failed for BIPUSH opcode with operand 123: " + output1);
        }

        // Test with NEWARRAY opcode (Opcodes.NEWARRAY = 188) and operand 4 (T_INT)
        asmifier.visitIntInsn(Opcodes.NEWARRAY, 4);
        String output2 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output2.contains("visitIntInsn") || !output2.contains("NEWARRAY") || !output2.contains("int")) {
            throw new Error("visitIntInsn failed for NEWARRAY opcode with operand 4 (int): " + output2);
        }

        // Test with NEWARRAY opcode and operand 0 (T_BOOLEAN)
        asmifier.visitIntInsn(Opcodes.NEWARRAY, 0);
        String output3 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output3.contains("visitIntInsn") || !output3.contains("NEWARRAY") || !output3.contains("boolean")) {
            throw new Error("visitIntInsn failed for NEWARRAY opcode with operand 0 (boolean): " + output3);
        }

        // Test with an opcode outside normal range (e.g., 0) and operand 0
        asmifier.visitIntInsn(0, 0);
        String output4 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output4.contains("visitIntInsn") || !output4.contains("0") || !output4.contains("0")) {
            throw new Error("visitIntInsn failed for opcode 0 with operand 0: " + output4);
        }
    }
}
