package com.moppahtech.bankbierapp;

import android.content.Intent;
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

    private Button btnSacar, btnDepositar;
    private TextView txtSaldoBanco, txtSaldo, txtObservacao,txt300,txt600;
    private EditText editDepositante, editObservacao, editTipo2, editTipo1;
    public int T1, T2,Total,SD;
    public String log, tipo1, tipo2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);

        btnSacar = findViewById(R.id.btnSacar);
        btnDepositar = findViewById(R.id.btnDepositar);
        txtSaldo = findViewById(R.id.txtSaldo);
        txtSaldoBanco = findViewById(R.id.txtSaldoBanco);
        txtObservacao = findViewById(R.id.txtObservacao);
        txt300 = findViewById(R.id.txt300);
        txt600 = findViewById(R.id.txt600);
        editDepositante = findViewById(R.id.editDepositante);
        editObservacao = findViewById(R.id.editObservacao);
        editTipo2 = findViewById(R.id.editTipo2);
        editTipo1 = findViewById(R.id.editTipo1);


        Intent intent = getIntent();
        Bundle chave = intent.getExtras();
        if(chave != null){
            log = chave.getString("chave");


            if(log.toString().equals("123")){
                btnDepositar.setVisibility(View.VISIBLE);
                editDepositante.setVisibility(View.VISIBLE);
                editObservacao.setVisibility(View.VISIBLE);
                editTipo1.setVisibility(View.VISIBLE);
                editTipo2.setVisibility(View.VISIBLE);
                txtObservacao.setVisibility(View.VISIBLE);
                txt300.setVisibility(View.VISIBLE);
                txt600.setVisibility(View.VISIBLE);
                btnSacar.setVisibility(View.VISIBLE);


            }else {
                btnDepositar.setVisibility(View.INVISIBLE);
                editDepositante.setVisibility(View.INVISIBLE);
                editObservacao.setVisibility(View.INVISIBLE);
                editTipo1.setVisibility(View.INVISIBLE);
                editTipo2.setVisibility(View.INVISIBLE);
                txtObservacao.setVisibility(View.INVISIBLE);
                txt300.setVisibility(View.INVISIBLE);
                txt600.setVisibility(View.INVISIBLE);
                btnSacar.setVisibility(View.INVISIBLE);
            }



        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://moppahtech.co.nf/bb_select_quantidade.php").newBuilder();
            urlBuilder.addQueryParameter("bbCelular", log);


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

                                    tipo1 = (jsonObject.getString("bb_Tipo_1"));
                                    tipo2 = (jsonObject.getString("bb_Tipo_2"));

                                    T1 = Integer.parseInt(tipo1) * 300;
                                    T2 = Integer.parseInt(tipo2) * 600;
                                    Total = T1 + T2;
                                    String total = String.valueOf(Total);

                                    txtSaldo.setText(total);

                                } catch (JSONException e) {
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



            try {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                OkHttpClient client = new OkHttpClient();
                HttpUrl.Builder urlBuilder = HttpUrl.parse("http://moppahtech.co.nf/bb_select_saldobanco.php").newBuilder();
                urlBuilder.addQueryParameter("bbCelular", "0");

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

                                        String saldo = (jsonObject.getString("sum((bb_Tipo_1*300)+(bb_Tipo_2*600))"));


                                        SD = Integer.parseInt(saldo);

                                        String saldobanco = String.valueOf(SD);

                                        txtSaldoBanco.setText(saldobanco);

                                    } catch (JSONException e) {

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

    public void depositar (View view){

        String dp1 = editTipo1.getText().toString();
        String dp2 = editTipo2.getText().toString();

        int DP1 = Integer.parseInt(dp1);
        int DP2 = Integer.parseInt(dp2);
        int A = Integer.parseInt(tipo1);
        int B = Integer.parseInt(tipo2);

        A = A + DP1;
        B = B + DP2;

        String depo1 = String.valueOf(A);
        String depo2 = String.valueOf(B);

        try {


            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://moppahtech.co.nf/bb_update_cerveja.php").newBuilder();

            urlBuilder.addQueryParameter("bbCelular",editDepositante.getText().toString());
            urlBuilder.addQueryParameter("bb_Tipo_1", depo1);
            urlBuilder.addQueryParameter("bb_Tipo_2", depo2);
            urlBuilder.addQueryParameter("bb_Observacao", editObservacao.getText().toString());

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
            Toast toast = Toast.makeText(SaldoActivity.this,"Cerveja depositada !",Toast.LENGTH_LONG);
            toast.show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(SaldoActivity.this,"Deposito não realizado !",Toast.LENGTH_LONG);
            toast.show();

        }

        limpar();
        Intent intent = new Intent(SaldoActivity.this,LoginActivity.class);
        startActivity(intent);

    }

    public void sacar (View view){

        String dp1 = editTipo1.getText().toString();
        String dp2 = editTipo2.getText().toString();

        int DP1 = Integer.parseInt(dp1);
        int DP2 = Integer.parseInt(dp2);
        int A = Integer.parseInt(tipo1);
        int B = Integer.parseInt(tipo2);

        A = A - DP1;
        B = B - DP2;

        String depo1 = String.valueOf(A);
        String depo2 = String.valueOf(B);

        try {


            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://moppahtech.co.nf/bb_update_sacarcerveja.php").newBuilder();

            urlBuilder.addQueryParameter("bbCelular",editDepositante.getText().toString());
            urlBuilder.addQueryParameter("bb_Tipo_1", depo1);
            urlBuilder.addQueryParameter("bb_Tipo_2", depo2);
            //urlBuilder.addQueryParameter("bb_Observacao", editObservacao.getText().toString());

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
            Toast toast = Toast.makeText(SaldoActivity.this,"Saque realizado !",Toast.LENGTH_LONG);
            toast.show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(SaldoActivity.this,"Não foi possivel sacar !",Toast.LENGTH_LONG);
            toast.show();
        }

        limpar();
        Intent intent = new Intent(SaldoActivity.this,LoginActivity.class);
        startActivity(intent);

    }
    public void limpar() {
        editTipo1.clearFocus();
        editTipo1.setText("");
        editTipo2.setText("");

    }
}
