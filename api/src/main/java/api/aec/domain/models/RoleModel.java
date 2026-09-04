package api.aec.domain.models;

import lombok.Getter;

@Getter
public enum RoleModel {
    ADMIN("A"),
    STAFF("S"),
    USER("U");

    private final String code;

    RoleModel(String code) {
        this.code = code;
    }

    public static RoleModel fromCode(String code) {
        for(RoleModel s : values()) {
            if(s.code.equals(code)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown role code: " + code);
    }
}
