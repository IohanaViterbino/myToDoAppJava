package com.example.mytodoapp;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button addButton;
    private LinearLayout listLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        addButton = findViewById(R.id.addButton);
        listLayout = findViewById(R.id.listLayout);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
    }

    private void addToList() {
        String text = editText.getText().toString().trim();
        if (!text.isEmpty()) {
            final RelativeLayout itemLayout = new RelativeLayout(this);
            itemLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    950,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(60, 30, 0, 0);
            itemLayout.setLayoutParams(layoutParams);
            itemLayout.setMinimumHeight(125);
            itemLayout.setBackgroundResource(R.drawable.rounded_checkbox);

            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(text);
            checkBox.setTextSize(18);
            CompoundButtonCompat.setButtonTintList(checkBox, ContextCompat.getColorStateList(this, R.color.black));
            RelativeLayout.LayoutParams checkParams = new RelativeLayout.LayoutParams(
                    850,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            checkParams.addRule(RelativeLayout.CENTER_VERTICAL);
            checkParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            checkParams.setMargins(15,15,0,15);
            checkBox.setLayoutParams(checkParams);
            itemLayout.addView(checkBox);
            Button deleteButton = new Button(this);
            deleteButton.setBackgroundResource(R.drawable.ic_x_delete);
            RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                    72,
                    72
            );
            buttonParams.addRule(RelativeLayout.CENTER_VERTICAL);
            buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            buttonParams.setMarginEnd(25);
            deleteButton.setLayoutParams(buttonParams);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listLayout.removeView(itemLayout);
                }
            });
            itemLayout.addView(deleteButton);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        checkBox.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.red));
                    } else {
                        checkBox.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                    }
                }
            });

            listLayout.addView(itemLayout);
            editText.getText().clear();
        }
    }

}
