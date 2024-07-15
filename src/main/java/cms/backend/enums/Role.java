package cms.backend.enums;

public enum Role {
    AGENCY,
    CLIENT;

    @Override
    public String toString() {
        String s = name().toLowerCase();
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
