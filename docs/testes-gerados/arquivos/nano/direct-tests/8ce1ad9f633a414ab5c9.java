/*
 * @test
 * @summary Test the visitAttribute method of Textifier class for basic behavior.
 * @run main TextifierWitupTest
 */

import java.util.ArrayList;
import java.util.List;

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        new TextifierWitupTest().testVisitAttribute();
    }

    public void testVisitAttribute() {
        Textifier textifier = new Textifier();
        List<String> output = new ArrayList<>();

        // Create a dummy Attribute with a type and a textify implementation
        Attribute attr = new Attribute("TestType") {
            @Override
            public void textify(java.lang.StringBuilder buf, Object arg) {
                buf.append("textified attribute\n");
            }
        };

        // Call visitAttribute and capture output
        textifier.visitAttribute(attr);

        // Verify that output contains expected strings
        boolean containsAttribute = false;
        for (String line : textifier.text) {
            if (line.contains("ATTRIBUTE")) {
                containsAttribute = true;
                break;
            }
        }

        if (!containsAttribute) {
            throw new AssertionError("Output does not contain ATTRIBUTE line") ;
        }

        boolean containsTextified = false;
        for (String line : textifier.text) {
            if (line.contains("textified attribute")) {
                containsTextified = true;
                break;
            }
        }

        if (!containsTextified) {
            throw new AssertionError("Output does not contain textified attribute") ;
        }
    }
}
