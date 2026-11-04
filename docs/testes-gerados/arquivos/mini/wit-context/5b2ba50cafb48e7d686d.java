/*
 * @test
 * @summary Deterministic tests for Textifier.visitArray(String) method
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitArrayWithNullName();
        testVisitArrayWithNonNullName();
    }

    private static void testVisitArrayWithNullName() {
        Textifier textifier = new Textifier();
        Textifier returned = textifier.visitArray(null);
        // The returned Textifier should not be null
        if (returned == null) {
            throw new AssertionError("visitArray returned null when name is null");
        }
        // The original textifier's text list should contain the opening brace and closing brace strings
        java.util.List<String> text = textifier.getText();
        if (text == null || text.size() < 2) {
            throw new AssertionError("Text list is null or too small after visitArray with null name");
        }
        // The first element should contain '{'
        if (!text.get(0).contains("{")) {
            throw new AssertionError("First text element does not contain '{' when name is null");
        }
        // The last element should be "}"
        if (!"}".equals(text.get(text.size() - 1))) {
            throw new AssertionError("Last text element is not '}' when name is null");
        }
    }

    private static void testVisitArrayWithNonNullName() {
        Textifier textifier = new Textifier();
        String name = "testName";
        Textifier returned = textifier.visitArray(name);
        if (returned == null) {
            throw new AssertionError("visitArray returned null when name is non-null");
        }
        java.util.List<String> text = textifier.getText();
        if (text == null || text.size() < 2) {
            throw new AssertionError("Text list is null or too small after visitArray with non-null name");
        }
        // The first element should contain the name followed by '=' and '{'
        String first = text.get(0);
        if (!first.contains(name + "=") || !first.contains("{")) {
            throw new AssertionError("First text element does not contain name= and '{' when name is non-null");
        }
        // The last element should be "}"
        if (!"}".equals(text.get(text.size() - 1))) {
            throw new AssertionError("Last text element is not '}' when name is non-null");
        }
    }
}
