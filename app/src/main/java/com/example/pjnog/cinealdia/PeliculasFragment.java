package com.example.pjnog.cinealdia;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PeliculasFragment extends Fragment {
    private RecyclerView rv;
    HttpURLConnection con;
    List<Peliculas> peliculas;
    Busqueda usu;

    public PeliculasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context context = inflater.getContext();
        //int gen=getArguments().getInt("generos");
        usu = (Busqueda)getArguments().getSerializable("busqueda");
        Toast.makeText(context, usu.getModo()+"", Toast.LENGTH_LONG).show();
        View rootView =  inflater.inflate(R.layout.recyclerview_activity, container, false);
        rv=(RecyclerView)rootView.findViewById(R.id.rv);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rv.setItemAnimator(new DefaultItemAnimator());
        String url="";

        if (usu.getModo()==1)
        {
           url = Constantes.SERVIDOR+"/cinealdia/cinealdia_clase.php?peliculas";
        }
        else if (usu.getModo()==2){

            url = Constantes.SERVIDOR+"/cinealdia/cinealdia_clase.php?genero="+usu.getGenero();
        }
        else if (usu.getModo()==3)
        {
            url = Constantes.SERVIDOR+"/cinealdia/cinealdia_clase.php?busqueda="+usu.getPelicula();
        }
        try {
            ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                new JsonTask().
                        execute(
                                new URL(url));
            } else {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_LONG).show();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return rootView;
    }

    public class JsonTask extends AsyncTask<URL, Void, List<Peliculas>> {

        @Override
        protected List<Peliculas> doInBackground(URL... urls) {


            try {

                // Establecer la conexión
                con = (HttpURLConnection)urls[0].openConnection();
                con.setConnectTimeout(15000);
                con.setReadTimeout(10000);

                // Obtener el estado del recurso
                int statusCode = con.getResponseCode();

                if(statusCode!=200) {
                    peliculas = new ArrayList<>();
                    //animales.add(new Animal("Error",null,null));

                } else {

                    // Parsear el flujo con formato JSON
                    InputStream in = new BufferedInputStream(con.getInputStream());

                    // JsonAnimalParser parser = new JsonAnimalParser();
                    GsonPeliculaParser parser = new GsonPeliculaParser();

                    peliculas = parser.leerFlujoJson(in);
                    System.out.println(peliculas.size());


                }

            } catch (Exception e) {
                e.printStackTrace();

            }finally {
                con.disconnect();
            }
            return peliculas;
        }


        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        protected void onPostExecute(List<Peliculas> peliculas) {
            /*
            Asignar los objetos de Json parseados al adaptador
             */
            if(peliculas!=null) {
                //  adaptador = new AdaptadorDeAnimales(getBaseContext(), animales);
                // lista.setAdapter(adaptador);
                RVAdapter2 adapter = new RVAdapter2(peliculas);
                rv.setAdapter(adapter);

            }else{
                Toast.makeText(
                        getActivity(),
                        "Ocurrió un error de Parsing Json",
                        Toast.LENGTH_SHORT)
                        .show();
            }

        }
    }

    private void initializeData(){
        // persons = new ArrayList<>();
        // persons.add(new Person("Tom Hanks", "61 years old", R.drawable.tomhanks));
        // persons.add(new Person("Mat Damon", "49 years old", R.drawable.matdamin));
        // persons.add(new Person("Julia Roberts", "45 years old", R.drawable.juliaroberts));
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void initializeAdapter(){
        //RVAdapter adapter = new RVAdapter(persons);
        //rv.setAdapter(adapter);
    }
    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
