/*
 * @test
 * @summary Testa o construtor de LinkPermission com entradas inválidas.
 * @run main LinkPermissionWitupTest
 */

import java.nio.file.LinkPermission;

public class LinkPermissionWitupTest {

    public static void main(String[] args) {
        testInvalidName();
        testInvalidActions();
    }

    private static void testInvalidName() {
        try {
            new LinkPermission("invalidName");
            System.err.println("Falha: esperado IllegalArgumentException para nome inválido.");
        } catch (IllegalArgumentException e) {
            // Esperado
        }
    }

    private static void testInvalidActions() {
        try {
            new LinkPermission("hard", "nonEmptyActions");
            System.err.println("Falha: esperado IllegalArgumentException para ações não vazias.");
        } catch (IllegalArgumentException e) {
            // Esperado
        }
    }
}
