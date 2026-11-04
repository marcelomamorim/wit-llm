/*
 * @test
 * @summary Test visitLineNumber method of ASMifier for basic functionality and stability
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;
import jdk.internal.org.objectweb.asm.Label;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();
        Label label = new Label();

        // Test with a normal line number and label
        asmifier.visitLineNumber(42, label);

        // Test with zero line number
        asmifier.visitLineNumber(0, label);

        // Test with negative line number (should not throw, just produce output)
        asmifier.visitLineNumber(-1, label);

        // Test with null label (should not throw, but label is null)
        try {
            asmifier.visitLineNumber(10, null);
        } catch (NullPointerException e) {
            // Acceptable if NPE occurs because label is null, but no other exceptions
            System.out.println("Caught expected NullPointerException for null label");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null label", e);
        }

        // No exceptions expected for any of the above calls except possible NPE for null label
        System.out.println("ASMifier.visitLineNumber tests completed successfully.");
    }
}
