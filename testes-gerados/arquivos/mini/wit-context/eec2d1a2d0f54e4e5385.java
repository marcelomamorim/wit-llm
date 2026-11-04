/*
 * @test
 * @summary Test constructor of ModuleHashesBuilder.Graph for null edges argument
 * @run main ModuleHashesBuilderWitupTest
 */

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import jdk.internal.module.ModuleHashesBuilder;

public class ModuleHashesBuilderWitupTest {

    public static void main(String[] args) {
        testGraphConstructorNullEdges();
        testGraphConstructorEmptyCollections();
    }

    private static void testGraphConstructorNullEdges() {
        Set<String> nodes = new HashSet<>();
        nodes.add("moduleA");
        try {
            // edges == null should throw NullPointerException
            new ModuleHashesBuilder.Graph<String>(nodes, null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGraphConstructorEmptyCollections() {
        Set<String> nodes = Collections.emptySet();
        Map<String, Set<String>> edges = Collections.emptyMap();
        ModuleHashesBuilder.Graph<String> graph = new ModuleHashesBuilder.Graph<>(nodes, edges);
        if (graph.nodes() != nodes) {
            throw new RuntimeException("Graph nodes set not as expected");
        }
        if (graph.edges() != edges) {
            throw new RuntimeException("Graph edges map not as expected");
        }
    }
}
