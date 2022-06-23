package edu.uniritter.gpsapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;

import edu.uniritter.gpsapp.R;
import edu.uniritter.gpsapp.services.config.ConfigService;

public class ConfigActivity extends AppCompatActivity {
    private int Interval;
    private TextView intervalEditText;
    private Spinner DisplacementSpinner;
    private CheckBox SaveStopCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        InitializeObjects();
    }

    private void InitializeObjects(){

        intervalEditText = (TextView) findViewById(R.id.Interval);
        Interval = ConfigService.GetInterval(this);
        intervalEditText.setText(Interval);

        DisplacementSpinner = (Spinner) findViewById(R.id.displacementTypes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.displacementTypes,
                android.R.layout.simple_spinner_item);
        DisplacementSpinner.setAdapter(adapter);

        SaveStopCheckBox = (CheckBox) findViewById(R.id.SaveStopBox);
        SaveStopCheckBox.setActivated(ConfigService.GetSaveStop(this));
    }

    public void Less(View view){
        if (Interval > 3){
            Interval -= 1;
            intervalEditText.setText(Interval);
        }
    }

    public void More(View view){
        if (Interval < 180){
            Interval += 1;
            intervalEditText.setText(Interval);
        }
    }

    public void Save(View view){
        ConfigService.SetInterval(this, Integer.parseInt(intervalEditText.getText().toString()));
        ConfigService.SetDisplacement(this, DisplacementSpinner.getSelectedItem().toString());
        ConfigService.SetSaveStop(this, SaveStopCheckBox.isChecked());
    }
}