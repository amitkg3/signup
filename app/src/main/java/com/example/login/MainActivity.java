package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private TextInputLayout textInputLayoutStudentName,textInputLayoutStudentEmail,textInputLayoutStudentContactNo,textInputLayoutStudentUserName,textInputLayoutStudentPassword;
    private Spinner spinnerBranch,spinnerYear,spinnerRollNo,spinnerHostel;
    EditText editTextRoomNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textInputLayoutStudentName=findViewById(R.id.text_input_layout_student_name);
        textInputLayoutStudentEmail=findViewById(R.id.text_input_layout_student_email);
        textInputLayoutStudentContactNo=findViewById(R.id.text_input_layout_student_contactno);
        textInputLayoutStudentUserName=findViewById(R.id.text_input_layout_student_username);
        textInputLayoutStudentPassword=findViewById(R.id.text_input_layout_student_password);
        spinnerBranch=findViewById(R.id.spinner_branch);
        spinnerYear=findViewById(R.id.spinner_year);
        spinnerRollNo=findViewById(R.id.spinner_rollno);
        spinnerHostel=findViewById(R.id.spinner_hostel);
        editTextRoomNo=findViewById(R.id.edit_text_roomno);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branches, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBranch.setAdapter(adapter);
        spinnerBranch.setOnItemSelectedListener(this);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= thisYear-10; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapter2);

        ArrayList<String> rollnos = new ArrayList<String>();
        for (int i = 1; i <= 150; i++) {
            rollnos.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rollnos);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRollNo.setAdapter(adapter3);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.hostels, android.R.layout.simple_list_item_1);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHostel.setAdapter(adapter4);
        spinnerHostel.setOnItemSelectedListener(this);


        findViewById(R.id.textViewLogin).setOnClickListener(this);
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
    }

    private  void userSignUp(){
        String name= textInputLayoutStudentName.getEditText().getText().toString().trim();
        String email = textInputLayoutStudentEmail.getEditText().getText().toString().trim();
        String rollno = textInputLayoutStudentContactNo.getEditText().getText().toString().trim();
        String username = textInputLayoutStudentUserName.getEditText().getText().toString().trim();
        String password = textInputLayoutStudentPassword.getEditText().getText().toString().trim();

        //Check if Name is Empty
        if(name.isEmpty()) {
            textInputLayoutStudentName.setError("Name is required");
            return;
        }
        else{
            textInputLayoutStudentName.setError(null);
        }

        //Check if E-mail is Empty
        if(email.isEmpty()){
            textInputLayoutStudentEmail.setError("E-mail is required.");
            return;
        }
        else{
            textInputLayoutStudentEmail.setError(null);
        }

        // Validate E-mail Pattern
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputLayoutStudentEmail.setError("Please enter a valid E-mail Address.");
            return;
        }
        else{
            textInputLayoutStudentEmail.setError(null);
        }

        //Check if Roll No. is Empty
        if(rollno.isEmpty())
        {
            textInputLayoutStudentContactNo.setError("Roll No. is required.");
            return;
        }
        else{
            textInputLayoutStudentContactNo.setError(null);
        }

        //Check if UserName is Empty
        if(username.isEmpty())
        {
            textInputLayoutStudentUserName.setError("UserName is required.");
            return;
        }
        else{
            textInputLayoutStudentUserName.setError(null);
        }


        //Check if  Password is Empty
        if(password.isEmpty())
        {
            textInputLayoutStudentPassword.setError("Password is required.");
            return;
        }
        else if(password.length()<6)
        {
            textInputLayoutStudentPassword.setError("Password length should be greater than 6.");
            return;
        }
        else{
            textInputLayoutStudentPassword.setError(null);
        }



        /*  Do User Registration Here. using api call */

       Call<ResponseBody> call = RetrofitClient
               .getInstance()
               .getApi()
               .createUser(email,username,password,password);

       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

               try {
                   String s = response.body().string();
                   Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
               } catch (IOException e) {
                   e.printStackTrace();
               }


           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {
               Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

           }
       });

    }  // end of user sign up....

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonSignUp:
                //userSignUp();
                Intent myIntent = new Intent(MainActivity.this, ProfessorSignUp.class);
                //myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
                break;
            case R.id.textViewLogin:


                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
