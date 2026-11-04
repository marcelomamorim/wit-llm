/*
 * @test
 * @summary Testa o construtor padrão de java.security.Permissions
 * @run main PermissionsWitupTest
 */

import java.security.Permissions;

public class PermissionsWitupTest {

    public static void main(String[] args) {
        testPermissionsConstructor();
    }

    public static void testPermissionsConstructor() {
        Permissions permissions = new Permissions();
        if (permissions == null) {
            throw new AssertionError("Permissions instance should not be null");
        }
        // Verifica se permsMap não é nulo após construção
        try {
            java.lang.reflect.Field permsMapField = Permissions.class.getDeclaredField("permsMap");
            permsMapField.setAccessible(true);
            Object permsMapValue = permsMapField.get(permissions);
            if (permsMapValue == null) {
                throw new AssertionError("permsMap should be initialized and not null");
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Reflection failed to access permsMap", e);
        }
    }
}
