package response;

/**
 * The response to be returned from the load function
 */
public class LoadResponse extends Response {
    /**
     * Creates new load response
     *
     * @param message new response's message
     * @param success new response's success
     */
    public LoadResponse(String message, boolean success) {
        super(message, success);
    }
}
