package pl.wroc.uni.ii.guard.common.enums;

import java.util.Arrays;

public enum Role {
    ADMIN(0, "ROLE_ADMIN"),
    USER(1, "ROLE_USER"),
    CREATOR(2, "ROLE_CREATOR");

    private final int id;
    private final String name;;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Role fromId(int id) {
        return Arrays.stream(Role.values()).filter(role -> role.id == id).findFirst().orElseThrow(() -> new EnumConstantNotPresentException(Role.class, "No role with id  #" + id + " is defined"));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
