package com.rnsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.rnandroidsdk.RNSDK;
import com.rnsample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.rn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RNFragmentActivity.class);
                Bundle initialProperties = new Bundle();
                initialProperties.putString("name", "Ashish");
                initialProperties.putString("viewType", "embedded");
                intent.putExtra("bundle", initialProperties);
                startActivity(intent);
            }
        });

        binding.rn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Bundle initialProperties = new Bundle();
                    initialProperties.putString("name", "Ashish");
                    initialProperties.putString("viewType", "standalone");
                    RNSDK.getInstance().startCFActivity(MainActivity.this, initialProperties);
            }
        });

    }
}
