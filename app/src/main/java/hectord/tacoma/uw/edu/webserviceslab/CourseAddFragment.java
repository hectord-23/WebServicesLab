package hectord.tacoma.uw.edu.webserviceslab;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseAddFragment extends Fragment {

    private final static String COURSE_ADD_URL
            = "http://cssgate.insttech.washington.edu/~hectord/Android/addCourse.php?";

    // member variables to access the EditText fields in onCreateView
    private EditText mCourseIdEditText;
    private EditText mCourseShortDescEditText;
    private EditText mCourseLongDescEditText;
    private EditText mCoursePrereqsEditText;


    /** Listener interface and create a member variable to access it. */
    private CourseAddListener mListener;

    public interface CourseAddListener {
        public void addCourse(String url);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CourseAddListener) {
            mListener = (CourseAddListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CourseAddListener");
        }
    }



    public CourseAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_course, container, false);

        mCourseIdEditText = (EditText) v.findViewById(R.id.add_course_id);
        mCourseShortDescEditText = (EditText) v.findViewById(R.id.add_course_short_desc);
        mCourseLongDescEditText = (EditText) v.findViewById(R.id.add_course_long_desc);
        mCoursePrereqsEditText = (EditText) v.findViewById(R.id.add_course_prereqs);


        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();

        // Call the buildURL method in the onClickListener in onCreateView
        Button addCourseButton = (Button) v.findViewById(R.id.add_course_button);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildCourseURL(v);
                mListener.addCourse(url);
            }
        });
        return v;
    }

    /** method that will build the url for calling the AsyncTask.  */
    private String buildCourseURL(View v) {

        StringBuilder sb = new StringBuilder(COURSE_ADD_URL);

        try {

            String courseId = mCourseIdEditText.getText().toString();
            sb.append("id=");
            sb.append(courseId);


            String courseShortDesc = mCourseShortDescEditText.getText().toString();
            sb.append("&shortDesc=");
            sb.append(URLEncoder.encode(courseShortDesc, "UTF-8"));


            String courseLongDesc = mCourseLongDescEditText.getText().toString();
            sb.append("&longDesc=");
            sb.append(URLEncoder.encode(courseLongDesc, "UTF-8"));

            String coursePrereqs = mCoursePrereqsEditText.getText().toString();
            sb.append("&prereqs=");
            sb.append(URLEncoder.encode(coursePrereqs, "UTF-8"));

            Log.i("CourseAddFragment", sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

}
