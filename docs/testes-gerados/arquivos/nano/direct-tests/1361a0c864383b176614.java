/*
 * @test
 * @summary Test java.lang.Class.forName(Module, String) method for expected behavior
 * @run main ClassWitupTest
 */

import java.lang.Class;
import java.lang.module.Module;
import java.lang.reflect.ReflectPermission;
import java.security.Permission;

public class ClassWitupTest {

    public static void main(String[] args) {
        testForNameWithSystemModule();
        testForNameWithCustomModule();
        testForNameWithNullModule();
        testForNameWithNullName();
    }

    private static void testForNameWithSystemModule() {
        try {
            Module systemModule = Object.class.getModule();
            String className = "java.lang.String";
            Class<?> cls = Class.forName(systemModule, className);
            assert cls != null : "Class should not be null";
            assert cls.getName().equals(className) : "Expected class name: " + className;
        } catch (Throwable t) {
            throw new AssertionError("Failed testForNameWithSystemModule", t);
        }
    }

    private static void testForNameWithCustomModule() {
        try {
            Module customModule = ClassWitupTest.class.getModule();
            String className = "java.lang.Object";
            Class<?> cls = Class.forName(customModule, className);
            assert cls != null : "Class should not be null";
            assert cls.getName().equals(className) : "Expected class name: " + className;
        } catch (Throwable t) {
            throw new AssertionError("Failed testForNameWithCustomModule", t);
        }
    }

    private static void testForNameWithNullModule() {
        try {
            Class.forName(null, "java.lang.String");
            throw new AssertionError("Expected NullPointerException for null module");
        } catch (NullPointerException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for null module", t);
        }
    }

    private static void testForNameWithNullName() {
        try {
            Module module = Object.class.getModule();
            Class.forName(module, null);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for null name", t);
        }
    }
}
