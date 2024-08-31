package response;

import model.Person;

/**
 * The Response to be returned by the person function
 */
public class PersonsResponse extends Response {
    /**
     * An Array of all the people in the person table
     */
    private Person[] data;

    /**
     * Creates new Person Response
     * Case 1: Operation Successful, return array of people
     *
     * @param data new response's array of people
     */
    public PersonsResponse(Person[] data) {
        super(null, true);
        this.data = data;
    }

    /**
     * Creates new Person Response
     * Case2: Operation failed, return error message
     *
     * @param message new response's error message
     */
    public PersonsResponse(String message) {
        super(message, false);
    }

    public Person[] getData() {
        return data;
    }
}
