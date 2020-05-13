package com.sliit.sliittutemad05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
{
    EditText txtID , txtName , txtAdd , txtConNo;
    Button btnShow , btnSave , btnDelete , btnUpdate;
    DatabaseReference dbRef;
    Student std;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtID = (EditText) findViewById(R.id.EtID);
        txtName = (EditText) findViewById(R.id.EtName);
        txtAdd = (EditText) findViewById(R.id.EtAddress);
        txtConNo = (EditText) findViewById(R.id.EtConNo);

        btnShow = (Button) findViewById(R.id.BtnShow);
        btnSave = (Button) findViewById(R.id.BtnSave);
        btnDelete = (Button) findViewById(R.id.BtnDelete);
        btnUpdate = (Button) findViewById(R.id.BtnUpdate);

        std = new Student();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Student");
                try{
                    if (TextUtils.isEmpty(txtID.getText().toString()))
                    {
                        Toast.makeText(MainActivity.this, "Please enter an ID", Toast.LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(txtName.getText().toString()))
                    {
                        Toast.makeText(MainActivity.this, "Please enter an name", Toast.LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(txtAdd.getText().toString()))
                    {
                        Toast.makeText(MainActivity.this, "Please enter an address", Toast.LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(txtConNo.getText().toString()))
                    {
                        Toast.makeText(MainActivity.this, "Please enter an contact number", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        std.setID(txtID.getText().toString().trim());
                        std.setName(txtName.getText().toString().trim());
                        std.setAddress(txtAdd.getText().toString().trim());
                        std.setConNo(Integer.parseInt(txtConNo.getText().toString().trim()));

                        //dbRef.push().setValue(std1);
                        dbRef.child("std1").setValue(std);


                        Toast.makeText(MainActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                }catch (NumberFormatException e)
                {
                    Toast.makeText(getApplicationContext(), "Invalid contact number", Toast.LENGTH_SHORT).show();
                }
            }
        });



        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Student").child("std1");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.hasChildren())
                        {
                            txtID.setText(dataSnapshot.child("id").getValue().toString());
                            txtID.setText(dataSnapshot.child("name").getValue().toString());
                            txtID.setText(dataSnapshot.child("address").getValue().toString());
                            txtID.setText(dataSnapshot.child("conNo").getValue().toString());

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "no source to display", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Student");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.hasChild("std1"))
                        {
                            try{

                                std.setID(txtID.getText().toString().trim());
                                std.setName(txtName.getText().toString().trim());
                                std.setAddress(txtAdd.getText().toString().trim());
                                std.setConNo(Integer.parseInt(txtConNo.getText().toString().trim()));

                                dbRef = FirebaseDatabase.getInstance().getReference().child("Student").child("std1");
                                dbRef.setValue(std);
                                clearControls();
                                Toast.makeText(getApplicationContext(), "Record updated successfully", Toast.LENGTH_SHORT).show();
                            }
                            catch (NumberFormatException e)
                            {
                                Toast.makeText(getApplicationContext(), "Invalid contact number", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "No source to update", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {

                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Student");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.hasChild("std1"))
                        {
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Student").child("std1");
                            dbRef.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(), "record removed successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "No source to delete", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    public void clearControls()
    {
        txtID.setText("");
        txtName.setText("");
        txtAdd.setText("");
        txtConNo.setText("");


    }
}
