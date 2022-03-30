package com.example.drawing;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DrawView mDrawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawView = findViewById(R.id.draw_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initColorBtn(R.id.btn_black, getColor(R.color.colorBlack));
            initColorBtn(R.id.btn_red, getColor(R.color.colorRed));
            initColorBtn(R.id.btn_orange, getColor(R.color.colorOrange));
            initColorBtn(R.id.btn_yellow, getColor(R.color.colorYellow));
            initColorBtn(R.id.btn_lemon, getColor(R.color.colorLemon));
            initColorBtn(R.id.btn_green, getColor(R.color.colorGreen));
            initColorBtn(R.id.btn_dark_green, getColor(R.color.colorDarkGreen));
            initColorBtn(R.id.btn_blue, getColor(R.color.colorBlue));
            initColorBtn(R.id.btn_dark_blue, getColor(R.color.colorDarkBlue));
            initColorBtn(R.id.btn_purple, getColor(R.color.colorPurple));
            initColorBtn(R.id.btn_lilac, getColor(R.color.colorLilac));
            initColorBtn(R.id.btn_pink, getColor(R.color.colorPink));
            initColorBtn(R.id.btn_wight, getColor(R.color.colorWight));
            initColorBtn(R.id.btn_gray, getColor(R.color.colorGray));
        }

            initInstrumentBtn(R.id.btn_clean, Instrument.CLEAN_ALL);
            initInstrumentBtn(R.id.btn_point, Instrument.POIT);
            initInstrumentBtn(R.id.btn_line, Instrument.LINE);
            initInstrumentBtn(R.id.btn_curve, Instrument.CURVE);
            initInstrumentBtn(R.id.btn_square, Instrument.SQUARE);
            initInstrumentBtn(R.id.btn_polygon, Instrument.POLYGON);
    }

    private void initInstrumentBtn(int btnId, final Instrument instrument){
        ImageButton btn = findViewById(btnId);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(instrument == Instrument.CLEAN_ALL){
                    mDrawView.clean();
                } else {
                    mDrawView.setInstrument(instrument);
                }
            }
        });
    }

    private void initColorBtn(int btnColorId, final int color){
        Button btn = findViewById(btnColorId);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawView.setColor(color);
            }
        });
    }

}
