package vancityinfo.vancityinfocommidities.Activity;

import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import vancityinfo.vancityinfocommidities.Fragment.QuoteList;
import vancityinfo.vancityinfocommidities.Fragment.QuoteDisplay;
import vancityinfo.vancityinfocommidities.Model.Quote;
import vancityinfo.vancityinfocommidities.R;
import android.support.v4.widget.SlidingPaneLayout;

public class QuoteViewer extends ActionBarActivity implements
        QuoteList.ListViewSelected {

    //instance of QuoteDisplay
    private QuoteDisplay Display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodities_viewer);

        setTitle(R.string.app_name);

        /* Commodities List and Display Fragment Commit */

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            QuoteList quoteList = QuoteList.getInstance();
            Display = QuoteDisplay.getInstance();

            fragmentTransaction.add(R.id.commodities_view_container_list, quoteList);
            fragmentTransaction.add(R.id.commodities_view_container_display, Display, "display");

            fragmentTransaction.commit();




        //sets the SlidingPanelayout to open
        SlidingPaneLayout slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.com_view_sliding_pane);
        slidingPaneLayout.openPane();

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


        //set quote to be displayed
        try {
            Display.setQuote(quote);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
