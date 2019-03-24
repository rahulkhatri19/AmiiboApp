package in.khatri.rahul.amiiboapp.utils;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.StringRequestListener;

import in.khatri.rahul.amiiboapp.utils.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class WebserviceHandler {
    private RequestBody requestBody;
    private Request request;
    private Context context;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    //  private ProgressDialog progressDialog;
    private SpotsDialog progressDialog;
    public WebServiceInterface serviceListener;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public WebserviceHandler(Context context){
        try {
            progressDialog = (SpotsDialog) new SpotsDialog.Builder().setContext(context).build();
            progressDialog.setMessage("Please Wait ...");
            progressDialog.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getGameData() {
        try {
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
         AndroidNetworking.get("https://www.amiiboapi.com/api/amiibo")
                .setTag(this).setPriority(Priority.HIGH).build().setAnalyticsListener(new AnalyticsListener() {
            @Override
            public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {

            }
        }).getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                serviceListener.onResponse(response);
            }

            @Override
            public void onError(ANError anError) {
                Log.d("ERROR", anError.getResponse() + anError.getErrorBody());
                anError.printStackTrace();
                // handle error
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

    }
}
