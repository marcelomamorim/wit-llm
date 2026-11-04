/* @test    @summary Test ASMifier.visitVarInsn method for basic behavior    @run main ASMifierWitupTest */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitVarInsn();
    }

    public static void testVisitVarInsn() {
        ASMifier asmifier = new ASMifier();
        int opcode = 21; // Example opcode, e.g., ILOAD
        int var = 5; // Example variable index
        asmifier.visitVarInsn(opcode, var);
        String lastLine = asmifier.text.get(asmifier.text.size() - 1);
        String expectedOpcode = asmifier.OPCODES[opcode];
        assert lastLine.contains("visitVarInsn") : "Expected method call in output";
        assert lastLine.contains(expectedOpcode) : "Opcode not correctly printed";
        assert lastLine.contains(String.valueOf(var)) : "Variable index not correctly printed";
    }
}
