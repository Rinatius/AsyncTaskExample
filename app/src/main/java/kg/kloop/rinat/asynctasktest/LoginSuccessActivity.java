package kg.kloop.rinat.asynctasktest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.exceptions.BackendlessFault;

import java.net.URL;

public class LoginSuccessActivity extends Activity
{
    private Button logoutButton;
    private TextView loginText;
    private Button getSubjectsButton;

  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.login_success );

    initUI();
  }

  private void initUI()
  {
      logoutButton = (Button) findViewById( R.id.logoutButton );
      getSubjectsButton = (Button) findViewById( R.id.listSubjectsButton );

      getSubjectsButton.setOnClickListener( new View.OnClickListener() {
        @Override
        public void onClick( View view ) {
            new ListSubjectsTask().execute();
        }
      } );

      logoutButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
              onLogoutButtonClicked();
            }
        } );

      loginText = (TextView)findViewById(R.id.loginText);
  }

  private void onLogoutButtonClicked()
  {
    Backendless.UserService.logout( new DefaultCallback<Void>( this )
    {
      @Override
      public void handleResponse( Void response )
      {
        super.handleResponse( response );
        startActivity( new Intent( LoginSuccessActivity.this, LoginActivity.class ) );
        finish();
      }

      @Override
      public void handleFault( BackendlessFault fault )
      {
        if( fault.getCode().equals( "3023" ) ) // Unable to logout: not logged in (session expired, etc.)
          handleResponse( null );
        else
          super.handleFault( fault );
      }
    } );

  }



  private class ListSubjectsTask extends AsyncTask<Integer, Integer, BackendlessCollection<Subject>> {
    protected BackendlessCollection<Subject> doInBackground(Integer... num) {

        return Db.getAllSubjects();
    }

    protected void onProgressUpdate(Integer... progress) {

    }

    protected void onPostExecute(BackendlessCollection<Subject> result) {

        updateList(result);
    }


  }

    private void updateList(BackendlessCollection<Subject> list){
        loginText.setText(list.getData().toString());
    }

}