package laby;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import laby.contact.ContactList;
import laby.task.TaskList;

/** Tests contact-related console output produced by {@link Ui}. */
class UiTest {

    @Test
    void getContacts_displaysNumberedContacts() {
        TaskList taskList = new TaskList(new ArrayList<>());
        ContactList contactList = new ContactList(new ArrayList<>());
        contactList.addContact("John Doe", "91234567", "john@example.com");

        assertEquals("Here are the tasks and contacts in your list:\n\n"
                        + "1.John Doe | Phone: 91234567 | Email: john@example.com\n\n",
                Ui.getItems(taskList, contactList));
    }

    @Test
    void contactMessages_displayContactAndCount() {
        String contact = "John Doe | Phone: 91234567 | Email: john@example.com";

        assertEquals("Laby has added the contact. Did Chief find a better assistant :(\n  " + contact + "\n",
                Ui.getAddContact(contact));
        assertEquals("Laby has deleted the contact. Seems like Laby is enough :D\n  " + contact + "\n",
                Ui.getDeleteContact(contact));
        assertEquals("There is a total of 1 contact in your list.\n",
                Ui.getNumberOfContacts(1));
    }
}
