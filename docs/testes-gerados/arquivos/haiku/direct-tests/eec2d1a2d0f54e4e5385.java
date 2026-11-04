/*
 * @test
 * @summary Verifies the Graph constructor of ModuleHashesBuilder rejects null arguments and accepts valid ones
 * @modules java.base/jdk.internal.module
 * @run main ModuleHashesBuilderWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ModuleHashesBuilderWitupTest {

    private static final String GRAPH_CLASS = "jdk.internal.module.ModuleHashesBuilder$Graph";

    static Constructor<?> graphConstructor() throws Exception {
        Class<?> c = Class.forName(GRAPH_CLASS);
        Constructor<?> ctor = c.getDeclaredConstructor(Set.class, Map.class);
        ctor.setAccessible(true);
        return ctor;
    }

    static Throwable constructWithCause(Set<String> nodes, Map<String, Set<String>> edges) throws Exception {
        try {
            graphConstructor().newInstance(nodes, edges);
            return null;
        } catch (InvocationTargetException e) {
            return e.getCause();
        }
    }

    public static void main(String[] args) throws Exception {
        Set<String> nodes = new HashSet<>(Arrays.asList("a", "b"));
        Map<String, Set<String>> edges = new HashMap<>();
        edges.put("a", Collections.singleton("b"));

        // valid arguments must construct without error
        Throwable t = constructWithCause(nodes, edges);
        if (t != null) {
            throw new AssertionError("valid arguments rejected: " + t);
        }

        // valid arguments with empty collections must also construct
        t = constructWithCause(new HashSet<String>(), new HashMap<String, Set<String>>());
        if (t != null) {
            throw new AssertionError("empty arguments rejected: " + t);
        }

        // null nodes: Collections.unmodifiableSet(null) throws NullPointerException
        t = constructWithCause(null, edges);
        if (!(t instanceof NullPointerException)) {
            throw new AssertionError("expected NullPointerException for null nodes, got: " + t);
        }

        // null edges: Collections.unmodifiableMap(null) throws NullPointerException
        t = constructWithCause(nodes, null);
        if (!(t instanceof NullPointerException)) {
            throw new AssertionError("expected NullPointerException for null edges, got: " + t);
        }
    }
}
