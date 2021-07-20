package com.fogdestination.yourmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class AddPlaceAct extends AppCompatActivity {

    private EditText nameEdit;
    private TextInputLayout descEdit;
    private EditText descEditT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        nameEdit = findViewById(R.id.placeName);
        descEdit = findViewById(R.id.descInput);
        descEditT = descEdit.getEditText();
        nameEdit.setOnEditorActionListener((v, actionId, event) -> {
            nameEdit.setBackground(getDrawable(R.drawable.fonram2));
            return true;
        });
    }

    public void confirm(View view)
    {
        boolean must_fields=true;

        if(TextUtils.isEmpty(String.valueOf(nameEdit.getText())))
        {
            nameEdit.setBackground(getDrawable(R.drawable.fonram2red));
            Toast.makeText(getApplicationContext(),"name is empty",Toast.LENGTH_SHORT).show();
            must_fields=false;
        }
        String place_name = String.valueOf(nameEdit.getText());
        String place_desc = String.valueOf(descEditT.getText());

        if(must_fields)
        {
            Intent i = new Intent(AddPlaceAct.this,MapActivity.class);
            i.putExtra("place_name",place_name);
            i.putExtra("place_desc",place_desc);
            i.putExtra("type","newPlace");
            setResult(RESULT_OK,i);
            finish();
        }


    }
}