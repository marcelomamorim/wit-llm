/*
 * @test
 * @summary Basic tests for ModuleHashesBuilder
 * @run main ModuleHashesBuilderWitupTest
 */

import java.lang.module.Configuration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import jdk.internal.module.ModuleHashesBuilder;

public class ModuleHashesBuilderWitupTest {

    public static void main(String[] args) {
        testGraphConstructorWithEmptyNodes();
        testGraphConstructorWithSingleNode();
        testGraphConstructorWithMultipleNodesAndEdges();
    }

    static void testGraphConstructorWithEmptyNodes() {
        Set<String> nodes = Collections.emptySet();
        Map<String, Set<String>> edges = Collections.emptyMap();
        ModuleHashesBuilder.Graph<String> graph = new ModuleHashesBuilder.Graph<>(nodes, edges);
        assert graph.nodes().isEmpty() : "Graph nodes should be empty";
        assert graph.edges().isEmpty() : "Graph edges should be empty";
    }

    static void testGraphConstructorWithSingleNode() {
        Set<String> nodes = new HashSet<>();
        nodes.add("moduleA");
        Map<String, Set<String>> edges = Collections.singletonMap("moduleA", Collections.emptySet());
        ModuleHashesBuilder.Graph<String> graph = new ModuleHashesBuilder.Graph<>(nodes, edges);
        assert graph.nodes().contains("moduleA") : "Graph should contain moduleA";
        assert graph.edges().get("moduleA").isEmpty() : "moduleA should have no edges";
    }

    static void testGraphConstructorWithMultipleNodesAndEdges() {
        Set<String> nodes = new HashSet<>();
        nodes.add("moduleA");
        nodes.add("moduleB");
        Map<String, Set<String>> edges = Map.of(
            "moduleA", Set.of("moduleB"),
            "moduleB", Set.of()
        );
        ModuleHashesBuilder.Graph<String> graph = new ModuleHashesBuilder.Graph<>(nodes, edges);
        assert graph.nodes().containsAll(Set.of("moduleA", "moduleB")) : "Graph should contain moduleA and moduleB";
        assert graph.edges().get("moduleA").contains("moduleB") : "moduleA should have edge to moduleB";
        assert graph.edges().get("moduleB").isEmpty() : "moduleB should have no edges";
    }
}
