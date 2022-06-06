package com.usman.testhms3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.usman.testhms3.HMS_Utils.NetworkChangerListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Register_Activity extends AppCompatActivity {

    private static final Pattern PHONENUMBER_PATERN=Pattern.compile( "(0/91)?[7-9][0-9]{9}" );

    ImageView imgprev;
    TextInputLayout student_name, student_phone, student_email, student_rollno, student_batch, student_parentage, student_parent_phonenumber, student_pincode, student_address;
    Button browse, submit_form;
    String encodedImageString,otpid;
    Bitmap bitmap;
    Spinner branchSpinner;
    ProgressBar progressbar112;
    int rand_password;
    Dialog dialog;
    FirebaseAuth mAuth;
    String branch;

    NetworkChangerListener networkChangerListener = new NetworkChangerListener();
    public static final String UPLOAD_URL = "https://thedawg.000webhostapp.com/API-Android/studentDetailUpload.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        getSupportActionBar().hide();
        student_name = (TextInputLayout) findViewById( R.id.student_name );
        student_phone = (TextInputLayout) findViewById( R.id.student_phone );
        student_email = (TextInputLayout) findViewById( R.id.student_email );
        student_rollno = (TextInputLayout) findViewById( R.id.student_rollno );
        student_phone = (TextInputLayout) findViewById( R.id.student_phone );
        student_batch = (TextInputLayout) findViewById( R.id.student_batch );
        student_parentage = (TextInputLayout) findViewById( R.id.student_parentage );
        student_parent_phonenumber = (TextInputLayout) findViewById( R.id.student_parent_phonenumber );
        student_pincode = (TextInputLayout) findViewById( R.id.student_pincode );
        student_address = (TextInputLayout) findViewById( R.id.student_address );

        mAuth=FirebaseAuth.getInstance();

//        otp dialog
        dialog=new Dialog( Register_Activity.this );
        dialog.setContentView( R.layout.custom_otp_dialog );
        dialog.getWindow().setBackgroundDrawable( getDrawable( R.drawable.otp_dialog_background ) );
        dialog.setCancelable( false );
        dialog.getWindow().getAttributes().windowAnimations=R.style.animation1;
        TextInputLayout otp_text = dialog.findViewById( R.id.otp_text );
        Button submit_otp = dialog.findViewById( R.id.submit_otp );
        ProgressBar progressbar3333= dialog.findViewById( R.id.progressbar3333 );
        ImageButton cancel_dialog = dialog.findViewById( R.id.cancel_dialog );
        cancel_dialog.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        } );
        submit_otp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp_text.getEditText().getText().toString().isEmpty()){
                    Toast.makeText( Register_Activity.this, "NO Data Provided", Toast.LENGTH_SHORT ).show();

                }else if(otp_text.getEditText().getText().toString().length()!=6){
                    Toast.makeText( Register_Activity.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT ).show();
                }
                else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential( otpid,otp_text.getEditText().getText().toString().trim());
                    signInWithPhoneAuthCredential( credential );
                    Toast.makeText( Register_Activity.this, "Verification  Successful", Toast.LENGTH_SHORT ).show();
                    submit_otp.setVisibility( View.INVISIBLE );
                    progressbar3333.setVisibility( View.VISIBLE );
//                    submit_form.setVisibility( View.INVISIBLE );
//                    progressbar112.setVisibility( View.VISIBLE );
                }
            }
        } );

        //dialog Ends Here



        Random ran = new Random();
        rand_password = ran.nextInt(999999);
        Log.e( "me",String.valueOf( rand_password ) );
        progressbar112 = findViewById( R.id.progressbar112 );


        browse = findViewById( R.id.select_photo );
        submit_form = findViewById( R.id.submit_form );

        imgprev = (ImageView) findViewById( R.id.imgprev );
