package com.moppahtech.bankbierapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

                                try {
                                    String data = response.body().string();

                                    JSONArray jsonArray = new JSONArray(data);
                                    JSONObject jsonObject;

                                    jsonObject = jsonArray.getJSONObject(0);

                                    int trezentos = (jsonObject.getInt("bb_Tipo_1"));
                                    int seiszentos = (jsonObject.getInt("bb_Tipo_2"));

                                    //String login = (jsonObject.getString("bbCelular"));
                                    //String senha = (jsonObject.getString("bb_Senha"));

                                    txtSaldo.setText(jsonObject.getInt(String.valueOf(trezentos)));
                                    txtSaldoBanco.setText(jsonObject.getInt(String.valueOf(seiszentos)));
                                    /* txtlogin.setText("Logado!!!");
                                    if((editLoginCelular.getText().toString().equals(login)&&(editSenha.getText().toString().equals(senha)))){
                                        Intent intent = new Intent(LoginActivity.this, SaldoActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast toast = Toast.makeText(LoginActivity.this,"Informe a senha correta!",Toast.LENGTH_LONG);
                                        toast.show();
                                    }*/


                                } catch (JSONException e) {
                                    // txtlogin.setText("Não Logado!!!");
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

}
