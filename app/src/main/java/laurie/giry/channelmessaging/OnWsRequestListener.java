package laurie.giry.channelmessaging;

/**
 * Created by Wibou on 02/02/2016.
 */
public interface OnWsRequestListener {
    public void OnSuccess(int requestCode, String result);
    public void OnError(int requestCode);
}
