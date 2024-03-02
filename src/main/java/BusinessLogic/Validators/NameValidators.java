package BusinessLogic.Validators;

import Model.Client;

/**
 * @Author Andreea Onaci
 * This class is validating the name of the client
 */
public class NameValidators implements Validators<Client> {
    @Override
    public void validate(Client t) {
        boolean valid;
        String pattern = "^[a-zA-Z]+([a-zA-Z\\s]*)$";
        valid = t.getName().matches(pattern);
        if (!valid)
            throw new IllegalArgumentException("The name contains unknown characters!");
    }
}
