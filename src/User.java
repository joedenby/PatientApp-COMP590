public class User {
    
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
