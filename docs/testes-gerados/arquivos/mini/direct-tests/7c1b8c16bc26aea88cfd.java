/*
 * @test
 * @summary Test ASMifier.visitMainClass method behavior
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with a normal class name
        asmifier.visitMainClass("java/lang/Object");
        String output1 = asmifier.getText().get(0);
        if (!output1.equals("mdv.visitMainClass(\"java/lang/Object\");\n")) {
            throw new Error("Unexpected output for java/lang/Object: " + output1);
        }

        // Test with empty string
        asmifier.visitMainClass("");
        String output2 = asmifier.getText().get(1);
        if (!output2.equals("mdv.visitMainClass(\"\");\n")) {
            throw new Error("Unexpected output for empty string: " + output2);
        }

        // Test with null (should print mdv.visitMainClass(null);
        asmifier.visitMainClass(null);
        String output3 = asmifier.getText().get(2);
        if (!output3.equals("mdv.visitMainClass(null);\n")) {
            throw new Error("Unexpected output for null: " + output3);
        }
    }
}
