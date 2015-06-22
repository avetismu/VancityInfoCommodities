package vancityinfo.vancityinfocommidities.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import vancityinfo.vancityinfocommidities.Model.Quote;
import vancityinfo.vancityinfocommidities.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends Fragment {

    public Quote mQuote;

    public DataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //retain this fragment
        setRetainInstance(true);
    }


}
