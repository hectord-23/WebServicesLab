package hectord.tacoma.uw.edu.webserviceslab.authenticate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.OutputStreamWriter;

import hectord.tacoma.uw.edu.webserviceslab.CourseActivity;
import hectord.tacoma.uw.edu.webserviceslab.LoginFragment;
import hectord.tacoma.uw.edu.webserviceslab.R;

public class SignInActivity extends AppCompatActivity implements LoginFragment.LoginInteractionListener{

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Check if the user is already logged in
        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
        if(!mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)){
            // load the Login Fragment
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment() )
                    .commit();
        } else {
            // take user to curses / normal view
            Intent i = new Intent(this, CourseActivity.class);
            startActivity(i);
            finish();
        }
    }

    /**
     * When the user logs in (prentend is successful, we remember that they are logged in and not
     * show this screen again but instead take the user to another fragment. In the login method
     * of the SignInActivity, make it start the CourseActivity.
     * @param userId The userId
     * @param pwd The users Password
     */
    @Override
    public void login(String userId, String pwd) {

        // check if network exist
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            // TODO Check if the login and password are valid
//            new LoginTask().execute(url);

            // Saving the userId and password to internal file on the device
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                        openFileOutput(getString(R.string.LOGIN_FILE)
                                , Context.MODE_PRIVATE));
                outputStreamWriter.write("email = " + userId + ";");
                outputStreamWriter.write("password = " + pwd);
                outputStreamWriter.close();
                Toast.makeText(this,"Stored in File Successfully!", Toast.LENGTH_LONG)
                        .show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "No network connection available. Cannot authenticate user",
                    Toast.LENGTH_SHORT) .show();
            return;
        }

//      When the user logs in (pretend that it is successful), we remember that they are logged
//      in and not show this screen again but instead take the user to another fragment. In the
//      login method of the SignInActivity, make it start the CourseActivity.
        mSharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), true).commit();

        Intent i = new Intent(this, CourseActivity.class);
        startActivity(i);
        finish();
    }
}
