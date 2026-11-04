/*
 * @test
 * @summary Verify Label.getOffset throws IllegalStateException for an unresolved label
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main LabelWitupTest
 */
import jdk.internal.org.objectweb.asm.Label;

public class LabelWitupTest {
    public static void main(String[] args) {
        Label label = new Label();
        try {
            label.getOffset();
            throw new RuntimeException("Expected IllegalStateException");
        } catch (IllegalStateException e) {
        }
    }
}
