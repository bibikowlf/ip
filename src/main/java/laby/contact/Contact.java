package laby.contact;

/** Represents a contact's name, phone number, and email address. */
public class Contact {
    private static final String FIELD_SEPARATOR = "|";
    private final String name;
    private final String phone;
    private final String email;

    /**
     * Creates a contact with the supplied details.
     *
     * @param name Contact's name.
     * @param phone Contact's phone number.
     * @param email Contact's email address.
     */
    public Contact(String name, String phone, String email) {
        assert isValidField(name) : "contact name cannot be empty or contain separators";
        assert isValidField(phone) : "contact phone cannot be empty or contain separators";
        assert isValidField(email) : "contact email cannot be empty or contain separators";

        this.name = name.trim();
        this.phone = phone.trim();
        this.email = email.trim();
    }

    /**
     * Returns the contact's name.
     *
     * @return Contact name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the contact's phone number.
     *
     * @return Contact phone number.
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Returns the contact's email address.
     *
     * @return Contact email address.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Returns the contact in the format shown in the console.
     *
     * @return Display representation of the contact.
     */
    @Override
    public String toString() {
        return this.name + " | Phone: " + this.phone + " | Email: " + this.email;
    }

    /**
     * Returns the contact in the format used for persistence.
     *
     * @return Serialized contact record without a trailing newline.
     */
    public String toFileString() {
        return String.join(FIELD_SEPARATOR, "C", this.name, this.phone, this.email);
    }

    /**
     * Checks whether a contact field can be represented in the storage format.
     *
     * @param field Field value to validate.
     * @return Whether the field is non-empty and contains no record delimiters.
     */
    private static boolean isValidField(String field) {
        return field != null && !field.trim().isEmpty()
                && !field.contains(FIELD_SEPARATOR)
                && !field.contains("\n")
                && !field.contains("\r");
    }
}
