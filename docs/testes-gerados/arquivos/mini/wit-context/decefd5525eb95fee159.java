/*
 * @test
 * @summary Test visitAttribute method of ASMifier for basic behavior and null handling
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;
import jdk.internal.org.objectweb.asm.Attribute;
import java.util.ArrayList;
import java.util.List;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitAttributeWithNull();
        testVisitAttributeWithCustomAttribute();
    }

    private static void testVisitAttributeWithNull() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitAttribute(null);
            // Expect no exception and text list to contain a string with "null" type
            List<String> texts = asmifier.getText();
            boolean found = false;
            for (String s : texts) {
                if (s.contains("null")) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new AssertionError("visitAttribute(null) did not add expected text");
            }
        } catch (Exception e) {
            throw new AssertionError("visitAttribute(null) threw exception: " + e);
        }
    }

    private static void testVisitAttributeWithCustomAttribute() {
        ASMifier asmifier = new ASMifier();
        // Custom Attribute implementing ASMifiable
        Attribute attr = new Attribute("TestType") {
            @Override
            public void asmify(StringBuffer buf, String varName, java.util.Map<jdk.internal.org.objectweb.asm.Label, String> labelNames) {
                buf.append("// asmify called\n");
            }
        };

        asmifier.visitAttribute(attr);

        List<String> texts = asmifier.getText();
        boolean containsAttributeComment = false;
        boolean containsAsmifyCall = false;
        boolean containsVisitAttributeCall = false;

        for (String s : texts) {
            if (s.contains("// ATTRIBUTE TestType")) {
                containsAttributeComment = true;
            }
            if (s.contains("// asmify called")) {
                containsAsmifyCall = true;
            }
            if (s.contains(".visitAttribute(attr);")) {
                containsVisitAttributeCall = true;
            }
        }

        if (!containsAttributeComment) {
            throw new AssertionError("Output missing attribute comment");
        }
        if (!containsAsmifyCall) {
            throw new AssertionError("asmify method was not called or output missing");
        }
        if (!containsVisitAttributeCall) {
            throw new AssertionError("visitAttribute call missing in output");
        }
    }
}
