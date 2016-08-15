package com.android4dev.navigationview;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by priya on 7/16/2015.
 */
public class ServerRequests {
    ProgressDialog progressDialog;
    String name;
    protected Context context;
    public static final int CONNECTION_TIMEOUT = 15000;
    public static final String SERVER_ADDRESS = "http://suvojitkar365.esy.es/cloud_message/";

    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    public ServerRequests(Context context)
    {


        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait..");

    }

    public void storeDataInBackground(Contact contact , GetUserCallback callback)
    {
        progressDialog.show();
        new StoreDataAsyncTask(contact, callback).execute();


    }

    public void fetchDataInBackground(Contact contact , GetUserCallback callback)
    {
        progressDialog.show();
       // new FetchDataAsyncTask(contact, callback).execute();


    }

    public class StoreDataAsyncTask extends AsyncTask<Void , Void , Void>
    {
        Contact contact;
        GetUserCallback callback;

        public StoreDataAsyncTask(Contact contact , GetUserCallback callback)
        {
            this.contact = contact;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<NameValuePair> data_to_send = new ArrayList<>();
            data_to_send.add(new BasicNameValuePair("sender" , contact.name));
            data_to_send.add(new BasicNameValuePair("reciever" , contact.recipient));
            data_to_send.add(new BasicNameValuePair("message" , contact.textBody));
            Log.d("sumit",contact.name);
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams , CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams , CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "save_chat.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(data_to_send));
                client.execute(post);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            try {
                callback.done(null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            super.onPostExecute(aVoid);
        }
    }

   /* public class FetchDataAsyncTask extends AsyncTask<Void , Void , Contact>
    {
        Contact contact;
        GetUserCallback callback;

        public FetchDataAsyncTask(Contact contact , GetUserCallback callback)
        {
            this.contact = contact;
            this.callback = callback;
        }


        @Override
        protected Contact doInBackground(Void... voids) {
            ArrayList<NameValuePair> data_to_send = new ArrayList<>();
            data_to_send.add(new BasicNameValuePair("Username" , contact.username));
            data_to_send.add(new BasicNameValuePair("Password" , contact.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams , CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams , CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserData.php");

            Contact retunedContact = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(data_to_send));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);


                JSONObject jsonObject = new JSONObject(result);
                 retunedContact = null;

                    if(jsonObject.length() == 0)
                    {
                        retunedContact = null;

                    }
                    else
                    {
                        String name,email;
                        name = null;
                        email=null;

                        if(jsonObject.has("name"))
                            name = jsonObject.getString("name");
                        if(jsonObject.has("email"))
                            email =jsonObject.getString("email");

                        retunedContact = new Contact(name , email , contact.username , contact.password);

                    }

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return retunedContact;
        }
        @Override
        protected void onPostExecute(Contact returnedContact) {
            progressDialog.dismiss();
            callback.done(returnedContact);
            super.onPostExecute(returnedContact);
        }

    }*/
}
