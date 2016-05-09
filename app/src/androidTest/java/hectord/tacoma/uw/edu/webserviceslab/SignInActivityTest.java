package hectord.tacoma.uw.edu.webserviceslab;

import android.test.ActivityInstrumentationTestCase2;

import hectord.tacoma.uw.edu.webserviceslab.authenticate.SignInActivity;

import com.robotium.solo.Solo;

/**
 * Created by Hector on 5/8/2016.
 */
public class SignInActivityTest extends
        ActivityInstrumentationTestCase2<SignInActivity>{

    private Solo solo;

    public SignInActivityTest() {
        super(SignInActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();

    }

    public void testSignInFragmentLoads() {
        boolean fragmentLoaded = solo.searchText("Enter your userid");
        assertTrue("Login fragment loaded", fragmentLoaded);
    }

    public void testSignInWorks() {
        solo.enterText(0, "userid@");
        solo.enterText(1, "somepassword");
        solo.clickOnButton("Sign In");
        boolean worked = solo.searchText("Course List");
        assertTrue("Sign in worked!", worked);
    }


}
