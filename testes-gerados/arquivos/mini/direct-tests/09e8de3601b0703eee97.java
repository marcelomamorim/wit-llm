/*
 * @test
 * @summary Test java.lang.Module.addReads(Module) method behavior
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import jdk.internal.reflect.Reflection;

public class ModuleWitupTest {

    public static void main(String[] args) throws Exception {
        testAddReadsWithNull();
        testAddReadsOnUnnamedModuleReturnsThis();
        testAddReadsOnNamedModuleWithCallerMismatchThrows();
        testAddReadsOnNamedModuleWithCallerMatchSucceeds();
    }

    private static void testAddReadsWithNull() {
        Module m = ModuleWitupTest.class.getModule();
        try {
            m.addReads(null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testAddReadsOnUnnamedModuleReturnsThis() {
        // Unnamed module: isNamed() == false
        ClassLoader cl = ModuleWitupTest.class.getClassLoader();
        Module unnamedModule = cl.getUnnamedModule();
        Module other = ModuleWitupTest.class.getModule();
        Module result = unnamedModule.addReads(other);
        if (result != unnamedModule) {
            throw new RuntimeException("addReads on unnamed module should return this");
        }
    }

    private static void testAddReadsOnNamedModuleWithCallerMismatchThrows() throws Exception {
        // We need a named module different from the caller module to trigger IllegalCallerException
        Module thisModule = ModuleWitupTest.class.getModule();
        if (!thisModule.isNamed()) {
            // If test class is in unnamed module, skip this test
            return;
        }

        // Create a dummy module instance via reflection to simulate a different module
        // Since Module constructor is not public, we cannot create a new Module instance directly
        // Instead, we use the current module as 'other' and call addReads on a proxy module that is different

        // We simulate caller mismatch by invoking addReads via reflection from a different caller
        // The method addReads checks caller module via Reflection.getCallerClass()

        // To simulate caller mismatch, we create a helper class that calls addReads on thisModule
        try {
            CallerMismatchHelper.callAddReads(thisModule, thisModule);
            throw new RuntimeException("Expected IllegalCallerException");
        } catch (InvocationTargetException ite) {
            Throwable cause = ite.getCause();
            if (!(cause instanceof IllegalCallerException)) {
                throw new RuntimeException("Expected IllegalCallerException, got " + cause, cause);
            }
            // expected
        }
    }

    private static void testAddReadsOnNamedModuleWithCallerMatchSucceeds() {
        Module thisModule = ModuleWitupTest.class.getModule();
        if (!thisModule.isNamed()) {
            // If test class is in unnamed module, skip this test
            return;
        }
        // Calling addReads on this module with other module should succeed
        Module other = thisModule;
        Module result = thisModule.addReads(other);
        if (result != thisModule) {
            throw new RuntimeException("addReads should return this module");
        }
    }

    static class CallerMismatchHelper {
        static void callAddReads(Module target, Module other) throws Exception {
            Method m = Module.class.getDeclaredMethod("addReads", Module.class);
            m.invoke(target, other);
        }
    }
}
