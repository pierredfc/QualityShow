package videolibrary.street.quality.qualityshow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.strongloop.android.loopback.AccessToken;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.helpers.UserHelper;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;


public class LoginActivity extends Activity implements UserListener {

    Toolbar toolbar;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.toolbar = (Toolbar) findViewById(R.id.login_toolBar);
        this.setActionBar(this.toolbar);
        setToolbar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.login_menu_cancel) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view){
        //Retrieve each field
        Context context = getApplicationContext();
        String email = ((EditText)findViewById(R.id.emailText)).getText().toString();
        if(email == "" || email.length() == 0){
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, "Incorrect username", duration).show();
        }
        else{
            String password = ((EditText)findViewById(R.id.pwdText)).getText().toString();
            if(password == "" || password.length() == 0){

                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, "Incorrect password", duration).show();
            }else{
                userHelper = new UserHelper(context);
                userHelper.login(email, password, this);
            }
        }
    }

    @Override
    public void getAllUsers(ArrayList<User> users) {

    }

    @Override
    public void isLogged(AccessToken accessToken, User user) {
        Toast.makeText(getApplicationContext(), "Bienvenue " + user.getUsername(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        Bundle extras = new Bundle();

        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void userIsUpdated(boolean isUpdated) {

    }

    @Override
    public void userIsDeleted(boolean isDeleted) {

    }

    @Override
    public void userIsCreated(boolean user) {

    }

    @Override
    public void userIsLogout() {

    }

    @Override
    public void userIsFind(User user) {

    }

    @Override
    public void onError(Throwable t) {
        Toast.makeText(getApplicationContext(), "Erreur lors de la connexion", Toast.LENGTH_LONG).show();
    }

    private void setToolbar(){
        setTitle("Se connecter");
    }
}
