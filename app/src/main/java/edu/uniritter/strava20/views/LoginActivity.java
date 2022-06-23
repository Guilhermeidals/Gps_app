package edu.uniritter.strava20.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import edu.uniritter.strava20.R;
import edu.uniritter.strava20.models.Login;

public class LoginActivity extends AppCompatActivity {

    private EditText user;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitializeObjects();
    }


    public void InitializeObjects(){
        user = (EditText) findViewById(R.id.LoginText);
        password = (EditText) findViewById(R.id.PasswordText);
    }

    public void WarningMessage(String message){
        new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .setCancelable(false)
                .show();
    }
    public void Login(View view){
        Login login = new Login();

        login.setUser(user.getText().toString());
        login.setPassword(password.getText().toString());

        if (login.Validate(this) == false){
            WarningMessage("Usuário ou senha incorretos!");
        }
        else{
            setResult(Activity.RESULT_OK);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }
}