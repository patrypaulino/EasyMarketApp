package com.infodat.easymarket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.infodat.zbarscannerexample.R;


public class LoginActivity extends ActionBarActivity {

    TextView txtuser;
    TextView txtpass;
    Button btnlogin;
    static String usuario;
    static String contrasena;
    String returnString;
    boolean found;
    String getMessage;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // StrictMode is most commonly used
                        //to catch accidental disk or network access on the application's main thread
                .penaltyLog().build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtuser = (TextView) findViewById(R.id.txtusername);
        txtpass = (TextView) findViewById(R.id.txtpassword);
        btnlogin = (Button) findViewById(R.id.button);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = txtuser.getText().toString(); //Capturo el valor del texto digitado
                contrasena = txtpass.getText().toString();//capturar el valor del password
//                ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
//
//                postParameters.add(new BasicNameValuePair("user", usuario)); //Añadir parametro del usuario
//                postParameters.add(new BasicNameValuePair("pass", contrasena));//Añadir el parametro del passwoord
//                String response = null;
//                try{
//                    /*
//                    Ahora ejecutaremos el link donde verificaremos con el servicio web (RESTFul) los parametros, para saber
//                    si son correctos o no.
//                     */
//
//                      response = CustomHttpClient.executeHttpPost(
//                           "http://ddbbtre.16mb.com/Andoid_Rest/v1/login", //Url que nos llevara al servicio web y los parametros anteriormente añadidos
//                         postParameters);
//
//                    //Resultado de la peticion
//                     String result = response.toString();
//                     Log.i("Result", result);
//                    returnString = "";
//                    Log.i("RESULT", result);
//                    /*
//                    Nota: Los Datos vienen en formato JSON
//                     */
//                    //parse json data
//                    try {
//                        returnString = "";
//                        Log.i("RESULT", result);
//
//                        JSONObject json_data = new JSONObject(result); //Creo un objeto JSON
//                        getMessage = json_data.getString("Usuario"); //De JSON convertirlo a STRING, el dato del usuario
//                        if (!getMessage.equals("Null")){ //Verificar si el Resultado del Usuario no es Null
//                            Log.i("log_tag", getMessage); //Pruebas por consola
//                            found = true; //Encontro al usuario, osea es un usuario Valido.
//                           // Log.i("Username",getMessage);
//
//
//
//                        }
//                }catch (JSONException e) {
//                        Log.e("log_tag", "Error parsing data " + e.toString());
//                    }
//                } catch (SocketException soex) {
//                    Log.i("Socket Err", "Error in http connection!" + soex.toString());
//
//                } catch (Exception e) {
//                    Log.e("log_tag", "Error in http connection!!" + e.toString());
//                }
                found = true;
               if (found) {
                CharSequence text = "Bienvenido " + usuario;
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                    /*
                     Tarea Asincronica para presentar un dialogo de que se esta cargando.....
                                 */
                AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected void onPreExecute() {
                        pd = new ProgressDialog(LoginActivity.this);
                        pd.setTitle("Processing...");
                        pd.setMessage("Please wait.");
                        pd.setCancelable(false);
                        pd.setIndeterminate(true);

                        pd.show();
                    }
                    /*
                    Timer de Espera
                     */
                    @Override
                    protected Void doInBackground(Void... arg0) {
                        try {
                            //Do something...
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        if (pd!=null) {
                            pd.dismiss();
                            //b.setEnabled(true);
                        }
                    }

                };
                task.execute((Void[])null);
                    /*
                    Abriendo o pasando al otro activity
                     */
                    // Cuando se pasa el login, la aplicacion CodigosActivity es llamada.
                    //Esta contiene la lista de los productos temporalmente.
                Intent it = new Intent(LoginActivity.this,CodigosActivity.class);
                startActivity(it);




            } else {
                CharSequence text = "Error, Revise El username y/o el password";
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        }

    });


    }



}
