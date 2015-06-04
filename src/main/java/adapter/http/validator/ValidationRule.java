package adapter.http.validator;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */


public class ValidationRule implements Rule {

    private String name;
    private String regex;
    private String errorMessage;


    public ValidationRule(String name, String errorMessage, String regex) {
        this.name = name;
        this.regex = regex;
        this.errorMessage = errorMessage;
    }

    @Override
    public String name() {
        return name;
    }


    @Override
    public boolean isValid(String value) {
        if (value.matches(regex)) return true;
        return false;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }

}