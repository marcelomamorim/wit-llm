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
        // The label is newly created, so status RESOLVED bit is not set.
        // Expect IllegalStateException when calling getOffset().
        try {
            label.getOffset();
            throw new Error("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected exception, test passes.
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Label offset position has not been resolved yet")) {
                throw new Error("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testGetOffsetReturnsPositionIfResolved() {
        jdk.internal.org.objectweb.asm.Label label = new jdk.internal.org.objectweb.asm.Label();
        // Use reflection to set private fields 'status' and 'position' to simulate resolved label.
        // This is necessary because there is no public API to set these fields.
        try {
            java.lang.reflect.Field statusField = jdk.internal.org.objectweb.asm.Label.class.getDeclaredField("status");
            java.lang.reflect.Field positionField = jdk.internal.org.objectweb.asm.Label.class.getDeclaredField("position");
            statusField.setAccessible(true);
            positionField.setAccessible(true);
            final int RESOLVED = 2;
            statusField.setInt(label, RESOLVED); // set RESOLVED bit
            int expectedPosition = 12345;
            positionField.setInt(label, expectedPosition);

            int offset = label.getOffset();
            if (offset != expectedPosition) {
                throw new Error("getOffset() returned " + offset + ", expected " + expectedPosition);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error("Reflection failed: " + e);
        }
    }
}
