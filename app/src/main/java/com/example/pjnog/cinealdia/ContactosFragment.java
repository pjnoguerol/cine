package com.example.pjnog.cinealdia;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class ContactosFragment extends Fragment {
    private List<String> lista;
    private RecyclerView rv;
    HttpURLConnection con;
    private List<Usuarios> usuarios;
    public ContactosFragment() {
        // Required empty public constructor
    }


    private List<String> telList (Context context)
    {
        lista = new ArrayList<String>() ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context.checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1540);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            ContentResolver cr = context.getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);

            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));

                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phoneNo = phoneNo.replace("+34","");
                            phoneNo = phoneNo.replace(" ", "");
                            phoneNo = phoneNo.replace("(", "");
                            phoneNo = phoneNo.replace(")", "");
                            phoneNo = phoneNo.replace("-", "");
                            lista.add("'"+phoneNo+"'");
                            //Toast.makeText(context, "Name: " + name
                                    //+ ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                        }
                        pCur.close();
                    }
                }
            }
        }
        return lista;


    }

    private void enviarWebservice(Context context)
    {
        //Toast.makeText(context, lista.size(), Toast.LENGTH_LONG).show();
        if (lista!=null && !lista.isEmpty())
        {
            try {
                String listaCadena=android.text.TextUtils.join(",", lista);
                String url = "http://www.intraco.es/cinealdia/cinealdia_clase.php?contactos="+listaCadena;
                //Toast.makeText(context, url, Toast.LENGTH_LONG).show();
                System.out.println(url);

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
        }
        else
        {
            Toast.makeText(context, "lista vacia o nula", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context context = inflater.getContext();

        View rootView = inflater.inflate(R.layout.recyclerview_activity, container, false);
        rv=(RecyclerView)rootView.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        telList(context);
        enviarWebservice(context);

        return rootView;

    }
    public class JsonTask extends AsyncTask<URL, Void, List<Usuarios>> {

        @Override
        protected List<Usuarios> doInBackground(URL... urls) {


            try {

                // Establecer la conexión
                con = (HttpURLConnection)urls[0].openConnection();
                con.setConnectTimeout(15000);
                con.setReadTimeout(10000);

                // Obtener el estado del recurso
                int statusCode = con.getResponseCode();

                if(statusCode!=200) {
                    usuarios = new ArrayList<>();
                    //animales.add(new Animal("Error",null,null));

                } else {

                    // Parsear el flujo con formato JSON
                    InputStream in = new BufferedInputStream(con.getInputStream());

                    // JsonAnimalParser parser = new JsonAnimalParser();
                    GsonUsuarioParser parser = new GsonUsuarioParser();

                    usuarios = parser.leerFlujoJson(in);
                    System.out.println(usuarios.size());


                }

            } catch (Exception e) {
                e.printStackTrace();

            }finally {
                con.disconnect();
            }
            return usuarios;
        }


        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        protected void onPostExecute(List<Usuarios> usuarios) {
            /*
            Asignar los objetos de Json parseados al adaptador
             */
            if(usuarios!=null) {
                //  adaptador = new AdaptadorDeAnimales(getBaseContext(), animales);
                // lista.setAdapter(adaptador);
                RVAdapter3 adapter = new RVAdapter3(usuarios);
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
}
