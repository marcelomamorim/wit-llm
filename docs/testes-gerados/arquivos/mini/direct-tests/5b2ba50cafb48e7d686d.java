/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.Textifier.visitArray method
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier t = new Textifier();

        // Test visitArray with null name
        Textifier resultNull = t.visitArray(null);
        List<String> textNull = t.getText();
        if (textNull.isEmpty()) {
            throw new AssertionError("Text should not be empty after visitArray with null");
        }
        String firstLineNull = textNull.get(textNull.size() - 3);
        if (!firstLineNull.startsWith("{")) {
            // Actually firstLineNull is like "name={" or "{"
            // But since name is null, it should start with '{'
            if (!firstLineNull.equals("{")) {
                throw new AssertionError("Expected '{' in text for null name, got: " + firstLineNull);
            }
        }
        if (resultNull == null) {
            throw new AssertionError("visitArray should not return null");
        }

        // Test visitArray with non-null name
        Textifier t2 = new Textifier();
        Textifier resultName = t2.visitArray("myName");
        List<String> textName = t2.getText();
        if (textName.isEmpty()) {
            throw new AssertionError("Text should not be empty after visitArray with name");
        }
        String firstLineName = textName.get(textName.size() - 3);
        if (!firstLineName.startsWith("myName=")) {
            throw new AssertionError("Expected text to start with 'myName=', got: " + firstLineName);
        }
        if (!firstLineName.endsWith("{")) {
            throw new AssertionError("Expected text to end with '{', got: " + firstLineName);
        }
        if (resultName == null) {
            throw new AssertionError("visitArray should not return null");
        }

        // Check that the returned Textifier is a new instance
        if (resultName == t2) {
            throw new AssertionError("visitArray should return a new Textifier instance");
        }

        System.out.println("All tests passed.");
    }
}
