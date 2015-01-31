package vancityinfo.vancityinfocommidities.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import vancityinfo.vancityinfocommidities.Adapter.CommoditiesListAdapter;
import vancityinfo.vancityinfocommidities.Model.Commodity;
import vancityinfo.vancityinfocommidities.Parsers.CommoditiesParser;
import vancityinfo.vancityinfocommidities.R;

/**
 * Implements Singleton Pattern
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CommoditiesList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CommoditiesList#Instance} factory method to
 * get an instance of this fragment.
 */
public class CommoditiesList extends Fragment {

    //Singleton Instance
    private static volatile CommoditiesList instance;

    //Commodities to be Viewed
    private ArrayList<Commodity> mCommodities;

    //ListView Selection Listener
    private OnFragmentInteractionListener mListener;

    public static CommoditiesList Instance() {
        if(instance == null)
         instance = new CommoditiesList();

        return instance;
    }

    public CommoditiesList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_commodities_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        InputStream ins = getResources().openRawResource(
                getResources().
                        getIdentifier(
                                "raw/commodities",
                                "raw",
                                getActivity().getPackageName()));

        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        String line;
        StringBuilder JSONString = new StringBuilder();

        try {
            while (( line = reader.readLine()) != null) {
                JSONString.append(line);
                JSONString.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //initialise new commodities parser
        CommoditiesParser parser = CommoditiesParser.Instance();

        try {
            mCommodities = parser.Parse(JSONString.toString());
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        ListView listView = (ListView) getActivity().
                findViewById(R.id.fragment_commodities_listView);

        CommoditiesListAdapter adapter = new CommoditiesListAdapter(getActivity(), mCommodities);
        listView.setAdapter(adapter);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
