/*
 * @test
 * @summary Tests for java.util.stream.Nodes class methods
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithSizeExceedingLength();
    }

    public static void testBeginWithValidSize() {
        Nodes node = new Nodes();
        try {
            // Invoca o método begin com um tamanho válido (exemplo: 0)
            node.begin(0L);
            System.out.println("testBeginWithValidSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithValidSize failed: " + e);
        }
    }

    public static void testBeginWithSizeExceedingLength() {
        Nodes node = new Nodes();
        try {
            // Invoca o método begin com um tamanho maior que length para disparar IllegalStateException
            node.begin(100L); // supondo que length seja menor que 100
            System.err.println("testBeginWithSizeExceedingLength failed: exception not thrown");
        } catch (IllegalStateException e) {
            System.out.println("testBeginWithSizeExceedingLength passed");
        } catch (Exception e) {
            System.err.println("testBeginWithSizeExceedingLength failed: " + e);
        }
    }
}
