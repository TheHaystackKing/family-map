package response;

/**
 * The response to be returned from the fill function
 */
public class FillResponse extends Response {

    /**
     * Creates new fill response
     *
     * @param message new response's message
     * @param success new response's success
     */
    public FillResponse(String message, boolean success) {
        super(message, success);
    }
}
