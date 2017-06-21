package uk.co.lost_boyproductions.sonycameraremotecontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.Toast;

// Start of Camera snippet
import android.graphics.PixelFormat;
import android.hardware.camera2;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import java.io.IOException;
// End of Camera snippet

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, SurfaceHolder.Callback {

    ImageView cameraMonitor;
    SeekBar lensZoom;
    int lensZoomPosition = 50;
    int lensZoomOldPosition = 50; // set to the same value as android.progress in layout definition

    // Start of Camera snippet
//    @SuppressWarnings("deprecation")    // this suppresses the warning that android.hardware.Camera is deprecation
    Camera camera; // camera class variable
    SurfaceView camView; // drawing camera preview using this variable
    SurfaceHolder surfaceHolder; // variable to hold surface for surfaceView which means display
    boolean camCondition = false;  // conditional variable for camera preview checking and set to false
    // End of Camera snippet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        lensZoom.setMax(100);     // crashes app
//        lensZoom.setMin(0);       // crashes app

        lensZoom=(SeekBar)findViewById(R.id.lensZoom);
        lensZoom.setOnSeekBarChangeListener(this);

        // Start of Camera snippet
        getWindow().setFormat(PixelFormat.UNKNOWN);  // getWindow() to get window and set it's pixel format which is UNKNOWN
        camView = (SurfaceView) findViewById(R.id.camerapreview);  // refering the id of surfaceView
        surfaceHolder = camView.getHolder();   // getting access to the surface of surfaceView and return it to surfaceHolder
        surfaceHolder.addCallback(this);  // adding call back to this context means MainActivity
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);  // to set surface type
        // End of Camera snippet


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

    // Start of Camera snippet
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        if(camCondition){  // stop the camera
            camera.stopPreview(); // stop preview using stopPreview() method
            camCondition = false; // setting camera condition to false means stop
        }

        if (camera != null){   // condition to check whether your device have camera or not
            try {
                camera.setPreviewDisplay(surfaceHolder); // setting preview of camera
                camera.startPreview();  // starting camera preview
                camCondition = true; // setting camera to true which means having camera
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();   // opening camera
        camera.setDisplayOrientation(90);   // setting camera preview orientation
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();  // stopping camera preview
        camera.release();       // releasing camera
        camera = null;          // setting camera to null when left
        camCondition = false;   // setting camera condition to false also when exit from application
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    // End of Camera snippet
}
