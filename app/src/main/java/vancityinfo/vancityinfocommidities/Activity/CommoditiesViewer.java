package vancityinfo.vancityinfocommidities.Activity;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import vancityinfo.vancityinfocommidities.Fragment.CommoditiesList;
import vancityinfo.vancityinfocommidities.R;

public class CommoditiesViewer extends ActionBarActivity implements
        CommoditiesList.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodities_viewer);

        /* Commodities List Fragment Commit */

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        CommoditiesList commoditiesList = CommoditiesList.Instance();

        fragmentTransaction.add(R.id.commodities_view_container_1, commoditiesList);
        fragmentTransaction.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_commodities_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
