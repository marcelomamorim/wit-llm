/*
 * @test
 * @summary Verify ClassLoader.getDefinedPackage null handling, absent packages and packages defined by a loader
 * @run main ClassLoaderWitupTest
 */

public class ClassLoaderWitupTest {

    static class TestLoader extends ClassLoader {
        TestLoader() {
            super(null);
        }

        Package define(String name) {
            return definePackage(name, "spec", "1.0", "vendor",
                                 "impl", "1.0", "vendor", null);
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        TestLoader loader = new TestLoader();

        boolean npe = false;
        try {
            loader.getDefinedPackage(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "getDefinedPackage(null) must throw NullPointerException");

        check(loader.getDefinedPackage("witup.absent") == null,
              "undefined package must yield null");

        loader.define("witup.pkg");
        Package p = loader.getDefinedPackage("witup.pkg");
        check(p != null, "defined package must be returned");
        check("witup.pkg".equals(p.getName()), "returned package must have requested name");

        check(loader.getDefinedPackage("witup.other") == null,
              "other undefined name must still yield null");

        Package q = loader.getDefinedPackage("witup.pkg");
        check(q != null && "witup.pkg".equals(q.getName()),
              "repeated lookup must still return the package");
    }
}
