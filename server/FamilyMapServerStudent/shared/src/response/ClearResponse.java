package response;

/**
 * The response to be returned from the clear function
 */
public class ClearResponse extends Response {

    /**
     * Creates new clear response
     *
     * @param message new response message
     * @param success new response success
     */
    public ClearResponse(String message, boolean success) {
        super(message,success);
    }
}
