package Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by DELL on 10/13/2017.
 */

public class MarshMallowPermission extends Activity{

    public static final int RECORD_PERMISSION_REQUEST_CODE = 1;
    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 3;

    AlertDialog.Builder builder;
    Activity activity;

    public MarshMallowPermission(Activity activity) {
        this.activity = activity;
    }

    public boolean checkPermissionForRecord(){
        int result = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.RECORD_AUDIO);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPermissionForExternalStorage(){
        int result = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!(result == PackageManager.PERMISSION_GRANTED)){
            return false;
        } else {
            return true;
        }

    }

    public boolean checkPermissionForCamera(){
        int result = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }


    public void requestPermissionForRecord(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.RECORD_AUDIO)){
            Toast.makeText(activity, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{android.Manifest.permission.RECORD_AUDIO},RECORD_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForExternalStorage(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            builder = new AlertDialog.Builder(activity);

            builder.setTitle("Permission Denied");
            //builder.setIcon(R.drawable.ic_launcher);
            builder.setMessage("External Storage permission needed. Please allow in App Settings for additional functionality");

            builder.setNegativeButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {


                        }
                    });
            builder.show();


        } else {
            ActivityCompat.requestPermissions(activity,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForCamera(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.CAMERA)){
            builder = new AlertDialog.Builder(activity);

            builder.setTitle("Permission Denied");
            //builder.setIcon(R.drawable.ic_launcher);
            builder.setMessage("Camera permission needed. Please allow in App Settings for Camera use");

            builder.setNegativeButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {


                        }
                    });
            builder.show();
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{android.Manifest.permission.CAMERA},CAMERA_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // open_camera();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        Toast.makeText(activity,"Please allow camera permission. Camera cannot be used without it",Toast.LENGTH_LONG).show();
                    }

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
