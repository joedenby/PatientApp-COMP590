/**
 * ============================================================================
 * ================================== User ====================================
 * ============================================================================
 * Logged in users (see DBManager) are represented locally with this object.
 * All relevant information for the patient are stored as String for the user
 * to observe and change where applicable. Note that the password is not
 * present. Should a patient try to make a change later on, it would be expected
 * that a password should be given to access the database and overwrite the
 * previous entries. 
 *
 * @author Group23a
 * @email <jd744@kent.ac.uk> <lgb30@kent.ac.uk>
 */
public class User {
    
    //Representation for non-logged in user. Useful when checking if we have
    //user information to work with or not.
    public static User NULL = new User("NULL", "NULL", "NULL", "NULL", "NULL", "NULL");
    
    public String ID;
    public String firstName;
    public String lastName;
    public String email;
    public String dateofbirth;
    public String phonenumber;

    
    public User(String ID, String firstName, String lastName, String email, String dateofbith, String phonenumber) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateofbirth = dateofbith;
        this.phonenumber = phonenumber;
    }

    /**
     * Returns true if User object is equivilant to
     * static representaion of User. Typically this would
     * indicate the User is not logged in.
     * 
     * @return true if NULL
     */
    public boolean isNull() { 
        return equals(NULL);
    }

    /**
     * String representation of User as an object.
     * This includes all String values stored in the
     * class e.g. ID, first_name, email etc ...
     * 
     * @return String
     */
    @Override
    public String toString() {
        return 
            "ID: " + ID +
            "\nName: " + firstName + " " + lastName + 
            "\nDateOfBirth: " + dateofbirth + 
            "\nemail: " + email +
            "\nPhone:" + phonenumber; 
    }

}
