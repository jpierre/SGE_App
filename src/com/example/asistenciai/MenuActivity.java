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
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;



public class MenuActivity extends Activity {
	
	JSONParser jsonParser = new JSONParser();
	// url to update product
	private static final String url_update_registro = "http://10.0.2.2/android_connect/registrar_asistencia.php";
	
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
	 	        if(contents.equals("30")){
	 	        	new SaveProductDetails().execute();
	 	        	/*Intent i = new Intent(MenuActivity.this, ResultadoOkActivity.class);
	 	        	startActivity(i);*/  
	 	         }else{ 
	 	        	Intent i = new Intent(MenuActivity.this, ResultadoFalloActivity.class);
	 	        	startActivity(i);
	 	         }
	 	      } else if (resultCode == RESULT_CANCELED) {
	 	         // Handle cancel
	 	      }
	 	   }
	 	}
		
		class SaveProductDetails extends AsyncTask<String, String, String> {

			/**
			 * Saving product
			 * */
			protected String doInBackground(String... args) {

				/*// getting updated data from EditTexts
				String name = txtName.getText().toString();
				String price = txtPrice.getText().toString();
				String description = txtDesc.getText().toString();*/
				String num_doc_user = "30";
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("num_doc_user", num_doc_user));
				
				// sending modified data through http request
				// Notice that update product url accepts POST method
				JSONObject json = jsonParser.makeHttpRequest(url_update_registro,
						"POST", params);

				// check json success tag
				try {
					int success = json.getInt(TAG_SUCCESS);
					
					if (success == 1) {
						Intent i = new Intent(MenuActivity.this, ResultadoOkActivity.class);
		 	        	startActivity(i);
						/*// successfully updated
						Intent i = getIntent();
						// send result code 100 to notify about product update*/
						setResult(100, i);
						finish();
					} else {
						Intent i = new Intent(MenuActivity.this, ResultadoFalloActivity.class);
		 	        	startActivity(i);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return null;
			}
		}
}
