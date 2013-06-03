package com.example.asistenciai;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
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
	
	// Progress Dialog
    private ProgressDialog pDialog;
 
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
 
    // single product url
	private static final String url_registrar_asistencia = "http://10.0.2.2/android_connect/registrar_asistencia.php";
 
    // JSON Node names
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
	 	         String contents = intent.getStringExtra("SCAN_RESULT");
	 	        if(contents.equals("64572215")){
	 	        	
	 	        	new ActualizarAsistencia().execute();
	 	        	
	 	        	
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
		
			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(MenuActivity.this);
				pDialog.setMessage("Registrando asistencia..");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}
	        
			/**
			 * Creating product
			 * */
			protected String doInBackground(String... args) {
				String cod = "645722150";
				String asistio ="1";
				
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("cod", cod));
				params.add(new BasicNameValuePair("asistio", asistio));
							
				
				// getting JSON Object
				// Note that create product url accepts POST method
				JSONObject json = jsonParser.makeHttpRequest(url_registrar_asistencia,
						"POST", params);
				
				
				
				// check log cat for response
				Log.d("Create Response", json.toString());

				// check for success tag
				/*try {
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						// successfully created product
						Intent i = new Intent(MenuActivity.this, ResultadoOkActivity.class);
		 	        	startActivity(i);
						// closing this screen
						finish();
					} else {
						Intent i = new Intent(MenuActivity.this, ResultadoFalloActivity.class);
		 	        	startActivity(i);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}*/

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
