/*
 * @test
 * @summary WITUP generated checks for ClassLoader.getDefinedPackage null handling and unknown package lookup
 * @run main ClassLoaderWitupTest
 */

public class ClassLoaderWitupTest {

    public static void main(String[] args) throws Exception {
        ClassLoader loader = new ClassLoader() {};

        boolean thrown = false;
        try {
            loader.getDefinedPackage(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getDefinedPackage(null) must throw NullPointerException");
        }

        Package p = loader.getDefinedPackage("witup.missing.pkg");
        if (p != null) {
            throw new RuntimeException("getDefinedPackage for unknown package must return null");
        }
    }
}
