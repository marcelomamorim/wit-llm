/*
 * @test
 * @summary Test ASMifier.visitUse method for correct string output
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitUseWithNull();
        testVisitUseWithEmptyString();
        testVisitUseWithRegularString();
    }

    public static void testVisitUseWithNull() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitUse(null);
            String output = asmifier.text.get(asmifier.text.size() - 1);
            if (!output.contains("null")) {
                throw new AssertionError("Expected 'null' in output for null input");
            }
        } catch (Exception e) {
            throw new AssertionError("Exception thrown for null input: " + e);
        }
    }

    public static void testVisitUseWithEmptyString() {
        ASMifier asmifier = new ASMifier();
        asmifier.visitUse("");
        String output = asmifier.text.get(asmifier.text.size() - 1);
        if (!output.contains("")) {
            throw new AssertionError("Output does not contain empty string for empty input");
        }
    }

    public static void testVisitUseWithRegularString() {
        ASMifier asmifier = new ASMifier();
        String serviceName = "TestService";
        asmifier.visitUse(serviceName);
        String output = asmifier.text.get(asmifier.text.size() - 1);
        if (!output.contains(serviceName)) {
            throw new AssertionError("Output does not contain the service name: " + serviceName);
        }
        if (!output.contains("append")) {
            throw new AssertionError("Output does not contain 'append' call");
        }
    }
}
