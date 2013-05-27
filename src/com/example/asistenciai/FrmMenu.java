package com.example.asistenciai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

public class FrmMenu extends Activity {

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
	 	        if(contents.equals("prueba")){
	 	        	Intent i = new Intent(FrmMenu.this, FrmResultado.class);
	 	        	startActivity(i);  
	 	         }else{ 
	 	        	 //En caso de que sea otro link...
	 	         }
	 	      } else if (resultCode == RESULT_CANCELED) {
	 	         // Handle cancel
	 	      }
	 	   }
	 	}
	
}
