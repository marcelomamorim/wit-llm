/*
 * @test
 * @summary Test Textifier.visitLdcInsn behavior with various constant types
 * @run main TextifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with a String constant
        textifier.visitLdcInsn("testString");
        List<String> text = textifier.getText();
        if (text.isEmpty() || !text.get(text.size() - 1).contains("LDC \"testString\"")) {
            throw new AssertionError("visitLdcInsn did not correctly append String constant");
        }

        // Test with a Type constant
        Type type = Type.getType("Ljava/lang/String;");
        textifier.visitLdcInsn(type);
        text = textifier.getText();
        if (text.isEmpty() || !text.get(text.size() - 1).contains("Ljava/lang/String;.class")) {
            throw new AssertionError("visitLdcInsn did not correctly append Type constant descriptor");
        }

        // Test with a generic Object constant (Integer)
        Integer intConst = Integer.valueOf(123);
        textifier.visitLdcInsn(intConst);
        text = textifier.getText();
        if (text.isEmpty() || !text.get(text.size() - 1).contains("123")) {
            throw new AssertionError("visitLdcInsn did not correctly append generic Object constant");
        }

        // Test with null constant
        textifier.visitLdcInsn(null);
        text = textifier.getText();
        if (text.isEmpty() || !text.get(text.size() - 1).contains("null")) {
            throw new AssertionError("visitLdcInsn did not correctly append null constant");
        }

        System.out.println("All tests passed.");
    }
}
