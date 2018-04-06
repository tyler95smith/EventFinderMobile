package com.eventfinder.www.eventfindermobile;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.eventfinder.www.eventfindermobile.api.Requests;
import com.eventfinder.www.eventfindermobile.api.VolleyHandler;
import com.eventfinder.www.eventfindermobile.api.VolleyResponseListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Profile extends AppCompatActivity implements InterestFragment.InterestListener{
    private ImageView image;
    User user;
    TextView ints;
    private static final int SELECT_PICTURE = 0;
    ArrayList<String> interests = new ArrayList<>();

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, ArrayList<String> ints) {
        interests = ints;
        updateInterestString();
        user.interests = interests;
        user.hasInterests = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageButton homebtn = (ImageButton) findViewById(R.id.home);
        ImageButton profilebtn = (ImageButton) findViewById(R.id.profile);
        ImageButton addbtn = (ImageButton) findViewById(R.id.add);
        ImageButton notbtn = (ImageButton) findViewById(R.id.notification);
        ImageButton favbtn = (ImageButton) findViewById(R.id.favorite);
        final Button edit = (Button) findViewById(R.id.editButton);
        final EditText name = (EditText) findViewById(R.id.NameBox);
        final Button addInt = (Button) findViewById(R.id.addInterests);
        final EditText about = (EditText) findViewById(R.id.aboutMe);
        final Button report = (Button)findViewById(R.id.reportUser);
        final Button pass = (Button) findViewById(R.id.changePass);
        final InterestFragment newFragment = new InterestFragment();
        final ChangePassword changep = new ChangePassword();
        final Bundle bundle = getIntent().getExtras();

        user = (User) bundle.getSerializable("user");

        if(!user.me) {
            edit.setVisibility(GONE);
            report.setVisibility(VISIBLE);
        }

        about.setText(user.bio);
        String fullName = user.firstName + " " + user.lastName;
        name.setText(fullName);
        EditText username = (EditText) findViewById(R.id.UsernameBox);
        username.setText(user.username);
        TextView gender = (TextView) findViewById(R.id.GenderBox);
        gender.setText(user.gender);
        TextView age = (TextView) findViewById(R.id.AgeBox);
        Calendar now = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
        dob.setTime(user.dateOfBirth);
        int years = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (now.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            years--;
        }
        age.setText(String.valueOf(years));
        EditText email = (EditText) findViewById(R.id.EmailBox);
        email.setText(user.email);
        interests = user.interests;
        ints = (TextView) findViewById(R.id.interestBox);
        if (user.interests != null) {
            user.hasInterests = true;
        }
        if (user.hasInterests == true) {
            updateInterestString();
            ArrayList<String> interests = user.interests;
            TextView ints = (TextView) findViewById(R.id.interestBox);
            String stringOfInterests;
            if (interests == null || interests.size() < 1) {
                stringOfInterests = "None";
            } else {
                stringOfInterests = "";
                for (String i : interests) {
                    stringOfInterests += (i + "\n");
                }
            }
            final FragmentManager fm = getFragmentManager();
            addInt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newFragment.show(fm, "interests");
                }
            });


            final android.support.v4.app.FragmentManager changeManage = getSupportFragmentManager();
            pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changep.show(changeManage, "password");
                }
            });

            image = (ImageView) findViewById(R.id.ProfilePic);

            //ints.setText(stringOfInterests);

            homebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Profile.this, HomeScreenActivity.class);
                    intent.putExtra("bundle", bundle);
                    startActivity(intent);
                }
            });

            addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Profile.this, AddEventActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            notbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Profile.this, NotificationsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            favbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Profile.this, FavoriteEventsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!name.isEnabled()) {
                        addInt.setVisibility(VISIBLE);
                        pass.setVisibility(VISIBLE);
                        name.setEnabled(true);
                        about.setEnabled(true);
                        edit.setText("Submit");
                        image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                            }
                        });
                    } else {
                        updateUser(); // i think this is where this goes
                        addInt.setVisibility(GONE);
                        pass.setVisibility(GONE);
                        name.setEnabled(false);
                        about.setEnabled(false);
                        edit.setText("Edit Profile");
                        image.setOnClickListener(null);
                    }
                }
            });
        }
    }

    public void updateInterestString() {
        String stringOfInterests = "";
        for (int i = 0; i < interests.size(); i++) {
            stringOfInterests += (interests.get(i) + "\n");
        }
        ints.setText(stringOfInterests);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            Bitmap bitmap = getPath(data.getData());
            image.setImageBitmap(bitmap);
        }
    }

    private Bitmap getPath(Uri data) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        return bitmap;
    }

    //User params (username, email, password)
    private HashMap<String, String> getUserParams()
    {
        HashMap<String, String> params = new HashMap<>();
        EditText name = (EditText)findViewById(R.id.NameBox);
        EditText username = (EditText)findViewById(R.id.UsernameBox);
        //EditText email = (EditText)findViewById(R.id.EmailBox);
        //EditText password = (EditText)findViewById(R.id.password);
        params.put("name",name.getText().toString());
        params.put("username",username.getText().toString());
        //params.put("email", email.getText().toString());
        //params.put("password", password.getText().toString());
        return params;
    }

    //All other account params other than username, email and password
    private HashMap<String, String> getAcctParams()
    {
        HashMap<String, String> params = new HashMap<String, String>();
        //EditText dob = (EditText)findViewById(R.id.date_of_birth);
        EditText bio = (EditText)findViewById(R.id.aboutMe);
        //params.put("date_of_birth", dob.getText().toString());
        params.put("bio",bio.getText().toString());
        return params;
    }

    private void getInterests() {

    }

    private void updateUser() {
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_LONG;
        //Toast.makeText(context, "Made it into the updateUser function!!!", duration).show();

        // Create listener to determine how to handle the response from the request
        VolleyResponseListener listener = new VolleyResponseListener() {
            //int duration = Toast.LENGTH_SHORT;
            @Override
            public void onError(String message) {
                Toast.makeText(context, "Oops. There was an error making the request: " + message, duration).show();
            }

            @Override
            public void onResponse(Object response) {
                //Handle JSON response... for now just shows a simple message
                Toast.makeText(context, "The request to update account was made successfully.", duration).show();
            }
        };

        // Make API request to create a new account with entered data
        JsonObjectRequest req = Requests.updatePersonalAccount(getAcctParams(), getUserParams(), listener);

        if(req != null) {
            VolleyHandler.getInstance(context).addToRequestQueue(req);
        }
    }
}
