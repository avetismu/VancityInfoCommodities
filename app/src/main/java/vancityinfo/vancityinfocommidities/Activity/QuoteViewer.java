package vancityinfo.vancityinfocommidities.Activity;

import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import vancityinfo.vancityinfocommidities.Fragment.QuoteList;
import vancityinfo.vancityinfocommidities.Fragment.QuoteDisplay;
import vancityinfo.vancityinfocommidities.Model.Quote;
import vancityinfo.vancityinfocommidities.R;

public class QuoteViewer extends ActionBarActivity implements
        QuoteList.ListViewSelectable {

    //instance of QuoteDisplay
    private QuoteDisplay Display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodities_viewer);

        /* Commodities List Fragment Commit */

        try {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            QuoteList quoteList = QuoteList.getInstance();

            fragmentTransaction.add(R.id.commodities_view_container_list, quoteList);
            fragmentTransaction.commit();
        }
        catch(Exception e){
            Log.e("Commodities List", e.getMessage());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_commodities_viewer, menu);
        return true;
    }

    /* Interface Selectable implementation */

    @Override
    public void onListViewItemSelected(Quote quote) {

        //instantiate if not instantiated
        if (Display == null)
            Display = QuoteDisplay.getInstance();

        /* Commodities Display Fragment Commit */
        try {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.commodities_view_container_display, Display);
            fragmentTransaction.commit();

            Display.setCommodity(quote);
        }
        catch(Exception e){
            Log.e("Commodities Display", e.getMessage());
        }

    }
}
