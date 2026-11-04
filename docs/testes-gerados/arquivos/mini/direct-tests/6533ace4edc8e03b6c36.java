/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.Label.getOffset() behavior
 * @run main LabelWitupTest
 */

public class LabelWitupTest {

    public static void main(String[] args) {
        testGetOffsetThrowsIfNotResolved();
        testGetOffsetReturnsPositionIfResolved();
    }

    private static void testGetOffsetThrowsIfNotResolved() {
        jdk.internal.org.objectweb.asm.Label label = new jdk.internal.org.objectweb.asm.Label();
        try {
            label.getOffset();
            throw new Error("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (!"Label offset position has not been resolved yet".equals(msg)) {
                throw new Error("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testGetOffsetReturnsPositionIfResolved() {
        jdk.internal.org.objectweb.asm.Label label = new jdk.internal.org.objectweb.asm.Label();
        // Use reflection to set private fields 'status' and 'position' since no public setter
        try {
            java.lang.reflect.Field statusField = jdk.internal.org.objectweb.asm.Label.class.getDeclaredField("status");
            java.lang.reflect.Field positionField = jdk.internal.org.objectweb.asm.Label.class.getDeclaredField("position");
            statusField.setAccessible(true);
            positionField.setAccessible(true);
            // Set RESOLVED bit
            int RESOLVED = 2;
            statusField.setInt(label, RESOLVED);
            int testPosition = 12345;
            positionField.setInt(label, testPosition);

            int offset = label.getOffset();
            if (offset != testPosition) {
                throw new Error("Expected offset " + testPosition + " but got " + offset);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error("Reflection failed: " + e);
        }
    }
}
