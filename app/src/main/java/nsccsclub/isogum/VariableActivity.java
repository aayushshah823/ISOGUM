package nsccsclub.isogum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

public class VariableActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,
        PopupMenu.OnMenuItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(findViewById(R.id.fab));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void showPopup(View v) {
        //creates the popup object
        PopupMenu popup = new PopupMenu(this,v);
        //create an inflater to reference defined xml menu resource
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_create_popup, popup.getMenu());

        //registers an action listener
        popup.setOnMenuItemClickListener(this);



        //makes visible in activity
        popup.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        /*
        gets the menu option selected and passes in to the create activity
         */

        //creates an intent to start create activity
        Intent intent;

        //starts create activity with proper message
        switch(item.getItemId()) {
            case R.id.new_function:
                intent = new Intent(this,CreateFunctionActivity.class);
                intent.putExtra(FunctionActivity.EXTRA_NAME, "INSERT_NAME_HERE");
                startActivity(intent);
                return true;
            case R.id.new_variable:
                intent = new Intent(this,CreateVariableActivity.class);
                intent.putExtra(FunctionActivity.EXTRA_NAME, "INSERT_NAME_HERE");
                startActivity(intent);
                return true;
            default:
                //unrecognizable input try again
                return false;
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_function) {
            Intent intent = new Intent(this, FunctionActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_variable) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_help){
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
