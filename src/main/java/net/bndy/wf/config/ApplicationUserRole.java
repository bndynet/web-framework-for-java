package net.bndy.wf.config;

public enum ApplicationUserRole {
    Admin("Administrator, control all resources."),
    PublicUser("Public User, just access public resources.");

    private final String description;

    public String getDescription() {
        return description;
    }

    ApplicationUserRole(String description){
        this.description = description;
    }
}
