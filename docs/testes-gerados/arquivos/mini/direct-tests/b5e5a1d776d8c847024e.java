/*
 * @test
 * @summary Test ModuleResolution.withDeprecated() method behavior
 * @run main ModuleResolutionWitupTest
 */

public class ModuleResolutionWitupTest {

    // Constants from ClassFileConstants as per source context
    private static final int WARN_DEPRECATED = 0x1;
    private static final int WARN_DEPRECATED_FOR_REMOVAL = 0x2;
    private static final int WARN_INCUBATING = 0x4;

    public static void main(String[] args) {
        testWithDeprecatedAddsFlag();
        testWithDeprecatedThrowsIfDeprecatedForRemovalSet();
        testWithDeprecatedThrowsIfIncubatingSet();
    }

    private static void testWithDeprecatedAddsFlag() {
        // Start with empty resolution (value=0)
        jdk.internal.module.ModuleResolution mr = new jdk.internal.module.ModuleResolution(0);
        jdk.internal.module.ModuleResolution result = mr.withDeprecated();
        int val = result.value();
        if ((val & WARN_DEPRECATED) == 0) {
            throw new AssertionError("withDeprecated() did not set WARN_DEPRECATED flag");
        }
        // Original value should remain unchanged
        if (mr.value() != 0) {
            throw new AssertionError("Original ModuleResolution value changed");
        }
    }

    private static void testWithDeprecatedThrowsIfDeprecatedForRemovalSet() {
        // value with WARN_DEPRECATED_FOR_REMOVAL set
        int val = WARN_DEPRECATED_FOR_REMOVAL;
        jdk.internal.module.ModuleResolution mr = new jdk.internal.module.ModuleResolution(val);
        try {
            mr.withDeprecated();
            throw new AssertionError("Expected InternalError not thrown when WARN_DEPRECATED_FOR_REMOVAL flag set");
        } catch (InternalError e) {
            String msg = e.getMessage();
            if (!msg.contains("cannot add deprecated")) {
                throw new AssertionError("InternalError message unexpected: " + msg);
            }
        }
    }

    private static void testWithDeprecatedThrowsIfIncubatingSet() {
        // value with WARN_INCUBATING set
        int val = WARN_INCUBATING;
        jdk.internal.module.ModuleResolution mr = new jdk.internal.module.ModuleResolution(val);
        try {
            mr.withDeprecated();
            throw new AssertionError("Expected InternalError not thrown when WARN_INCUBATING flag set");
        } catch (InternalError e) {
            String msg = e.getMessage();
            if (!msg.contains("cannot add deprecated")) {
                throw new AssertionError("InternalError message unexpected: " + msg);
            }
        }
    }
}
