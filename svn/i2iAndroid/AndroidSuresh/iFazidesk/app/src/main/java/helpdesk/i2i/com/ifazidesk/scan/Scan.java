package helpdesk.i2i.com.ifazidesk.scan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.activities.IssueDetails;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;

public class Scan extends Activity {
    static {

        try {
            System.loadLibrary("iconv");
        } catch (Exception e) {

        }

    }

    public Preferences prefs;
    TextView scanText;
    Button scanButton;
    ImageScanner scanner;
    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;
    private boolean barcodeScanned = false;
    private boolean previewing = true;

    private Runnable doAutoFocus = new Runnable() {
        @Override
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }

    };
    // Mimic continuous auto-focusing
    AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;

    }

    PreviewCallback previewCb = new PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Size size = parameters.getPreviewSize();
            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);


            int result = scanner.scanImage(barcode);

            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms) {
                    scanText.setText("Scan Result : " + sym.getData());
                    //  setContentView(R.layout.dialog);
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            Scan.this).create();

                    alertDialog.setTitle("Scanner Result");
                    //	alertDialog.setBackgroundColor(Color.YELLOW);
                    alertDialog.setMessage(sym.getData());
                    String code = sym.getData();
                    code = code.replace('.', '-');
                    prefs.setString("id_qrcode", code);
                    /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("id_qrcode", code);
                    editor.commit();*/
                    alertDialog.setIcon(R.drawable.bariconsmall);
                    alertDialog.setButton("Get Details", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            Toast.makeText(getApplicationContext(), "Fetching Details From Server",
                                    Toast.LENGTH_SHORT).show();

                            Intent ii9i = new Intent(Scan.this, IssueDetails.class);
                            ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(ii9i);

                        }
                    });
                    alertDialog.show();


                    barcodeScanned = true;
                }
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scan);
        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        // Instance barcode scanner
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        prefs = new Preferences(getApplicationContext());

        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        scanText = (TextView) findViewById(R.id.scanText);

        scanButton = (Button) findViewById(R.id.ScanButton);


        scanButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;
                    scanText.setText("Scanning...");
                    mCamera.setPreviewCallback(previewCb);
                    mCamera.startPreview();
                    previewing = true;
                    mCamera.autoFocus(autoFocusCB);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }

    }
}
