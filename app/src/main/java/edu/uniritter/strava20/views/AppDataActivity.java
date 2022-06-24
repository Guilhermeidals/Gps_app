package edu.uniritter.strava20.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.uniritter.strava20.R;
import edu.uniritter.strava20.services.config.ConfigService;
import edu.uniritter.strava20.services.sqlite.DBService;

public class AppDataActivity extends AppCompatActivity {

    private TextView distanceText;
    private TextView movingTimeText;
    private TextView downtimeText;
    private DBService db = new DBService(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_data);


        InitializeObjects();
    }

    private void InitializeObjects(){
        distanceText = (TextView) findViewById(R.id.distanceData);
        double distance = db.GetDistance();
        distanceText.setText(Double.toString(distance));

        movingTimeText = findViewById(R.id.movimentTimeData);
        double movingTime = db.GetTimeMoving();
        movingTimeText.setText(Double.toString(movingTime));

        downtimeText = findViewById(R.id.dowtimeData);
        double downtime = db.GetDowntime();
        downtimeText.setText(Double.toString(downtime));
    }
}