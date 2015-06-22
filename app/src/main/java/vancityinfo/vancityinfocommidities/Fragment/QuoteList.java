package vancityinfo.vancityinfocommidities.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import vancityinfo.vancityinfocommidities.Adapter.QuoteAdapter;
import vancityinfo.vancityinfocommidities.Model.Quote;
import vancityinfo.vancityinfocommidities.Parsers.QuoteParser;
import vancityinfo.vancityinfocommidities.R;

/**
 * Implements Singleton Pattern
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link vancityinfo.vancityinfocommidities.Fragment.QuoteList.ListViewSelected} interface
 * to handle interaction events.
 * Use the {@link QuoteList#getInstance} factory method to
 * get an instance of this fragment.
 */
public class QuoteList extends Fragment{

    //Singleton Instance
    private static volatile QuoteList instance;

    //Commodities to be Viewed
    private ArrayList<Quote> mQuotes;

    //ListView Selection Listener
    private ListViewSelected mListener;

    public static QuoteList getInstance() {
        if(instance == null)
         instance = new QuoteList();

        return instance;
    }

    public QuoteList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quote_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        InputStream ins = getResources().openRawResource(
                getResources().
                        getIdentifier(
                                getActivity().getString(R.string.commodities_path),
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

        //initialise new commodities_en parser
        QuoteParser parser = QuoteParser.Instance();

        try {
            mQuotes = parser.Parse(JSONString.toString());
        }
        catch(JSONException e){
            e.printStackTrace();
            mQuotes = new ArrayList<Quote>();
        }

        ListView listView = (ListView) getActivity().
                findViewById(R.id.fragment_quote_list_listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onListViewItemSelected(mQuotes.get(position));
            }
        });

                QuoteAdapter adapter = new QuoteAdapter(getActivity(), mQuotes);
        listView.setAdapter(adapter);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ListViewSelected) activity;
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
    public interface ListViewSelected {

        public void onListViewItemSelected(Quote quote);

    }



}
