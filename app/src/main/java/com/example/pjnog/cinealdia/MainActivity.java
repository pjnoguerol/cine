package com.example.pjnog.cinealdia;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView user;
    TextView email;
    Fragment fragment;
    Usuarios usuario;
    private Busqueda bus;

    private void generarFragment()
    {
        boolean fragmentTransaction = true;
        fragment = new PeliculasFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("busqueda", bus);
        fragment.setArguments(bundle);
        //fragment.setArguments(args);
        if(fragmentTransaction) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent i = getIntent();
        usuario = (Usuarios) i.getSerializableExtra("usuario");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        user = (TextView)header.findViewById(R.id.userName);
        email = (TextView)header.findViewById(R.id.userEmail);
        user.setText(usuario.getUsuario());
        email.setText(usuario.getEmail());
        bus = new Busqueda();
        bus.setModo(1);

        generarFragment();
        //boolean fragmentTransaction = true;
        //fragment = new PeliculasFragment();
        //fragment.setArguments(args);
       // if(fragmentTransaction) {
            //getSupportFragmentManager().beginTransaction()
                    //.replace(R.id.content_frame, fragment)
                    //.commit();



        //}



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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                // UserFeedback.show( "SearchOnQueryTextSubmit: " + query);
                //Toast.makeText(MainActivity.this, query, Toast.LENGTH_LONG).show();
                bus = new Busqueda();
                bus.setModo(3);
                bus.setPelicula(query);
                generarFragment();

                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                //Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                return false;
            }
        });
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        boolean fragmentTransaction = false;
        fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_peliculas) {
            fragment = new PeliculasFragment();
            Bundle bundle=new Bundle();
            bus = new Busqueda();
            bus.setModo(1);

            bundle.putSerializable("busqueda", bus);
            fragment.setArguments(bundle);
            fragmentTransaction = true;

        } else if (id == R.id.nav_actores) {
            fragment = new ActoresFragment();
            fragmentTransaction = true;

        } else if (id == R.id.nav_generos) {
            fragment = new PeliculasFragment();
            Bundle bundle=new Bundle();
            bus = new Busqueda();
            bus.setModo(2);
            bus.setGenero(usuario.getId_gen());
            bundle.putSerializable("busqueda", bus);
            fragment.setArguments(bundle);
            fragmentTransaction = true;

        } else if (id == R.id.nav_manage) {

        }else if (id == R.id.posicion) {
            fragment = new PosicionFragment();
            fragmentTransaction = true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(fragmentTransaction) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }

        drawer.closeDrawers();
        return true;
    }
}
