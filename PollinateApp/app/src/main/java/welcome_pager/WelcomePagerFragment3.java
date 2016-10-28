package welcome_pager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import project03.csc296.pollinateapp.EventPreferenceActivity;
import project03.csc296.pollinateapp.R;
import project03.csc296.pollinateapp.WelcomeActivity;


public class WelcomePagerFragment3 extends Fragment {

    Button mEnterButton;
    String mCurrUser;


    public WelcomePagerFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_welcome_pager_fragment3, container, false);

        mEnterButton = (Button) v.findViewById(R.id.button_enter_site);

        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterSite();
            }
        });

        return v;
    }

    public void enterSite() {
        Intent i = new Intent(getActivity(), EventPreferenceActivity.class);
        mCurrUser = getArguments().getString("KEY_EMAIL");
        i.putExtra("KEY_EMAIL", mCurrUser);
        startActivity(i);

    }

}
