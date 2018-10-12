package helpdesk.i2i.com.ifazidesk.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;
import helpdesk.i2i.com.ifazidesk.scan.Scan;

public class ScanScreen extends AppCompatActivity {
    Button button_scan;
    Button button_details;
    ImageButton button_newticket;
    EditText editText_barcode;
    ImageView imageView_scancode;
    public Preferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_screen);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        prefs = new Preferences (getApplicationContext());
        button_scan = (Button)findViewById(R.id.button_scan);
        button_details = (Button)findViewById(R.id.button_getdetails);
        button_newticket = (ImageButton)findViewById(R.id.fab_image_button);
        editText_barcode = (EditText)findViewById(R.id.edittext_scancode);
        imageView_scancode = (ImageView)findViewById(R.id.imageview_camera);

        button_scan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ii9i=new Intent(ScanScreen.this,Scan.class);
                ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ii9i);
            }
        });

        imageView_scancode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ii9i=new Intent(ScanScreen.this,Scan.class);
                ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ii9i);
            }
        });

        button_details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //	new UserLoginAsync().execute();
                String qrcode=editText_barcode.getText().toString().trim();
                if(!qrcode.equals(""))
                {
                    qrcode = qrcode.replace('.', '-');
                    prefs.setString("id_qrcode", qrcode);
                    Intent ii9i=new Intent(ScanScreen.this,IssueDetails.class);
                    ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(ii9i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Enter the Code!\nOR\nScan Image",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        button_newticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii9i=new Intent(ScanScreen.this,IssueDept.class);
                ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ii9i);
            }
        });
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent ii9i=new Intent(ScanScreen.this,DashboardNew.class);
            ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(ii9i);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
