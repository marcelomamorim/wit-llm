/*
 * @test
 * @summary Test jdk.internal.loader.Loader.getResource(String) for null argument and delegation
 * @run main LoaderWitupTest
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import jdk.internal.loader.Loader;
import java.lang.module.ResolvedModule;

public class LoaderWitupTest {

    // Minimal stub for ResolvedModule to allow Loader construction
    static class DummyResolvedModule implements ResolvedModule {
        public String name() { return "dummy.module"; }
        public ModuleDescriptor descriptor() { return null; }
        public ModuleReference reference() { return null; }
        public boolean isAutomatic() { return false; }
        public boolean isOpen() { return false; }
        public boolean isExported(String pn, ModuleReference mr) { return false; }
        public boolean isQualifiedExport(String pn) { return false; }
        public boolean isQualifiedOpen(String pn) { return false; }
        public boolean isOpen(String pn) { return false; }
        public boolean isExported(String pn) { return false; }
        public boolean isQualifiedExport(String pn, ModuleReference mr) { return false; }
        public boolean isQualifiedOpen(String pn, ModuleReference mr) { return false; }
    }

    public static void main(String[] args) throws Exception {
        testGetResourceNullName();
        testGetResourceDelegation();
    }

    private static void testGetResourceNullName() {
        // Construct Loader with dummy module and null parent
        Collection<ResolvedModule> modules = new ArrayList<>();
        modules.add(new DummyResolvedModule());
        Loader loader = new Loader(modules, null);

        try {
            loader.getResource(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testGetResourceDelegation() {
        // Construct Loader with dummy module and a parent ClassLoader
        Collection<ResolvedModule> modules = new ArrayList<>();
        modules.add(new DummyResolvedModule());

        ClassLoader parent = new ClassLoader() {
            @Override
            public URL getResource(String name) {
                if ("parentResource".equals(name)) {
                    try {
                        return new URL("file:/parent/resource");
                    } catch (Exception e) {
                        return null;
                    }
                }
                return null;
            }
        };

        Loader loader = new Loader(modules, parent);

        // The resource name that is not found locally, so delegation to parent occurs
        URL url = loader.getResource("parentResource");
        if (url == null || !"file:/parent/resource".equals(url.toString())) {
            throw new RuntimeException("Expected resource URL delegated to parent");
        }

        // The resource name that is not found locally or in parent, should return null
        URL missing = loader.getResource("nonexistentResource");
        if (missing != null) {
            throw new RuntimeException("Expected null for missing resource");
        }
    }
}
