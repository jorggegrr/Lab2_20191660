package com.example.Lab2_20191660;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Random;

public class registro extends AppCompatActivity {
    private EditText editTextNombre, editTextApellido, editTextCorreo, editTextContrasena;
    private CheckBox checkBoxAgree;
    private Button buttonCreateAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        editTextNombre = findViewById(R.id.editTextFirstName);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContrasena = findViewById(R.id.editTextContrasena);
        checkBoxAgree = findViewById(R.id.checkBoxTerms);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarEspacios()) {
                    Intent intent = new Intent(registro.this, MenuActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(registro.this, "complete todos los campos y marque la casilla.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        obtenerDatosDeURL();
    }

    private void obtenerDatosDeURL() {
        String url = "https://randomuser.me/api/?results=10"; // Obtener 10 resultados aleatorios

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mostrarDatosAleatorios(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(registro.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
                    }
                });

        // Agrega la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void mostrarDatosAleatorios(JSONArray jsonArray) {
        if (jsonArray != null && jsonArray.length() > 0) {
            try {
                // Genera un número aleatorio para seleccionar un usuario al azar
                Random random = new Random();
                int randomIndex = random.nextInt(jsonArray.length());

                JSONObject userData = jsonArray.getJSONObject(randomIndex);

                String nombre = userData.getJSONObject("name").getString("first");
                String apellido = userData.getJSONObject("name").getString("last");
                String correo = userData.getString("email");
                String contrasena = userData.getJSONObject("login").getString("password");

                editTextContrasena.setText(contrasena);
                editTextNombre.setText(nombre);
                editTextApellido.setText(apellido);
                editTextCorreo.setText(correo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(registro.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarEspacios() {
        String nombre = editTextNombre.getText().toString().trim();
        String apellido = editTextApellido.getText().toString().trim();
        String correo = editTextCorreo.getText().toString().trim();
        String contrasena = editTextContrasena.getText().toString().trim();
        boolean checkBoxChecked = checkBoxAgree.isChecked();

        return !nombre.isEmpty() && !apellido.isEmpty() && !correo.isEmpty() && !contrasena.isEmpty() && checkBoxChecked;
    }
}
