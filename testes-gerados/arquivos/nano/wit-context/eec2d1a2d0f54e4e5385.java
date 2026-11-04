/*
 * @test
 * @summary Tests for jdk.internal.module.ModuleHashesBuilder
 * @run main ModuleHashesBuilderWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import jdk.internal.module.ModuleHashesBuilder;

public class ModuleHashesBuilderWitupTest {

    public static void main(String[] args) {
        testGraphConstructorWithNullEdges();
    }

    private static void testGraphConstructorWithNullEdges() {
        Set<String> nodes = new HashSet<>();
        nodes.add("node1");
        nodes.add("node2");
        Map<String, Set<String>> edges = null;
        try {
            new ModuleHashesBuilder.Graph<>(nodes, edges);
            System.out.println("Test failed: Expected NullPointerException not thrown.");
        } catch (NullPointerException e) {
            System.out.println("Test passed: NullPointerException thrown as expected.");
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception type: " + e);
        }
    }
}
