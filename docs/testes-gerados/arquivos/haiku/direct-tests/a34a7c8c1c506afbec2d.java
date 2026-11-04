/*
 * @test
 * @summary Verifies that Loader.close() reports the unreachable-state InternalError
 * @modules java.base/jdk.internal.loader
 * @run main LoaderWitupTest
 */

import java.lang.module.ResolvedModule;
import java.util.Collection;
import java.util.Collections;

import jdk.internal.loader.Loader;

public class LoaderWitupTest {

    public static void main(String[] args) throws Exception {
        Collection<ResolvedModule> modules = Collections.<ResolvedModule>emptyList();
        Loader loader = new Loader(modules, ClassLoader.getSystemClassLoader());

        boolean thrown = false;
        try {
            loader.close();
        } catch (InternalError e) {
            thrown = true;
            if (!"Should not get here".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected InternalError from close()");
        }
    }
}
