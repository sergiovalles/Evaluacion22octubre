package com.example.practica22octubre2021.fragments;

import static com.example.practica22octubre2021.Library.Utils.Variables.receivedMovieOffline;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.practica22octubre2021.Library.Sqlite.MovieHelper;
import com.example.practica22octubre2021.Library.Utils.Utilerias;
import com.example.practica22octubre2021.Models.Movie;
import com.example.practica22octubre2021.R;
import com.example.practica22octubre2021.adapters.MoviesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerMovies;
    ArrayList<Movie> listamovies;
    String url = "";
    ProgressDialog loading;
    private List<String> UserData;
    String parametro ="";
    int tipo =0;
    private int  MY_TIMEOUT = 20000;
    String apiKey = "5e22301d806740ab05f6457124e07ec5";
    JsonObjectRequest jsonObjectRequest;
    String espacio = "101-2809";
    String numero = "8446";
    JSONArray json = null;
    String URLMOVIE = "https://api.themoviedb.org/3/movie/popular?api_key=";
    String URLPICTURE = "https://image.tmdb.org/t/p/w500";
    MovieHelper movieHelper;
    boolean serviciooffline = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        movieHelper = new MovieHelper(getActivity());
        listamovies =new ArrayList<>();
        if(Utilerias.checkInternetConnection(getActivity().getBaseContext())) {
            serviciooffline = false;
            WebServiceMovie();
        }
        else
        {
          serviciooffline = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerMovies  = (RecyclerView) getView().findViewById(R.id.idRecyclerImagen);
        recyclerMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerMovies.setHasFixedSize(true);

        if(serviciooffline)
        {
            ServicioOffline();
        }
    }

    private void WebServiceMovie() {
        loading = ProgressDialog.show(getActivity(), "Consultando Peliculas", "Espere por favor...", false, false);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getActivity());
        String url = URLMOVIE+apiKey; //
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (!(response.contains("vacio"))) {
                    try {
                        loading.dismiss();
                        Movie movie = null;
                        JSONObject jsonObj = new JSONObject(response);
                        //Toast.makeText(WorkOrders1.this,response,Toast.LENGTH_SHORT).show();
                        JSONArray json = jsonObj.optJSONArray("results");
                        for (int i = 0; i < json.length(); i++) {
                            movie = new Movie();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            movie.setTitle_movie(jsonObject.optString("original_title"));
                            movie.setDescription_moview(jsonObject.optString("overview"));
                            movie.setUrl_picture_movie(URLPICTURE+jsonObject.optString("poster_path"));
                            movie.setAutor_movie(jsonObject.optString("title"));
                            listamovies.add(movie);
                            movieHelper.saveNewMovie(movie);
                        }
                        MoviesAdapter adapter = new MoviesAdapter(listamovies);
                        recyclerMovies.setAdapter(adapter);

                    } catch (JSONException e) {
                        //e.printStackTrace();
                        Toast.makeText(getActivity(), "Error en " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }
                } else {
                    Toast.makeText(getActivity(), "Listado de Peliculas Vacía", Toast.LENGTH_SHORT).show();
                    loading.dismiss();


                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                loading.dismiss();
                Toast.makeText(getActivity(),"error "+error.getMessage(),Toast.LENGTH_SHORT).show();
                loading.dismiss();

            }
        });

        MyRequestQueue.add(MyStringRequest);
        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


    private void ServicioOffline() {
        Toast.makeText(getActivity(),"Debido a que no cuenta con una conexión a internet cargaremos las ultimas peliculas almacenadas de manera local",Toast.LENGTH_SHORT).show();
        movieHelper.getIdMovie();
        Movie movie = null;
        for(int i = 0; i < receivedMovieOffline.size(); i++)
        {
            movie = new Movie();
            movie.setTitle_movie(receivedMovieOffline.get(i).getTitle_movie());
            movie.setDescription_moview(receivedMovieOffline.get(i).getDescription_moview());
            movie.setUrl_picture_movie(receivedMovieOffline.get(i).getUrl_picture_movie());
            movie.setAutor_movie(receivedMovieOffline.get(i).getAutor_movie());
            listamovies.add(movie);
        }
        MoviesAdapter adapter2 = new MoviesAdapter(listamovies);
        recyclerMovies.setAdapter(adapter2);

    }
}