package com.example.hamid.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.disposables.CompositeDisposable;

public class Debounce extends AppCompatActivity {

    TextView tv_debounce ;
    EditText et_debounce ;
    private CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debounce);
        tv_debounce=(TextView)findViewById(R.id.tv_debounce);
//        et_debounce=(EditText)findViewById(R.id.et_debounce);
//           disposable.add()
    }
}
