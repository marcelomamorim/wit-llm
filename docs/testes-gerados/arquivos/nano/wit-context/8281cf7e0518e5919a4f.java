/*
 * @test
 * @summary Tests for jdk.internal.misc.VM.getSavedProperty
 * @run main VMWitupTest
 */

import static java.lang.Thread.State.*;
import java.util.Map;
import java.util.Properties;

public class VMWitupTest {

    public static void main(String[] args) {
        testGetSavedPropertyWithNullProps();
        testGetSavedPropertyWithValidKey();
    }

    private static void testGetSavedPropertyWithNullProps() {
        // Simulate the scenario where savedProps is null
        // Since savedProps is a private static field, we cannot set it directly.
        // Instead, we invoke getSavedProperty with a key that triggers the exception.
        try {
            // The method throws if savedProps == null, which is the case initially.
            jdk.internal.misc.VM.getSavedProperty("anyKey");
            // If no exception, the test fails
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
            assert e.getMessage().equals("Not yet initialized") : "Unexpected message: " + e.getMessage();
        }
    }

    private static void testGetSavedPropertyWithValidKey() {
        // To test normal behavior, we need to set savedProps to a non-null value.
        // Since savedProps is private and no public setter exists, this test cannot manipulate it directly.
        // Therefore, this test is limited to the initial state where savedProps is null.
        // Alternatively, if the code is modified to allow setting savedProps, we could test this.
        // For now, we acknowledge this limitation.
        System.out.println("Skipping testGetSavedPropertyWithValidKey due to access limitations.");
    }
}
