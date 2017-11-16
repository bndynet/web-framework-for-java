package net.bndy.wf;

public enum ApplicationUserRole {
    Admin("Administrator, control all resources."),
    CommonUser("Common User, access all resources but can not modify.");

    private final String description;

    public String getDescription() {
        return description;
    }

    ApplicationUserRole(String description){
        this.description = description;
    }
}
