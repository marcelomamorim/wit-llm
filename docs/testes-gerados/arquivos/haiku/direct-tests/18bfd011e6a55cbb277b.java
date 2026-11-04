/*
 * @test
 * @summary Tests ModuleDescriptor.newModule argument validation and observable modifier handling
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Modifier;
import java.util.EnumSet;
import java.util.Set;

public class ModuleDescriptorWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    public static void main(String[] args) {
        automaticAloneIsAccepted();
        automaticWithOpenIsRejected();
        openOnlyIsReportedAsOpen();
        emptyModifiersAreNormalModule();
        nullModifiersThrowNpe();

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    static void automaticAloneIsAccepted() {
        ModuleDescriptor md = ModuleDescriptor.newModule("m.auto",
                EnumSet.of(Modifier.AUTOMATIC)).build();
        check(md.isAutomatic(), "AUTOMATIC alone should produce an automatic module");
        check(!md.isOpen(), "AUTOMATIC alone should not be open");
    }

    static void automaticWithOpenIsRejected() {
        Set<Modifier> ms = EnumSet.of(Modifier.AUTOMATIC, Modifier.OPEN);
        boolean thrown = false;
        try {
            ModuleDescriptor.newModule("m.bad", ms);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "AUTOMATIC combined with OPEN should throw IllegalArgumentException");
    }

    static void openOnlyIsReportedAsOpen() {
        ModuleDescriptor md = ModuleDescriptor.newModule("m.open",
                EnumSet.of(Modifier.OPEN)).build();
        check(md.isOpen(), "OPEN modifier should produce an open module");
        check(!md.isAutomatic(), "OPEN module should not be automatic");
    }

    static void emptyModifiersAreNormalModule() {
        ModuleDescriptor md = ModuleDescriptor.newModule("m.normal",
                EnumSet.noneOf(Modifier.class)).build();
        check(!md.isOpen(), "empty modifiers should not be open");
        check(!md.isAutomatic(), "empty modifiers should not be automatic");
    }

    static void nullModifiersThrowNpe() {
        boolean thrown = false;
        try {
            ModuleDescriptor.newModule("m.null", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null modifier set should throw NullPointerException");
    }
}
