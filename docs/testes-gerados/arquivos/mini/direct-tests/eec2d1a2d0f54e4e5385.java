/*
 * @test
 * @summary Test constructor of Graph class in ModuleHashesBuilder
 * @run main ModuleHashesBuilderWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ModuleHashesBuilderWitupTest {

    public static void main(String[] args) {
        testGraphConstructorWithEmptyCollections();
        testGraphConstructorWithNonEmptyCollections();
    }

    private static void testGraphConstructorWithEmptyCollections() {
        Set<String> emptyNodes = Collections.emptySet();
        Map<String, Set<String>> emptyEdges = Collections.emptyMap();

        jdk.internal.module.ModuleHashesBuilder.Graph<String> graph =
            new jdk.internal.module.ModuleHashesBuilder.Graph<String>(emptyNodes, emptyEdges);

        if (graph.nodes == null) {
            throw new AssertionError("Graph.nodes should not be null");
        }
        if (graph.edges == null) {
            throw new AssertionError("Graph.edges should not be null");
        }
        if (!graph.nodes.isEmpty()) {
            throw new AssertionError("Graph.nodes should be empty");
        }
        if (!graph.edges.isEmpty()) {
            throw new AssertionError("Graph.edges should be empty");
        }
    }

    private static void testGraphConstructorWithNonEmptyCollections() {
        Set<String> nodes = new HashSet<String>();
        nodes.add("moduleA");
        nodes.add("moduleB");

        Map<String, Set<String>> edges = new HashMap<String, Set<String>>();
        Set<String> edgesForA = new HashSet<String>();
        edgesForA.add("moduleB");
        edges.put("moduleA", edgesForA);

        jdk.internal.module.ModuleHashesBuilder.Graph<String> graph =
            new jdk.internal.module.ModuleHashesBuilder.Graph<String>(nodes, edges);

        if (graph.nodes == null) {
            throw new AssertionError("Graph.nodes should not be null");
        }
        if (graph.edges == null) {
            throw new AssertionError("Graph.edges should not be null");
        }
        if (graph.nodes.size() != 2 || !graph.nodes.contains("moduleA") || !graph.nodes.contains("moduleB")) {
            throw new AssertionError("Graph.nodes content incorrect");
        }
        if (graph.edges.size() != 1) {
            throw new AssertionError("Graph.edges size incorrect");
        }
        Set<String> retrievedEdges = graph.edges.get("moduleA");
        if (retrievedEdges == null || retrievedEdges.size() != 1 || !retrievedEdges.contains("moduleB")) {
            throw new AssertionError("Graph.edges content incorrect");
        }

        // Test unmodifiable nature
        try {
            graph.nodes.add("moduleC");
            throw new AssertionError("Graph.nodes should be unmodifiable");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            graph.edges.put("moduleB", new HashSet<String>());
            throw new AssertionError("Graph.edges should be unmodifiable");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            retrievedEdges.add("moduleC");
            throw new AssertionError("Graph.edges.get() set should be unmodifiable");
        } catch (UnsupportedOperationException expected) {
        }
    }
}
