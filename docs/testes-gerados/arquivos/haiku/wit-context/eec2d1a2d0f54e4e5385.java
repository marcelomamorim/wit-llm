/*
 * @test
 * @summary Checks that the Graph constructor rejects null nodes or edges with NullPointerException
 * @modules java.base/jdk.internal.module
 * @run main/othervm --add-opens java.base/jdk.internal.module=ALL-UNNAMED ModuleHashesBuilderWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ModuleHashesBuilderWitupTest {

    static Constructor<?> graphCtor() throws Exception {
        Class<?> c = Class.forName("jdk.internal.module.ModuleHashesBuilder$Graph");
        Constructor<?> k = c.getDeclaredConstructor(Set.class, Map.class);
        k.setAccessible(true);
        return k;
    }

    static Throwable construct(Set<String> nodes, Map<String, Set<String>> edges) throws Exception {
        try {
            graphCtor().newInstance(nodes, edges);
            return null;
        } catch (InvocationTargetException ex) {
            return ex.getCause();
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        Set<String> nodes = new HashSet<>();
        nodes.add("a");
        Map<String, Set<String>> edges = new HashMap<>();
        edges.put("a", new HashSet<>());

        Throwable ok = construct(nodes, edges);
        check(ok == null, "valid arguments must not throw, got " + ok);

        Throwable nullEdges = construct(nodes, null);
        check(nullEdges instanceof NullPointerException,
              "null edges must throw NullPointerException, got " + nullEdges);

        Throwable nullNodes = construct(null, edges);
        check(nullNodes instanceof NullPointerException,
              "null nodes must throw NullPointerException, got " + nullNodes);
    }
}
