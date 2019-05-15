package com.moppahtech.bankbierapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SaldoActivity extends AppCompatActivity {

    Button btnSacar, btnDepositar, btnConsulta;
    TextView txtSaldoBanco, txtSaldo;
    EditText editUsuario;

    public int T1, T2,Total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);

        btnConsulta =findViewById(R.id.btnConsulta);
        btnSacar = findViewById(R.id.btnSacar);
        btnDepositar = findViewById(R.id.btnDepositar);
        txtSaldo = findViewById(R.id.txtSaldo);
        txtSaldoBanco = findViewById(R.id.txtSaldoBanco);
        editUsuario = findViewById(R.id.editUsuario);
    }

    public void consultaSaldo (View view){

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://moppahtech.co.nf/bb_select_quantidade.php").newBuilder();
            urlBuilder.addQueryParameter("bbCelular", editUsuario.getText().toString());

            // urlBuilder.addQueryParameter("bb_Senha", editSenha.getText().toString());

            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                //txtAviso.setText(response.body().string());

                                try {
                                    String data = response.body().string();

                                    JSONArray jsonArray = new JSONArray(data);
                                    JSONObject jsonObject;

                                    jsonObject = jsonArray.getJSONObject(0);

                                    String tipo1 = (jsonObject.getString("bb_Tipo_1"));
                                    String tipo2 = (jsonObject.getString("bb_Tipo_2"));

                                    T1 = Integer.parseInt(tipo1) * 300;
                                    T2 = Integer.parseInt(tipo2) * 600;
                                    Total = T1 + T2;
                                    String total = String.valueOf(Total);


                                    //atualizarSaldo();

                                    //HttpUrl.Builder urlBuilder = HttpUrl.parse("http://moppahtech.co.nf/bb_update_saldo.php").newBuilder();

                                    //urlBuilder.addQueryParameter("bb_saldo",txtSaldoBanco.getText().toString());

                                    txtSaldo.setText(total);
                                    //txtSaldoBanco.setText(tipo2);


                                } catch (JSONException e) {
                                    // txtlogin.setText("Não Logado!!!");
                                    Toast toast  = Toast.makeText(SaldoActivity.this,"Cervejeiro não cadastrado!",Toast.LENGTH_LONG);
                                    toast.show();

                                }


                            } catch (IOException e) {
                                e.printStackTrace();


                            }

                        }
                    });
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void atualizarSaldo (){

        int saldo = T1 + T2;
        String saldoBanco = String.valueOf(saldo);

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://moppahtech.co.nf/bb_update_saldo.php").newBuilder();

            urlBuilder.addQueryParameter("bb_saldo", saldoBanco);

            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                        }
                    });
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
