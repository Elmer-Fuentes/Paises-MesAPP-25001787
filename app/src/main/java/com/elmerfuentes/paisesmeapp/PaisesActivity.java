package com.elmerfuentes.paisesmeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Consulta todas las páginas del servicio REST Countries y muestra sus banderas. */
public class PaisesActivity extends AppCompatActivity {
    private static final String URL = "https://api.restcountries.com/countries/v5"
            + "?limit=100&response_fields=names.common,names.translations.spa.common,flag.url_png";
    private final List<Pais> listaPaises = new ArrayList<>();
    private PaisAdapter adaptador;
    private RequestQueue requestQueue;
    private ProgressBar progreso;
    private TextView estado;
    private Button reintentar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paises);
        WindowInsetsHelper.apply(this);
        RecyclerView listado = findViewById(R.id.rvPaises);
        listado.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new PaisAdapter(listaPaises);
        listado.setAdapter(adaptador);
        progreso = findViewById(R.id.progreso);
        estado = findViewById(R.id.estado);
        reintentar = findViewById(R.id.reintentar);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        reintentar.setOnClickListener(v -> cargarPaises());
        cargarPaises();
    }

    private void cargarPaises() {
        int anteriores = listaPaises.size();
        listaPaises.clear();
        adaptador.notifyItemRangeRemoved(0, anteriores);
        progreso.setVisibility(View.VISIBLE);
        reintentar.setVisibility(View.GONE);
        estado.setText(R.string.loading_countries);
        cargarPagina(0);
    }

    private void cargarPagina(int offset) {
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.GET,
                URL + "&offset=" + offset, null, respuesta -> {
            try {
                JSONObject data = respuesta.getJSONObject("data");
                JSONArray objetos = data.getJSONArray("objects");
                for (int i = 0; i < objetos.length(); i++) {
                    JSONObject objeto = objetos.getJSONObject(i);
                    JSONObject nombres = objeto.getJSONObject("names");
                    String nombre = nombres.getString("common");
                    JSONObject traducciones = nombres.optJSONObject("translations");
                    JSONObject espanol = traducciones == null ? null : traducciones.optJSONObject("spa");
                    if (espanol != null) nombre = espanol.optString("common", nombre);
                    JSONObject bandera = objeto.optJSONObject("flag");
                    listaPaises.add(new Pais(nombre, bandera == null ? "" : bandera.optString("url_png")));
                }
                JSONObject meta = data.optJSONObject("meta");
                boolean hayMas = meta == null ? objetos.length() == 100 : meta.optBoolean("more");
                if (hayMas && objetos.length() > 0) {
                    cargarPagina(offset + objetos.length());
                } else {
                    Collator orden = Collator.getInstance(new Locale("es"));
                    listaPaises.sort((a, b) -> orden.compare(a.getNombre(), b.getNombre()));
                    adaptador.notifyDataSetChanged();
                    progreso.setVisibility(View.GONE);
                    estado.setText(listaPaises.isEmpty() ? getString(R.string.empty_countries)
                            : getString(R.string.countries_loaded, listaPaises.size()));
                }
            } catch (JSONException e) {
                mostrarError(getString(R.string.invalid_response));
            }
        }, error -> {
            String mensaje = getString(R.string.network_error);
            if (error.networkResponse != null) {
                int codigo = error.networkResponse.statusCode;
                mensaje = codigo == 401 || codigo == 403 ? getString(R.string.auth_error)
                        : getString(R.string.http_error, codigo);
            }
            mostrarError(mensaje);
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + BuildConfig.RESTCOUNTRIES_API_KEY);
                headers.put("Accept", "application/json");
                headers.put("User-Agent", "PaisesMeApp/1.0 (Android)");
                return headers;
            }
        };
        solicitud.setTag(this);
        solicitud.setRetryPolicy(new DefaultRetryPolicy(20000, 1, 1f));
        requestQueue.add(solicitud);
    }

    private void mostrarError(String mensaje) {
        progreso.setVisibility(View.GONE);
        estado.setText(mensaje);
        reintentar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        if (requestQueue != null) {
            requestQueue.cancelAll(this);
            requestQueue.stop();
        }
        super.onDestroy();
    }
}
