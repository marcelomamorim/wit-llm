/*
 * @test
 * @summary Test java.lang.Module.addUses method behavior
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {

    public static void main(String[] args) throws Exception {
        testAddUsesWithNull();
        testAddUsesOnUnnamedModuleReturnsThis();
        testAddUsesOnNamedModuleWithCallerThis();
        testAddUsesOnNamedModuleWithCallerNotThisThrows();
    }

    private static void testAddUsesWithNull() {
        Module module = ModuleWitupTest.class.getModule();
        try {
            module.addUses(null);
            throw new RuntimeException("Expected NullPointerException for null service");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testAddUsesOnUnnamedModuleReturnsThis() {
        // Unnamed module: isNamed() == false, so no exception and no implAddUses call
        Module unnamedModule = new Object() {
            Module getModule() {
                return ModuleWitupTest.class.getModule();
            }
        }.getModule();

        // The unnamed module of the bootstrap loader is unnamed
        if (unnamedModule.isNamed()) {
            // fallback: find an unnamed module by creating a new class loader
            ClassLoader cl = new ClassLoader() {};
            unnamedModule = cl.getUnnamedModule();
        }

        Module result = unnamedModule.addUses(String.class);
        if (result != unnamedModule) {
            throw new RuntimeException("addUses should return this for unnamed module");
        }
    }

    private static void testAddUsesOnNamedModuleWithCallerThis() throws Exception {
        // This test requires a named module and that getCallerModule returns this
        // We simulate this by calling addUses from a method in this class
        // and expecting no exception
        Module module = ModuleWitupTest.class.getModule();
        if (!module.isNamed()) {
            // skip test if module is unnamed
            return;
        }

        // Call addUses from a helper method to simulate caller == this
        addUsesCallerThis(module, String.class);
    }

    private static void addUsesCallerThis(Module module, Class<?> service) {
        // This method calls addUses on the module
        // If caller != this, addUses throws IllegalCallerException
        // We expect no exception here
        module.addUses(service);
    }

    private static void testAddUsesOnNamedModuleWithCallerNotThisThrows() throws Exception {
        // This test attempts to call addUses with a caller module different from this
        // Since getCallerModule uses Reflection.getCallerClass(), we cannot easily fake it
        // So we test by invoking addUses via reflection from a different class

        Module module = ModuleWitupTest.class.getModule();
        if (!module.isNamed()) {
            // skip test if module is unnamed
            return;
        }

        // Use a helper class to call addUses on the module
        Class<?> helperClass = HelperCaller.class;
        try {
            java.lang.reflect.Method m = helperClass.getDeclaredMethod("callAddUses", Module.class, Class.class);
            m.invoke(null, module, String.class);
            throw new RuntimeException("Expected IllegalCallerException from addUses");
        } catch (java.lang.reflect.InvocationTargetException ite) {
            Throwable cause = ite.getCause();
            if (!(cause instanceof IllegalCallerException)) {
                throw new RuntimeException("Expected IllegalCallerException but got " + cause, cause);
            }
            // expected
        }
    }

    public static class HelperCaller {
        public static void callAddUses(Module module, Class<?> service) {
            module.addUses(service);
        }
    }
}
