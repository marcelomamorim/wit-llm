/*
 * @test
 * @summary Verifies ClassLoader.getResources null handling, parent-first ordering and empty results
 * @run main ClassLoaderWitupTest
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class ClassLoaderWitupTest {

    static class StubLoader extends ClassLoader {
        private final List<URL> urls;

        StubLoader(ClassLoader parent, List<URL> urls) {
            super(parent);
            this.urls = urls;
        }

        @Override
        protected Enumeration<URL> findResources(String name) {
            return Collections.enumeration(urls);
        }
    }

    static List<URL> collect(Enumeration<URL> e) {
        List<URL> result = new ArrayList<>();
        while (e.hasMoreElements()) {
            result.add(e.nextElement());
        }
        return result;
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        URL a = new URL("file:/tmp/witup/a.txt");
        URL p = new URL("file:/tmp/witup/parent.txt");
        URL c = new URL("file:/tmp/witup/child.txt");
        String missing = "witup/none/Missing.resource";

        // null name must be rejected
        StubLoader single = new StubLoader(null, Arrays.asList(a));
        try {
            single.getResources(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }

        // no parent: results come from findResources only (boot finds nothing for missing name)
        List<URL> r1 = collect(single.getResources(missing));
        check(r1.equals(Arrays.asList(a)), "single loader result: " + r1);

        // parent results come first, then local findResources results
        StubLoader parent = new StubLoader(null, Arrays.asList(p));
        StubLoader child = new StubLoader(parent, Arrays.asList(c));
        List<URL> r2 = collect(child.getResources(missing));
        check(r2.equals(Arrays.asList(p, c)), "parent-first ordering: " + r2);

        // empty local results with no parent yield an empty enumeration
        StubLoader empty = new StubLoader(null, Collections.<URL>emptyList());
        List<URL> r3 = collect(empty.getResources(missing));
        check(r3.isEmpty(), "expected empty result: " + r3);
    }
}
