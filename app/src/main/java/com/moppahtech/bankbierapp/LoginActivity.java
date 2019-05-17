package com.moppahtech.bankbierapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    EditText editLoginCelular, editSenha, editPesquisa;
    Button btnLogar, btnSemCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editLoginCelular = findViewById(R.id.editLoginCelular);
        editSenha = findViewById(R.id.editSenha);
        //editPesquisa = findViewById(R.id.editPesquisa);
        btnLogar = findViewById(R.id.btnLogar);
        btnSemCadastro = findViewById(R.id.btnSemCadastro);

    }

    public void pesquisar(View view){

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://moppahtech.co.nf/bb_select_login.php").newBuilder();
            urlBuilder.addQueryParameter("bbCelular", editLoginCelular.getText().toString());
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

                                   String login = (jsonObject.getString("bbCelular"));
                                   String senha = (jsonObject.getString("bb_Senha"));

                                    //editLoginCelular.setText(jsonObject.getString("bbCelular"));
                                    //editSenha.setText(jsonObject.getString("bb_Senha"));
                                    // txtlogin.setText("Logado!!!");
                                    if((editLoginCelular.getText().toString().equals(login)&&(editSenha.getText().toString().equals(senha)))){
                                        Intent intent = new Intent(LoginActivity.this, SaldoActivity.class);




                                        Bundle chave = new Bundle();
                                        chave.putString("chave",login);
                                        intent.putExtras(chave);
                                        startActivity(intent);





                                    }else {
                                        Toast toast = Toast.makeText(LoginActivity.this,"Informe a senha correta!",Toast.LENGTH_LONG);
                                        toast.show();
                                    }


                                } catch (JSONException e) {
                                   //txtlogin.setText("Não Logado!!!");
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
    public void semcadastro(View view){
        Intent intent = new Intent(LoginActivity.this, ClienteActivity.class);
        startActivity(intent);
    }

}
