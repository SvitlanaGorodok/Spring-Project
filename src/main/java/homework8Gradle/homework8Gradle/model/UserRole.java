package homework8Gradle.homework8Gradle.model;

public enum UserRole {
    ROLE_USER ("ROLE_USER"),
    ROLE_ADMIN ("ROLE_ADMIN");
    final String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
