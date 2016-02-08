package laurie.giry.channelmessaging;

/**
 * Created by Wibou on 08/02/2016.
 */
public class Response {

    private String response;
    private int code;
    private String accesstoken;

    public Response(){}

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
