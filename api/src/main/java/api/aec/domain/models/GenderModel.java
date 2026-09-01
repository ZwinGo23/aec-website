package api.aec.domain.models;

import lombok.Getter;

@Getter
public enum GenderModel {
    MALE("M"),
    FEMALE("F"),
    UNKNOWN("X");

    private final String code;

    GenderModel(String code) {
        this.code = code;
    }

    public static GenderModel fromCode(String code) {
        for(GenderModel s : values()) {
            if(s.getCode().equals((code))) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown gender code: " + code);
    }
}
