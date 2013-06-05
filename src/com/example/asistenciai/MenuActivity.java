package com.example.asistenciai;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;





public class MenuActivity extends Activity {
	
	private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
 	private static final String url_registrar_asistencia = "http://eventosfia.p.ht/android/registrar_asistencia.php";
    private static final String TAG_SUCCESS = "success";
 	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        
        //1. LOCALIZAR CONTROLES
        final Button btnRegistrar = (Button)findViewById(R.id.BtnRegistrar);
        
        btnRegistrar.setOnClickListener(new OnClickListener(){
    		@Override
    		public void onClick(View v) {
    				// TODO Auto-generated method stub
    			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
    		        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
    		        startActivityForResult(intent, 0);
    			
    		}
     
            });
       
       }
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	 	   if (requestCode == 0) {
	 	      if (resultCode == RESULT_OK) {
	 	         String resultado = intent.getStringExtra("SCAN_RESULT");
	 	         
	 	         //TRABAJANDO CON EL TEXTO DEL CODIGO QR
	 	         int punto1 = resultado.indexOf(".");
	 	         
	 	         String dni = resultado.substring(0,punto1);
	 	         String idpon = resultado.substring(punto1+1); 
	 	         int num =1;
	 	         
	 	        String data[] = {dni,idpon};
	 	         
	 	         Log.d("DNI Y COD_PON",dni+" "+idpon);
	 	         /*Calendar calendario = Calendar.getInstance();
	 	         int hora;
	 	         int minutos;
	 	         int segundos;
	 	         hora =calendario.get(Calendar.HOUR_OF_DAY);
	 	         minutos = calendario.get(Calendar.MINUTE);
	 	         segundos = calendario.get(Calendar.SECOND);*/
	 	         
	 	        if(resultado.equals("46619058.201")){
	 	        	new ActualizarAsistencia().execute(data);
	 	        	
	 	        }else{ 
	 	        	Intent i = new Intent(MenuActivity.this, ResultadoFalloActivity.class);
	 	        	startActivity(i);
	 	         }
	 	      } else if (resultCode == RESULT_CANCELED) {
	 	         // Handle cancel
	 	      }
	 	   }
	 }
		
	class ActualizarAsistencia extends AsyncTask<String, String, String> {
		
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(MenuActivity.this);
				pDialog.setMessage("Registrando asistencia..");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}
	        
			protected String doInBackground(String... data) {
				String dni = data[0];
				String idpon = data[1];
				
				Log.d("DNI Y COD_PON",dni+" "+idpon);
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("dni", dni));
				params.add(new BasicNameValuePair("idpon", idpon));
				
				JSONObject json = jsonParser.makeHttpRequest(url_registrar_asistencia,
						"POST", params);
				
				Log.d("Create Response", json.toString());
							
				try {
	                int success = json.getInt(TAG_SUCCESS);
	 
	                if (success == 1) {
	                    // Se registro correctamente la asistencia
	                	Intent i = new Intent(MenuActivity.this, ResultadoOkActivity.class);
		 	        	startActivity(i);
	                    finish();
	                } else {
	                    // No se encontro el DNI ingresado
	                	Intent i = new Intent(MenuActivity.this, ResultadoFalloActivity.class);
		 	        	startActivity(i);
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }

				return null;
			}
			
			/**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once product uupdated
	            pDialog.dismiss();
	        }
			
			
		}
		
		
}
