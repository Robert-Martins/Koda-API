package com.robertmartins.notesapi.enums;

public enum UserRolesEnum {

    ADMIN("Admin", "Administrador"),
    MANAGER("Manager", "Gerente"),
    ANALYST("Analyst", "Analista"),
    REPORTER("Reporter", "Redator"),
    VIEWER("Viewer", "Observador");

    private final String name;

    private final String description;

    UserRolesEnum(final String name, final String description){
        this.name = name;
        this.description = description;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

}
