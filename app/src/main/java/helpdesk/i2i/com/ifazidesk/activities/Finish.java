package helpdesk.i2i.com.ifazidesk.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;


public class Finish extends AppCompatActivity {
    Button button_newticket;
    Button button_exit;
    Button button_home;
    String string_Language = "";
    Preferences prefs;
    String string_PROGRESS_SUCCESS = "";
    String string_THANKYOU = "";
    String TicketResponseTime = "";
    Calendar calendar_countdown;
    TextView textView_timer;
    TextView textView_datetime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //getSupportActionBar().setCustomView(R.layout.actionbar);
        //getSupportActionBar().setDisplayShowCustomEnabled(true);
        prefs = new Preferences(getApplicationContext());
        button_newticket = (Button)findViewById(R.id.button_new);
        button_exit = (Button)findViewById(R.id.button_exit);
        button_home = (Button)findViewById(R.id.button_home);
        TextView textView_thankyou = (TextView)findViewById(R.id.tu);
        textView_datetime = (TextView)findViewById(R.id.ticketclosing);
        textView_timer = (TextView)findViewById(R.id.textview_reqhours);
        string_Language = prefs.getString("Language");
        TicketResponseTime = prefs.getString("Ticket_RT");
        try {
            JSONArray jsonArray_language = new JSONArray(string_Language);
            for(int i=0;i<jsonArray_language.length();i++)
            {
                String Control = jsonArray_language.getJSONObject(i).getString("Control");
                String langauge = jsonArray_language.getJSONObject(i).getString("langauge");
                if(Control.equals("textview_issuesubmitted"))
                {
                    string_PROGRESS_SUCCESS = langauge;
                }
                if(Control.equals("textview_thankyou"))
                {
                    string_THANKYOU = langauge;
                    getSupportActionBar().setTitle(string_THANKYOU);
                }
                if(Control.equals("textview_issuesubmitted"))
                {
                    textView_thankyou.setText(langauge);
                }
                if(Control.equals("button_gohome"))
                {
                    button_home.setText(langauge);
                }
                if(Control.equals("button_gonewissue"))
                {
                    button_newticket.setText(langauge);
                }
                if(Control.equals("button_goexit"))
                {
                    button_exit.setText(langauge);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        button_home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Intent ii9i=new Intent(Finish.this,DashboardNew.class);
                Intent ii9i = new Intent(Finish.this,NewDashboard.class);
                //Intent ii9i = new Intent(Finish.this,PreDashboard.class);
                ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ii9i);
            }
        });

        button_newticket.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent ii9i=new Intent(Finish.this,IssueDept.class);
                ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ii9i);
            }
        });


        button_exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                startActivity(intent);
                finish();
                System.exit(0);
                moveTaskToBack(true);

            }
        });

        //String ackwardRipOff = TicketResponseTime.replace("/Date(", "").replace(")/", "");
        //Long timeInMillis = Long.valueOf(ackwardRipOff);
        //textView_timer.setText(""+timeInMillis);
        Calendar rightNow = Calendar.getInstance();
        long long_systemtime = rightNow.getTimeInMillis();
        //Long timeInMilliseconds = timeInMillis - long_systemtime;


        calendar_countdown = Calendar.getInstance();
       // String string_monthdate = monthdate.format(calendar_countdown.getTime());

        //Date date1 = new Date(timeInMillis);

        Date date_monthformat = null;
        Date date1 = null;
        Date date2 = null;
        long diffinMilliseconds = 0L;

        //textView_timer.setVisibility(View.GONE);
            try {
                //Nov 25, 2017 00:20
                SimpleDateFormat dateformat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
                DateFormat monthdateformat = new SimpleDateFormat("MMM dd, yyyy hh:mm aa");
                date_monthformat = dateformat.parse(TicketResponseTime);
                textView_datetime.setText("Please wait, our helpdesk team will close this ticket on or before \n"+monthdateformat.format(date_monthformat));
                Calendar cal = Calendar.getInstance();
                Date d1 = dateformat.parse(TicketResponseTime);
                Date d2 = cal.getTime();
                System.out.println("DATE : "+d1+" "+d2);
                System.out.println("Formated :"+dateformat.format(d1)+" "+dateformat.format(d2));
                diffinMilliseconds = d1.getTime() - d2.getTime();
                Log.i("DAte1"," Date1: "+date1.getTime());
                Log.i("DAte2"," Date2: "+date2.getTime());
                Log.i("DiffinMS"," Milliseconds: "+diffinMilliseconds);
            }
            catch(ParseException e) {
                //java.text.ParseException: Unparseable date: Geting error
                System.out.println("Excep"+e);
            }
            //textview_hrs.setVisibility(View.VISIBLE);
            new CountDownTimer(diffinMilliseconds, 1000) {

                public void onTick(long millisUntilFinished) {

                    calendar_countdown.setTimeInMillis(millisUntilFinished);
                    calendar_countdown = Calendar.getInstance();
                    calendar_countdown.setTimeInMillis(millisUntilFinished);
                    DateFormat df = new SimpleDateFormat("HH:mm:ss");
                    df.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String date = df.format(calendar_countdown.getTime());
                    textView_timer.setText(date);
                    //handler_countdown.postDelayed(this, 0);
                    //mTextField.setText("Seconds remaining: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    //textView_timer.setText("Time Over\nEsc L1");
                }

            }.start();

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {
        @SuppressWarnings("unused")
        AlertDialog objAlertDialog = new AlertDialog.Builder(
                Finish.this, AlertDialog.THEME_HOLO_LIGHT)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                                startActivity(intent);
                                finish();
                                System.exit(0);
                                moveTaskToBack(true);
                            }
                        })
                .setNeutralButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        Intent ii9i=new Intent(Finish.this,LoginActivity.class);
                        ii9i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(ii9i);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                }).show();
    }
}
