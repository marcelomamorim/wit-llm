/* @test    @summary Test ASMifier.visitAttribute method for correct behavior    @run main ASMifierWitupTest */

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitAttributeWithASMifiable();
        testVisitAttributeWithNull();
    }

    private static void testVisitAttributeWithASMifiable() {
        // Create a mock ASMifiable attribute
        Attribute mockAttribute = new Attribute("TestType") {
            @Override
            public void asmify(StringBuilder buf, String name, java.util.Map<Label, String> labelNames) {
                buf.append("// Mock asmify output\n");
                buf.append("attributeName").append("\n");
            }
        };
        // Instantiate ASMifier
        ASMifier asmifier = new ASMifier();
        List<String> output = new ArrayList<>();
        // Override text list to capture output
        asmifier.text = output;
        // Call visitAttribute
        asmifier.visitAttribute(mockAttribute);
        // Verify output contains expected lines
        boolean containsComment = output.stream().anyMatch(s -> s.contains("// ATTRIBUTE TestType"));
        boolean containsAsmifyCall = output.stream().anyMatch(s -> s.contains("asmify"));
        boolean containsVisitCall = output.stream().anyMatch(s -> s.contains("visitAttribute"));
        boolean containsBraces = output.stream().anyMatch(s -> s.contains("}"));
        if (!containsComment || !containsAsmifyCall || !containsVisitCall || !containsBraces) {
            throw new AssertionError("visitAttribute with ASMifiable did not produce expected output: " + output);
        }
    }

    private static void testVisitAttributeWithNull() {
        // Instantiate ASMifier
        ASMifier asmifier = new ASMifier();
        List<String> output = new ArrayList<>();
        asmifier.text = output;
        // Call visitAttribute with null
        try {
            asmifier.visitAttribute(null);
            // Expect no exception, output should be unchanged
            if (!output.isEmpty()) {
                throw new AssertionError("Expected no output for null attribute, but got: " + output);
            }
        } catch (Exception e) {
            throw new AssertionError("visitAttribute threw exception on null attribute", e);
        }
    }
}
