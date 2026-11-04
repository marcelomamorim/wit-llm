/*
 * @test
 * @summary Verifies that a Loader built from an empty module collection keeps the supplied parent class loader.
 * @modules java.base/jdk.internal.loader
 * @run main LoaderWitupTest
 */
import java.util.Collections;
import jdk.internal.loader.Loader;

public class LoaderWitupTest {
    public static void main(String[] args) {
        ClassLoader parent = ClassLoader.getSystemClassLoader();
        Loader loader = new Loader(Collections.emptyList(), parent);
        if (loader.getParent() != parent) {
            throw new RuntimeException("Parent class loader was not preserved");
        }
        if (!(loader instanceof ClassLoader)) {
            throw new RuntimeException("Loader is not a ClassLoader");
        }
    }
}
