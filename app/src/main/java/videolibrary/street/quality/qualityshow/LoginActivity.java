package videolibrary.street.quality.qualityshow;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }


    public void sendMessage(View view){
        //Retrieve each field
        Context context = getApplicationContext();
        String username = ((EditText)findViewById(R.id.email)).getText().toString();
        if(username == null || username.length() == 0){
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, "incorrect username", duration).show();
        }
        else{
            String password = ((EditText)findViewById(R.id.password)).getText().toString();
            if(password == null || password.length() == 0){

                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, "incorrect password", duration).show();
            }else{
                //@TODO
                Toast.makeText(LoginActivity.this, "test login + password OK", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void launchMainActivity(){
        //@TODO
    }
}
