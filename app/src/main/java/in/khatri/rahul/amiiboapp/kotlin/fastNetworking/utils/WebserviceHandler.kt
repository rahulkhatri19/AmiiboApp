package `in`.khatri.rahul.amiiboapp.kotlin.fastNetworking.utils

import `in`.khatri.rahul.amiiboapp.java.utils.dialog.SpotsDialog
import android.content.Context
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener

class WebserviceHandler {
    private var progressDialog: SpotsDialog? = null
    var serviceListener: WebServiceInterface? = null
//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    fun WebserviceHandler(context: Context?) {
        try {
            progressDialog = SpotsDialog.Builder().setContext(context).build() as SpotsDialog
            progressDialog!!.setMessage("Please Wait ...")
            progressDialog!!.setCancelable(false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getGameData() {
      /*  try {
            progressDialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }*/
        AndroidNetworking.get("https://www.amiiboapi.com/api/amiibo")
                .setTag(this).setPriority(Priority.HIGH).build().setAnalyticsListener { timeTakenInMillis, bytesSent, bytesReceived, isFromCache -> }.getAsString(object : StringRequestListener {
                    override fun onResponse(response: String) {
                        Log.e("Response", response)
//                        if (progressDialog!!.isShowing) progressDialog!!.dismiss()
                        serviceListener!!.onResponse(response)
                    }

                    override fun onError(anError: ANError) {
                        Log.d("ERROR", anError.response.toString() + anError.errorBody)
                        anError.printStackTrace()
                        // handle error
//                        if (progressDialog!!.isShowing) progressDialog!!.dismiss()
                    }
                })
    }
}