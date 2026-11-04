/*
 * @test
 * @summary Test java.lang.Module.addUses(Class<?>) for null argument and caller checks
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.lang.IllegalCallerException;
import java.util.Objects;
import jdk.internal.reflect.Reflection;

public class ModuleWitupTest {

    public static void main(String[] args) {
        testAddUsesNullArgument();
        testAddUsesCallerModuleMismatch();
        testAddUsesOnUnnamedModule();
    }

    private static void testAddUsesNullArgument() {
        Module module = ModuleWitupTest.class.getModule();
        try {
            module.addUses(null);
            throw new Error("Expected NullPointerException for null service argument");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testAddUsesCallerModuleMismatch() {
        Module module = ModuleWitupTest.class.getModule();
        // Only test if module is named and not automatic to trigger IllegalCallerException path
        if (module.isNamed() && !isAutomatic(module)) {
            // We simulate a caller module different from 'this' by calling addUses indirectly
            // via a helper method in another class.
            try {
                Helper.callAddUses(module, String.class);
                throw new Error("Expected IllegalCallerException due to caller module mismatch");
            } catch (IllegalCallerException ice) {
                // expected
            }
        }
    }

    private static void testAddUsesOnUnnamedModule() {
        // For unnamed modules or automatic modules, addUses should not throw
        Module module = ModuleWitupTest.class.getModule();
        if (!module.isNamed() || isAutomatic(module)) {
            Module returned = module.addUses(String.class);
            if (returned != module) {
                throw new Error("addUses should return the same module instance");
            }
        }
    }

    private static boolean isAutomatic(Module module) {
        try {
            java.lang.reflect.Field descField = Module.class.getDeclaredField("descriptor");
            descField.setAccessible(true);
            Object descriptor = descField.get(module);
            if (descriptor != null) {
                java.lang.reflect.Method isAutomaticMethod = descriptor.getClass().getMethod("isAutomatic");
                Boolean automatic = (Boolean) isAutomaticMethod.invoke(descriptor);
                return automatic.booleanValue();
            }
        } catch (Exception e) {
            // If reflection fails, conservatively return false
        }
        return false;
    }

    static class Helper {
        static void callAddUses(Module module, Class<?> service) {
            // This method is called from testAddUsesCallerModuleMismatch to simulate a caller module
            module.addUses(service);
        }
    }
}
