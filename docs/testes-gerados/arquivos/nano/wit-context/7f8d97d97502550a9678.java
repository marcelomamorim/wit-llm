/*
 * @test
 * @summary Test for java.lang.Module.isExported(String)
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {

    public static void main(String[] args) {
        testIsExportedWithNull();
        testIsExportedWithValidString();
    }

    private static void testIsExportedWithNull() {
        Module module = getSampleModule();
        try {
            module.isExported(null);
            System.out.println("FAIL: Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testIsExportedWithValidString() {
        Module module = getSampleModule();
        String validPackageName = "java.lang";
        try {
            boolean result = module.isExported(validPackageName);
            System.out.println("Result for 'java.lang': " + result);
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static Module getSampleModule() {
        return Module.class.getModule();
    }
}
