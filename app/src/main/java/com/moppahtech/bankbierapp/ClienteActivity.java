package com.moppahtech.bankbierapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ClienteActivity extends AppCompatActivity {

    EditText editNome, editCelular, editLogin, editSenha, editConfirmaSenha;
    Button btnEnviar;
    TextView txtConfirma;

    private void limpar() {
        editNome.clearFocus();
        editNome.setText("");
        editCelular.setText("");
        editLogin.setText("");
        editSenha.setText("");
        editConfirmaSenha.setText("");
        txtConfirma.setText("Carregando...!");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        txtConfirma = findViewById(R.id.txtConfirma);
        editNome = findViewById(R.id.editNome);
        editCelular = findViewById(R.id.editCelular);
        editLogin = findViewById(R.id.editLoginCelular);
        editSenha = findViewById(R.id.editSenha);
        editConfirmaSenha = findViewById(R.id.editConfirmaSenha);
        btnEnviar = findViewById(R.id.btnEnviar);

}



    public void salvar(View view){

        String snh, confsnh;
        snh = editSenha.getText().toString();
        confsnh = editConfirmaSenha.getText().toString();

        if (editSenha.getText().toString().equals(editConfirmaSenha.getText().toString())){

            try{
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                OkHttpClient client = new OkHttpClient();

                HttpUrl.Builder urlBuilder = HttpUrl.parse("http://moppahtech.co.nf/bb_insert_cadastro.php").newBuilder();
                urlBuilder.addQueryParameter("bbCelular", editCelular.getText().toString());
                urlBuilder.addQueryParameter("bb_Nome", editNome.getText().toString());
                urlBuilder.addQueryParameter("bb_Login", editLogin.getText().toString());
                urlBuilder.addQueryParameter("bb_Senha", editSenha.getText().toString());
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
                                    txtConfirma.setText(response.body().string());
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

            limpar();

        }else {
            txtConfirma.setText("Senha não confirmada...!");
        }

        }




}
