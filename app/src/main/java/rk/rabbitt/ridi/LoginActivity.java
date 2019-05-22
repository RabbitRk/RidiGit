package rk.rabbitt.ridi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rk.rabbitt.ridi.preference.PrefsManager;

import static rk.rabbitt.ridi.SplashScreen.LOG_TAG;

public class LoginActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    EditText password, phone_number;
    Button login;
    String passTxt, phoneTxt;
    String PuserTxt, PemailTxt, getId;
    Boolean succes;// ed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        validation();
    }

    private void validation() {
        phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (phone_number.getText().length() < 10) {
                    phone_number.setError("Please enter valid Phone Number");
                    phone_number.requestFocus();
                }
            }
        });


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (password.getText().length() < 6) {
                    password.setError("Enter the password");
                    password.requestFocus();
                }
            }
        });

    }

    private void init() {
        //volley requestqueue initialization
        requestQueue = Volley.newRequestQueue(this);

        password = findViewById(R.id.confirm_pass);
        phone_number = findViewById(R.id.phonenumber);
        login = findViewById(R.id.loading_btn);
        login.setTypeface(Typeface.SERIF);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginto();
            }
        });
    }

    private void loginto() {

        passTxt = password.getText().toString().trim();
        phoneTxt = phone_number.getText().toString().trim();

        if (TextUtils.isEmpty(phoneTxt)) {
            phone_number.setError("Enter the phone number");
            phone_number.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(passTxt)) {
            password.setError("Enter the password");
            password.requestFocus();
            return;
        }

        //Displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Logging in", "Please wait...", false, false);

        //Again creating the string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.USER_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.i(LOG_TAG, "Responce.............." + response);

                        try {
                            JSONArray arr = new JSONArray(response);
                            JSONObject jb = arr.getJSONObject(0);
                            getId = jb.getString("id");
                            PuserTxt = jb.getString("name");
                            PemailTxt = jb.getString("email");

                            setPrefsdetails();

                            new android.os.Handler().postDelayed(new Runnable() {
                                public void run() {
                                    //if details  is true
                                    succes = true;
                                    new android.os.Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            if (succes) {
                                                navigateto();
                                            }
                                        }
                                    }, 1000);
                                }
                            }, 1500);

                        } catch (JSONException e) {
                            Log.i(LOG_TAG, "Json error.............." + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Log.i(LOG_TAG, "volley error.............................." + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Username or Phone number not found", Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //Adding the parameters to the request
                params.put("passWord", passTxt);
                params.put("phoneNumber", phoneTxt);
                return params;
            }
        };
        //Adding request the the queue
        requestQueue.add(stringRequest);
    }

    private void navigateto() {
        Intent mainA = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainA);
        finish();
        Log.i(LOG_TAG, "Hid.............." + getId);
    }

    private void setPrefsdetails() {
        PrefsManager prefsManager = new PrefsManager(this);
        prefsManager.userPreferences(getId, PuserTxt, phoneTxt, PemailTxt);
        Log.i(LOG_TAG, "set preference Hid.............." + getId);
    }
}