//        items of Branch Spinner
        branchSpinner = findViewById( R.id.student_branch_spinner );
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this, R.array.branches, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item );
        adapter.setDropDownViewResource( androidx.appcompat.R.layout.support_simple_spinner_dropdown_item );
        branchSpinner.setAdapter( adapter );
        branchSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branch =  branchSpinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );

        browse.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity( Register_Activity.this )
                        .withPermission( Manifest.permission.READ_EXTERNAL_STORAGE )
                        .withListener( new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent( Intent.ACTION_PICK );
                                intent.setType( "image/*" );
                                activityResultLauncher.launch( intent );
//                        startActivityForResult( Intent.createChooser( intent,"Browse Image" ),1 );
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                Log.e( "me", "denied " );
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        } ).check();
            }
        } );


        //        submitt button
        submit_form.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validatename() | !validateemail() | !validatephone() | !validaterollno() | !validatebatch() | !validateparentage() | !validateparentphone() | !validatepincode() | !validateaddress()) {
                    return;
                }
                dialog.show();
                initiateotp();
            }
        } );


    }//On Create Ende here

    private void initiateotp()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                student_phone.getEditText().getText().toString().trim(),
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                {
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                    {
                        otpid=s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
                    {
                        signInWithPhoneAuthCredential( phoneAuthCredential );
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText( Register_Activity.this, e.toString(), Toast.LENGTH_LONG ).show();
                    }
                });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uploadDataToDb();
                            Log.d("me", "signInWithCredential:success");
                        } else {
                            Log.w("me", "signInWithCredential:failure", task.getException());
                            Toast.makeText( Register_Activity.this, "Failed To SignUp", Toast.LENGTH_SHORT ).show();
                        }
                    }
                });
    }



    public boolean validatename() {
        String name = student_name.getEditText().getText().toString().trim();
        if (name.isEmpty()) {
            student_name.setError( "Field Can't be empty" );
            return false;
        } else {
            student_name.setError( null );
            return true;
        }
    }

    public boolean validateemail() {
        String email = student_email.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            student_email.setError( "Field Can't be empty" );
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher( email ).matches()) {
            student_email.setError( "Please Enter A Valid Email Address" );
            return false;
        } else {
            student_email.setError( null );
            return true;
        }
    }

    public boolean validatephone() {
        String phno = student_phone.getEditText().getText().toString().trim();
        if (phno.isEmpty()) {
            student_phone.setError( "Field Can't be empty" );
            return false;
        }
//        else if (!PHONENUMBER_PATERN.matcher( phno ).matches()) {
//            student_phone.setError( "Please Enter valid 10 Digit Phone number" );
//            return false;
//        }
        else {
            student_phone.setError( null );
            return true;
        }
    }

    public boolean validaterollno() {
        String rollno = student_rollno.getEditText().getText().toString().trim();
        if (rollno.isEmpty()) {
            student_rollno.setError( "Field Can't be empty" );
            return false;
        } else {
            student_rollno.setError( null );
            return true;
        }
    }

    public boolean validatebatch() {
        String batch = student_batch.getEditText().getText().toString().trim();
        if (batch.isEmpty()) {
            student_batch.setError( "Field Can't be empty" );
            return false;
        } else {
            student_batch.setError( null );
            return true;
        }
    }

    public boolean validateparentage() {
        String parentage = student_parentage.getEditText().getText().toString().trim();
        if (parentage.isEmpty()) {
            student_parentage.setError( "Field Can't be empty" );
            return false;
        } else {
            student_parentage.setError( null );
            return true;
        }
    }

    public boolean validateparentphone() {
        String parentphone = student_parent_phonenumber.getEditText().getText().toString().trim();
        if (parentphone.isEmpty()) {
            student_parent_phonenumber.setError( "Field Can't be empty" );
            return false;
        } else {
            student_parent_phonenumber.setError( null );
            return true;
        }
    }

    public boolean validatepincode() {
        String pincode = student_pincode.getEditText().getText().toString().trim();
        if (pincode.isEmpty()) {
            student_pincode.setError( "Field Can't be empty" );
            return false;
        }else if(pincode.length()>=7) {
            student_pincode.setError( "Pincode Cannot Be That Long " );
            return false;
        } else
         {
            student_pincode.setError( null );
            return true;
        }
    }

    public boolean validateaddress() {
        String address = student_address.getEditText().getText().toString().trim();
        if (address.isEmpty()) {
            student_address.setError( "Field Can't be empty" );
            return false;
        } else {
            student_address.setError( null );
            return true;
        }
    }


    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri filepath = data.getData();
                        try {

                            InputStream inputStream = getContentResolver().openInputStream( filepath );
                            bitmap = BitmapFactory.decodeStream( inputStream );
                            imgprev.setImageBitmap( bitmap );
                            encodeBitmapImage( bitmap );
                        } catch (Exception ex) {
                            Log.e( "me", "exception     " + ex.toString() );
                        }
                    }


                }
            }
    );


    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream );
        byte[] bytesofImage = byteArrayOutputStream.toByteArray();
        encodedImageString = android.util.Base64.encodeToString( bytesofImage, Base64.DEFAULT );
    }


    private void uploadDataToDb() {
        String name = student_name.getEditText().getText().toString().trim();
        String email = student_email.getEditText().getText().toString().trim();
        String phone = student_phone.getEditText().getText().toString().trim();
        String rollno = student_rollno.getEditText().getText().toString().trim();
        String sphone = student_phone.getEditText().getText().toString().trim();
        String batch = student_batch.getEditText().getText().toString().trim();
        String parentage = student_parentage.getEditText().getText().toString().trim();
        String parent_phonenumber = student_parent_phonenumber.getEditText().getText().toString().trim();
        String pincode = student_pincode.getEditText().getText().toString().trim();
        String address = student_address.getEditText().getText().toString().trim();
//        String branch ="";


        Log.e( "me", "in onclick5" );
        StringRequest request = new StringRequest( Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                imgprev.setImageResource( R.drawable.ic_baseline_person );
                Intent intent = new Intent( Register_Activity.this, Login_Activity.class );
                startActivity( intent );
                finish();
                Toast.makeText( Register_Activity.this, response.toString(), Toast.LENGTH_SHORT ).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                submit_form.setVisibility( View.VISIBLE );
                progressbar112.setVisibility( View.INVISIBLE );
                Toast.makeText( Register_Activity.this, error.toString(), Toast.LENGTH_SHORT ).show();
            }
        } ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put( "name", name );
                map.put( "email", email );
                map.put( "phone", phone );
                map.put( "rollno", rollno );
                map.put( "sphone", sphone );
                map.put( "batch", batch );
                map.put( "parentage", parentage );
                map.put( "parent_phonenumber", parent_phonenumber );
                map.put( "pincode", pincode );
                map.put( "address", address );
                map.put( "upload", encodedImageString );
                map.put( "branch", branch );
                map.put( "password", String.valueOf( rand_password ) );

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue( getApplicationContext() );
        queue.add( request );
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION );
        registerReceiver( networkChangerListener, filter );
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver( networkChangerListener );
        super.onStop();
    }


}