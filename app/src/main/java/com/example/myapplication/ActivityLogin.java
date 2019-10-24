package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.content.pm.Signature;

import com.example.myapplication.Database.Database;
import com.example.myapplication.Modal.ModalUser;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ActivityLogin extends AppCompatActivity {
    EditText edtUser, edtPass;
    Button btnLogin;
    SQLiteDatabase database;
    final String DATABASE_NAME = "IronBookStoreSQLite.sqlite";
    ModalUser modalUser;

    CheckBox ckbNhoMk;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        addEvents();
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.myapplication", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                xulysaukhilogin(loginResult);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }


    public void xulysaukhilogin(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());

                        // Application code
                        try {
                              AccessToken accessToken = AccessToken.getCurrentAccessToken();
//                              Log.v("hihi",accessToken.getUserId());
//                              939395516441354
                            Intent i = new Intent(ActivityLogin.this, MainActivity.class);

                            if (accessToken.getUserId().equals("939395516441354")){
                                edtUser.setText("admin");
                                i.putExtra("user", edtUser.getText().toString());

                            }startActivity(i);
                            overridePendingTransition(R.anim.anim_in,R.anim.anim_out);


                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(ActivityLogin.this, "loi" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email ");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void addControls() {
        edtUser = findViewById(R.id.edtUserName);

        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);

        ckbNhoMk = findViewById(R.id.ckbnhomk);
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BatLoiForm()) {
                    if (checkLogin()) {
                        Toast.makeText(ActivityLogin.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                        intent.putExtra("user", edtUser.getText().toString());
                        if (!ckbNhoMk.isChecked()) {
                            edtUser.setText("");
                            edtPass.setText("");
                        }
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                    } else {
                        Toast.makeText(ActivityLogin.this, "Thông tin đăng nhập sai", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private boolean checkLogin() {
        database = Database.initDatabase(this, DATABASE_NAME);
        String user = edtUser.getText().toString();
        String pass = edtPass.getText().toString();
        Cursor cursor = database.rawQuery("select * from tbNguoiDung", null);
        if (cursor.moveToFirst()) {
            do {
                String userdb = cursor.getString(3);
                if (userdb.equals(user)) {
                    String passdb = cursor.getString(4);
                    if (passdb.equals(pass)) {
                        return true;
                    } else return false;
                }
            } while (cursor.moveToNext());
        }
        return false;

    }

    public boolean BatLoiForm() {
        String user = edtUser.getText().toString();
        String pass = edtPass.getText().toString();
        if (user.length() == 0) {
            Toast.makeText(this, "Bạn chưa nhập Username", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pass.length() == 0) {
            Toast.makeText(this, "Bạn chưa nhập Password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (user.length() == 0 && pass.length() == 0) {
            Toast.makeText(this, "Bạn chưa nhập Username và Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
