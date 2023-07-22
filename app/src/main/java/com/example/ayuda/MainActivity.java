package com.example.ayuda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayuda.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText txtPedido;
    Button btnConsul;
    TextView txtEstado;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue= Volley.newRequestQueue(getBaseContext());
        btnConsul=(Button) findViewById(R.id.btnConsul);
        txtPedido=(EditText) findViewById(R.id.txtPedido);
        txtEstado=(TextView) findViewById(R.id.txtEstado);

        btnConsul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });

    }


    private void cargarWebService() {

        String url = "https://comprasegura.des-cali.com/webServices/webService.php?case=1&id="+txtPedido.getText();



        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                JSONArray json=response.optJSONArray("rpta");
                JSONObject jsonObject=null;

                try {
                    jsonObject=json.getJSONObject(0);
                    String rp=jsonObject.optString("estado");
                    if (rp.equalsIgnoreCase("1")){
                        Toast.makeText(getBaseContext(), "Pedido Activo", Toast.LENGTH_SHORT).show();
                        txtEstado.setText("Pedido Activo");
                        txtEstado.setTextColor(Color.GREEN);
                    }else{
                        Toast.makeText(getBaseContext(), "Pedido Inactivo", Toast.LENGTH_SHORT).show();
                        txtEstado.setText("Pedido Inactivo");
                        txtEstado.setTextColor(Color.RED);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}



