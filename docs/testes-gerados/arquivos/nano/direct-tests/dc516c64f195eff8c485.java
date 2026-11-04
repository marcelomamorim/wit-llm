/* @test    @summary Test ModuleResolution.withIncubating() behavior    @run main ModuleResolutionWitupTest */

import static jdk.internal.module.ClassFileConstants.*;
import jdk.internal.module.ModuleResolution;

public class ModuleResolutionWitupTest {

    public static void main(String[] args) {
        testWithIncubatingWhenNoFlags();
        testWithIncubatingWhenWarnDeprecatedSet();
        testWithIncubatingWhenWarnDeprecatedForRemovalSet();
        testWithIncubatingWhenWarnIncubatingSet();
        testWithIncubatingWhenWarnDeprecatedAndWarnDeprecatedForRemovalSet();
    }

    static void testWithIncubatingWhenNoFlags() {
        ModuleResolution mr = ModuleResolution.empty();
        ModuleResolution result = mr.withIncubating();
        assert result.value() == 8 : "Expected value 8, got " + result.value();
    }

    static void testWithIncubatingWhenWarnDeprecatedSet() {
        ModuleResolution mr = new ModuleResolution(WARN_DEPRECATED);
        try {
            mr.withIncubating();
            assert false : "Expected InternalError when WARN_DEPRECATED is set";
        } catch (InternalError e) {
            assert e.getMessage().contains("cannot add incubating") : "Unexpected error message: " + e.getMessage();
        }
    }

    static void testWithIncubatingWhenWarnDeprecatedForRemovalSet() {
        ModuleResolution mr = new ModuleResolution(WARN_DEPRECATED_FOR_REMOVAL);
        try {
            mr.withIncubating();
            assert false : "Expected InternalError when WARN_DEPRECATED_FOR_REMOVAL is set";
        } catch (InternalError e) {
            assert e.getMessage().contains("cannot add incubating") : "Unexpected error message: " + e.getMessage();
        }
    }

    static void testWithIncubatingWhenWarnIncubatingSet() {
        ModuleResolution mr = new ModuleResolution(WARN_INCUBATING);
        try {
            mr.withIncubating();
            assert false : "Expected InternalError when WARN_INCUBATING is set";
        } catch (InternalError e) {
            assert e.getMessage().contains("cannot add incubating") : "Unexpected error message: " + e.getMessage();
        }
    }

    static void testWithIncubatingWhenWarnDeprecatedAndWarnDeprecatedForRemovalSet() {
        ModuleResolution mr = new ModuleResolution(WARN_DEPRECATED | WARN_DEPRECATED_FOR_REMOVAL);
        try {
            mr.withIncubating();
            assert false : "Expected InternalError when WARN_DEPRECATED and WARN_DEPRECATED_FOR_REMOVAL are set";
        } catch (InternalError e) {
            assert e.getMessage().contains("cannot add incubating") : "Unexpected error message: " + e.getMessage();
        }
    }
}
