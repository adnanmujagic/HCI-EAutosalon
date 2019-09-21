package com.example.eautosalon.helpers;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import static com.example.eautosalon.fragments.VehicleListFragment.progressBar;

public class MyApiRequest {

    public static <T> void request(final Activity activity, final String url, final MyUrlConnection.HttpMethod httpMethod, final Object postObject, final MyRunnable<T> myCallback, final View view) {
        new AsyncTask<Void, Void, MyApiResult>() {

            @Override
            protected void onPreExecute() {
                if(view != null){
                    progressBar.setVisibility(view.VISIBLE);
                }
            }

            @Override
            protected MyApiResult doInBackground(Void... voids) {
                try{
                    String jsonPostObject = postObject==null?null:MyGson.build().toJson(postObject);
                    MyApiResult result = MyUrlConnection.request(MyConfig.baseUrl + url, httpMethod, jsonPostObject,"application/json");

                    return result;

                }catch(Exception er){
                    Log.e("Error: ", er.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(MyApiResult result) {

                if(view != null){
                    progressBar.setVisibility(view.GONE);
                }

                if (result.isException)
                {
                    View parentLayout = activity.findViewById(android.R.id.content);
                    Snackbar snackbar;
                    if (result.resultCode == 0)
                    {
                        snackbar = Snackbar.make(parentLayout, "Error while communicating with the server.", Snackbar.LENGTH_LONG);
                    }
                    else
                    {
                        snackbar = Snackbar.make(parentLayout, "Error " + result.resultCode + ": " + result.errorMessage, Snackbar.LENGTH_LONG);
                    }
                    snackbar.setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyApiRequest.request(activity, url, MyUrlConnection.HttpMethod.GET ,myCallback ,null, v);
                        }
                    });
                    snackbar.show();
                }else{
                    T data = MyGson.build().fromJson(result.value, myCallback.getGenericType());
                    myCallback.run(data);
                }
            }
        }.execute();
    }

    public static <T> void get(final Activity activity, final String urlAction, final MyRunnable<T> myCallback, View view)
    {
        request(activity, urlAction, MyUrlConnection.HttpMethod.GET, null, myCallback, view);
    }

    public static <T> void delete(Activity activity, String urlAction, MyRunnable<T> myCallback, View view)
    {
        request(activity, urlAction, MyUrlConnection.HttpMethod.DELETE, null, myCallback, view);
    }

    public static <T> void post(Activity activity, final String urlAction, Object postObject,  final MyRunnable<T> myCallback, View view)
    {
        request(activity, urlAction, MyUrlConnection.HttpMethod.POST, postObject, myCallback, view);
    }

    public static <T> void put(Activity activity, final String urlAction, Object postObject,  final MyRunnable<T> myCallback, View view)
    {
        request(activity, urlAction, MyUrlConnection.HttpMethod.PUT, postObject, myCallback, view);
    }

}
