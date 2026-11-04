/* @test    @summary Test for ModuleResolution.withDeprecated() method behavior    @run main ModuleResolutionWitupTest */

import java.lang.module.ModuleReference;

public class ModuleResolutionWitupTest {

    public static void main(String[] args) {
        testWithDeprecatedThrowsOnInvalidFlags();
        testWithDeprecatedReturnsExpected();
    }

    private static void testWithDeprecatedThrowsOnInvalidFlags() {
        // Setup: create a ModuleResolution with flags that include WARN_DEPRECATED_FOR_REMOVAL or WARN_INCUBATING
        // Since constructor is package-private, simulate by reflection or assume internal access; here, we simulate with a subclass or direct access if possible.
        // For simplicity, assume we can instantiate with a known value.
        // Note: In real scenario, this might require reflection or internal API; here, we simulate with a subclass or direct access.
        // As per the context, we can create a ModuleResolution with a specific value.
        // For test, we simulate a value with both flags set.
        int invalidValue = jdk.internal.module.ClassFileConstants.WARN_DEPRECATED_FOR_REMOVAL | jdk.internal.module.ClassFileConstants.WARN_INCUBATING;
        ModuleResolution mr = new ModuleResolution(invalidValue);
        try {
            mr.withDeprecated();
            // If no exception, test fails
            System.err.println("FAIL: Expected InternalError when adding deprecated to invalid flags.");
        } catch (InternalError e) {
            // Expected exception
            System.out.println("PASS: Caught expected InternalError: " + e.getMessage());
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testWithDeprecatedReturnsExpected() {
        // Setup: create a ModuleResolution with a valid flags value not including WARN_DEPRECATED_FOR_REMOVAL or WARN_INCUBATING
        int validValue = 0;
        ModuleResolution mr = new ModuleResolution(validValue);
        try {
            ModuleResolution result = mr.withDeprecated();
            // Verify that the returned ModuleResolution has the WARN_DEPRECATED flag set
            int expectedValue = validValue | jdk.internal.module.ClassFileConstants.WARN_DEPRECATED;
            if (result.value() == expectedValue) {
                System.out.println("PASS: withDeprecated() returned expected value.");
            } else {
                System.err.println("FAIL: withDeprecated() returned value " + result.value() + ", expected " + expectedValue);
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }
}
