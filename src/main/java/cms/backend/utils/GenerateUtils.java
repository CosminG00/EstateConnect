package cms.backend.utils;

public class GenerateUtils {
    // TODO a better generation algorithm
    public static String generateUsername(String email) {
        return email.split("@")[0];
    }
}
