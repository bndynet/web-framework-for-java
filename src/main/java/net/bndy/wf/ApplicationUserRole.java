package net.bndy.wf;

public enum ApplicationUserRole {
    Admin("Administrator"),
    GenericUser("Generic User");

    private final String description;

    public String getDescription() {
        return description;
    }

    ApplicationUserRole(String description){
        this.description = description;
    }
}
