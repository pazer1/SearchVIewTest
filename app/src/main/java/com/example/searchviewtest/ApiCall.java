package com.example.searchviewtest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
public class ApiCall {

    private static ApiCall mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    public ApiCall(Context ctx){
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized ApiCall getInstance(Context context){
        if(mInstance == null){
            mInstance = new ApiCall(context);
        }
        return  mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){

        getRequestQueue().add(req);
    }

    public static void make(Context ctx, Response .Listener<String>listener,Response.ErrorListener errorListener){

        String url = "https://api.pandascore.co/lol/matches?&token=gniyEx4IMnR8yYGPbS6PgefBnN7FY8mKqIq4a2_inj___Dtwkik";

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,listener,errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(stringRequest);
    }
}