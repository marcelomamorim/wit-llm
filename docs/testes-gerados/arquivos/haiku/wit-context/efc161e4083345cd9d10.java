/*
 * @test
 * @summary Checks Loader.getResource null handling and missing-resource behavior
 * @modules java.base/jdk.internal.loader
 * @run main LoaderWitupTest
 */

import java.lang.module.ResolvedModule;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

import jdk.internal.loader.Loader;

public class LoaderWitupTest {

    public static void main(String[] args) throws Exception {
        Collection<ResolvedModule> modules = Collections.<ResolvedModule>emptyList();

        Loader loader = new Loader(modules, ClassLoader.getSystemClassLoader());

        boolean npe = false;
        try {
            loader.getResource(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("getResource(null) must throw NullPointerException");
        }

        URL missing = loader.getResource("witup/generated/no/such/resource.txt");
        if (missing != null) {
            throw new RuntimeException("missing resource must yield null, got: " + missing);
        }

        Loader rootParentLoader = new Loader(modules, null);
        URL missing2 = rootParentLoader.getResource("witup/generated/no/such/resource.txt");
        if (missing2 != null) {
            throw new RuntimeException("missing resource with null parent must yield null, got: " + missing2);
        }

        boolean npe2 = false;
        try {
            rootParentLoader.getResource(null);
        } catch (NullPointerException e) {
            npe2 = true;
        }
        if (!npe2) {
            throw new RuntimeException("getResource(null) with null parent must throw NullPointerException");
        }
    }
}
