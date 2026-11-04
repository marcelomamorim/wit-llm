/*
 * @test
 * @summary Test System.definePackage method for expected behavior and exception path
 * @run main SystemWitupTest
 */

public class SystemWitupTest {
    public static void main(String[] args) {
        testDefinePackageWithValidInputs();
        testDefinePackageWithEmptyName();
    }

    private static void testDefinePackageWithValidInputs() {
        // We cannot instantiate ClassLoader or Module directly with public constructors,
        // so we test with the system class loader and a null module (allowed by signature)
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        String validName = "java.lang";
        java.lang.Module module = SystemWitupTest.class.getModule();

        try {
            Package pkg = java.lang.System.class.getMethod("definePackage", ClassLoader.class, String.class, java.lang.Module.class)
                    .invoke(null, cl, validName, module);
            // The method is instance method, not static, so above will throw IllegalArgumentException
            // Instead, call definePackage on ClassLoader directly
            Package p = cl.definePackage(validName, module);
            if (p == null) {
                throw new AssertionError("definePackage returned null for valid inputs");
            }
            if (!validName.equals(p.getName())) {
                throw new AssertionError("Package name mismatch");
            }
        } catch (ReflectiveOperationException e) {
            // Reflection used only to confirm method presence; fallback to direct call
            Package p = cl.definePackage(validName, module);
            if (p == null) {
                throw new AssertionError("definePackage returned null for valid inputs");
            }
            if (!validName.equals(p.getName())) {
                throw new AssertionError("Package name mismatch");
            }
        }
    }

    private static void testDefinePackageWithEmptyName() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        String emptyName = "";
        java.lang.Module module = SystemWitupTest.class.getModule();

        try {
            // According to WIT expath, empty name should cause InternalError from definePackage
            cl.definePackage(emptyName, module);
            throw new AssertionError("Expected InternalError not thrown for empty package name");
        } catch (InternalError expected) {
            // Expected path: InternalError thrown
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
