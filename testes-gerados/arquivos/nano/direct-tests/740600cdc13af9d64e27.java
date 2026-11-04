/* @test    @summary Test visitLdcInsn method of Textifier class    @run main TextifierWitupTest */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitLdcInsnWithString();
        testVisitLdcInsnWithType();
        testVisitLdcInsnWithInteger();
    }

    static void testVisitLdcInsnWithString() {
        Textifier t = new Textifier();
        t.visitLdcInsn("Hello World");
        List<String> output = t.text;
        String lastLine = output.get(output.size() - 1);
        assert lastLine.contains("LDC ") : "Expected 'LDC ' in output";
        assert lastLine.contains("Hello World") : "Expected 'Hello World' in output";
    }

    static void testVisitLdcInsnWithType() {
        Textifier t = new Textifier();
        t.visitLdcInsn(jdk.internal.org.objectweb.asm.Type.getType("Ljava/lang/String;"));
        List<String> output = t.text;
        String lastLine = output.get(output.size() - 1);
        assert lastLine.contains("LDC ") : "Expected 'LDC ' in output";
        assert lastLine.contains("Ljava/lang/String;") : "Expected descriptor in output";
        assert lastLine.contains(".class") : "Expected '.class' in output";
    }

    static void testVisitLdcInsnWithInteger() {
        Textifier t = new Textifier();
        t.visitLdcInsn(42);
        List<String> output = t.text;
        String lastLine = output.get(output.size() - 1);
        assert lastLine.contains("LDC ") : "Expected 'LDC ' in output";
        assert lastLine.contains("42") : "Expected '42' in output";
    }
}
