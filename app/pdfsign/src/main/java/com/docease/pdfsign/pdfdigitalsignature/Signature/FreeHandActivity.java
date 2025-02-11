package com.docease.pdfsign.pdfdigitalsignature.Signature;

import android.content.Intent;
import android.content.res.Configuration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;


import com.docease.pdfsign.R;
import com.document.docease.ui.base.BaseActivity;
import com.document.docease.utils.AnalyticsManager;
import com.document.docease.utils.FirebaseEvents;

import java.util.ArrayList;

public class FreeHandActivity extends BaseActivity {
    private boolean isFreeHandCreated = false;
    private SignatureView signatureView;
    private SeekBar inkWidth;
    private Menu menu = null;
    MenuItem saveItem;
    ImageView signSave2;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_hand);
        ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);
        signatureView = findViewById(R.id.inkSignatureOverlayView);
        inkWidth = findViewById(R.id.seekBar);
        signSave2 = findViewById(R.id.signature_save_2);
        inkWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                signatureView.setStrokeWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.action_clear).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FreeHandActivity.this.clearSignature();
                FreeHandActivity.this.enableClear(false);
                FreeHandActivity.this.enableSave(false);
            }
        });

        signSave2.setOnClickListener(view -> {

            saveFreeHand();
            Intent data = new Intent();
            String text = "Result OK";
            data.setAction(text);
            setResult(RESULT_OK, data);
            finish();
        });

        imgBack = findViewById(R.id.action_back);
        imgBack.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.freehandmenu, menu);
//        this.menu = menu;
//        saveItem = menu.findItem(R.id.signature_save);
//        saveItem.setEnabled(false);
//        saveItem.getIcon().setAlpha(130);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.signature_save) {
            saveFreeHand();
            Intent data = new Intent();
            String text = "Result OK";
            data.setAction(text);
            setResult(RESULT_OK, data);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        int id = view.getId();
        if (id == R.id.radioBlack) {
            if (checked) {
                signatureView.setStrokeColor(ContextCompat.getColor(FreeHandActivity.this, R.color.inkblack));
            }
        } else if (id == R.id.radioRed) {
            if (checked)
                signatureView.setStrokeColor(ContextCompat.getColor(FreeHandActivity.this, R.color.inkred));
        } else if (id == R.id.radioBlue) {
            if (checked)
                signatureView.setStrokeColor(ContextCompat.getColor(FreeHandActivity.this, R.color.inkblue));
        } else if (id == R.id.radiogreen) {
            if (checked)
                signatureView.setStrokeColor(ContextCompat.getColor(FreeHandActivity.this, R.color.inkgreen));
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);

    }

    public void clearSignature() {
        signatureView.clear();
        signatureView.setEditable(true);
    }

    public void enableClear(boolean z) {
        ImageButton button = findViewById(R.id.action_clear);
        button.setEnabled(z);
        if (z) {
            button.setAlpha(1.0f);
        } else {
            button.setAlpha(0.5f);
        }
    }

    public void enableSave(boolean z) {
//        if (z) {
//            saveItem.getIcon().setAlpha(255);
//        } else {
//            saveItem.getIcon().setAlpha(130);
//        }
//        saveItem.setEnabled(z);
    }

    public void saveFreeHand() {
        SignatureView localSignatureView = findViewById(R.id.inkSignatureOverlayView);
        ArrayList localArrayList = localSignatureView.mInkList;
        if ((localArrayList != null) && (localArrayList.size() > 0)) {
            isFreeHandCreated = true;
        }
        SignatureUtils.saveSignature(getApplicationContext(), localSignatureView);
        AnalyticsManager.INSTANCE.logEvent(FirebaseEvents.savedSignature);
    }
}
