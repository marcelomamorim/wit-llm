/*
 * @test
 * @summary Test getOffset() method of Label class for resolved and unresolved states
 * @run main LabelWitupTest
 */

import jdk.internal.org.objectweb.asm.Label;

public class LabelWitupTest {

    public static void main(String[] args) {
        testGetOffsetUnresolved();
        testGetOffsetResolved();
    }

    static void testGetOffsetUnresolved() {
        Label label = new Label();
        try {
            label.getOffset();
            System.out.println("FAIL: Expected IllegalStateException for unresolved label");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected exception for unresolved label");
        }
    }

    static void testGetOffsetResolved() {
        Label label = new Label();
        // Simulate resolved state
        // Since position is package-private, use reflection to set it for testing
        try {
            java.lang.reflect.Field positionField = Label.class.getDeclaredField("position");
            positionField.setAccessible(true);
            positionField.setInt(label, 42);
            java.lang.reflect.Field statusField = Label.class.getDeclaredField("status");
            statusField.setAccessible(true);
            statusField.setInt(label, Label.RESOLVED);
            int offset = label.getOffset();
            if (offset == 42) {
                System.out.println("PASS: getOffset() returned correct value when resolved");
            } else {
                System.out.println("FAIL: getOffset() returned incorrect value: " + offset);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("FAIL: Reflection error: " + e);
        }
    }
}
