package edu.uniritter.gpsapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.uniritter.gpsapp.R;
import edu.uniritter.gpsapp.models.Login;

public class MainActivity extends AppCompatActivity {

    private EditText user;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        if (login.Validate() == false){
            WarningMessage("Usuário ou senha incorretos!");
        }
        else{
            setResult(Activity.RESULT_OK);
            startActivity(new Intent(MainActivity.this, GpsActivity.class));
        }
    }
}