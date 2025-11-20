package app.utils;
import org.apache.commons.validator.routines.EmailValidator;

public class ValidationUtils {
    public static boolean isValidEmail(String email){
        return EmailValidator.getInstance().isValid(email);
    }
    public static boolean isValidRating(double r){ return r>=0 && r<=10; }
}
