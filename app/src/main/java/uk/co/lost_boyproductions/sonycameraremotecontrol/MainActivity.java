package uk.co.lost_boyproductions.sonycameraremotecontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    ImageView cameraMonitor;
    SeekBar lensZoom;
    int lensZoomPosition = 50;
    int lensZoomOldPosition = 50; // set to the same value as android.progress in layout definition

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        lensZoom.setMax(100);     // crashes app
//        lensZoom.setMin(0);       // crashes app
        lensZoom=(SeekBar)findViewById(R.id.lensZoom);
        lensZoom.setOnSeekBarChangeListener(this);

/*        // Start of Camera2 Fragment
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }
        // End of Camera2 Fragment
*/
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
/*        if (progress > lensZoomPosition) { // this would be a zoom out action
            Toast.makeText(getApplicationContext(), "Increased", Toast.LENGTH_SHORT).show();
        } else { // this would be a zoom in action
            Toast.makeText(getApplicationContext(), "Decreased", Toast.LENGTH_SHORT).show();
        }
*/    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
//        Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        lensZoomPosition = lensZoom.getProgress(); //
        if (lensZoomOldPosition < lensZoomPosition) {
            Toast.makeText(getApplicationContext(),"seekbar final position is higher: " + lensZoomPosition, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"seekbar final position is lower: " + lensZoomPosition, Toast.LENGTH_SHORT).show();
        }
        lensZoomOldPosition = lensZoomPosition;
//        Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
    }

}
