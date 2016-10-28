package welcome_pager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project03.csc296.pollinateapp.R;

public class WelcomeHeaderFragment extends Fragment {

    public static final String jl = "JEFF_WELC_HEAD_FRAG";

    public WelcomeHeaderFragment() {
        // Required empty public constructor
        Log.d(jl, "WelcomeHeaderFragment created!");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_welcome_header, container, false);
        Log.d(jl, "WelcomeHeaderFragment onCreateView()");
        return v;
    }
}
