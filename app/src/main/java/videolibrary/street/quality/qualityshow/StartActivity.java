package videolibrary.street.quality.qualityshow;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends Activity implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        findViewById(R.id.no_account_button).setOnClickListener(this);
        findViewById(R.id.sign_up_button).setOnClickListener(this);
        findViewById(R.id.sign_in).setOnClickListener(this);
     }


    @Override
    public void onClick(View v) {
        if(v instanceof Button){
            Button button = (Button) v;

            switch(button.getId()){
                case R.id.no_account_button:
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                case R.id.sign_up_button:
                    startActivity(new Intent(this, SignUpActivity.class));
                    break;
                default:
                    break;
            }
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
