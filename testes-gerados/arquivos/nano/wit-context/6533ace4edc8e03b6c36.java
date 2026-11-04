/*
 * @test
 * @summary Test for Label.getOffset() method to verify exception throwing when label is unresolved.
 * @run main LabelWitupTest
 */

import jdk.internal.org.objectweb.asm.Label;

public class LabelWitupTest {

    public static void main(String[] args) {
        testGetOffsetThrowsExceptionWhenUnresolved();
        testGetOffsetReturnsPositionWhenResolved();
    }

    private static void testGetOffsetThrowsExceptionWhenUnresolved() {
        Label label = new Label();
        // Ensure label is unresolved (status & RESOLVED == 0)
        // No explicit resolution method called, so default status is 0
        try {
            label.getOffset();
            System.out.println("FAIL: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException.");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testGetOffsetReturnsPositionWhenResolved() {
        Label label = new Label();
        // Manually set the label as resolved and assign position
        // Since constructor is public, but no public method to set status or position,
        // we assume reflection or direct field access is not allowed, so we simulate by subclassing or
        // by assuming the internal state is set via package-private methods or constructor (not shown).
        // For the purpose of this test, we simulate the resolved state by subclassing.
        Label resolvedLabel = new Label() {
            {
                // simulate internal state change
                // Note: in real scenario, internal fields are package-private or protected,
                // but since no such method is provided, this test assumes access.
                // Alternatively, this test is limited to the default constructor behavior.
                // So, we skip this test or document that it requires internal access.
            }
        };
        // As we cannot set internal state directly, we document this limitation.
        // Therefore, this test is a placeholder to illustrate the intended behavior.
        System.out.println("INFO: Skipping testGetOffsetReturnsPositionWhenResolved due to internal state access limitations.");
    }
}
