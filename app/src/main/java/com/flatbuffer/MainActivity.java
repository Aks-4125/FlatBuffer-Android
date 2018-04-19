package com.flatbuffer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flatbuffer.flatmodel.PeopleList;
import com.flatbuffer.jsonmodel.PeopleListJson;
import com.flatbuffer.utils.Utils;
import com.google.gson.Gson;

import java.nio.ByteBuffer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Aks4125 on 30/03/18.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.btnLoadFlatBuffer)
    Button btnLoadFlatBuffer;
    @BindView(R.id.btnLoadJson)
    Button btnLoadJson;
    @BindView(R.id.textViewFlat)
    TextView tvFlatbuffer;
    @BindView(R.id.textViewJson)
    TextView tvJson;
    @BindView(R.id.success)
    GifImageView success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btnLoadFlatBuffer.setVisibility(View.GONE);

    }

    private void loadFromFlatBuffer() {
        btnLoadFlatBuffer.setEnabled(false);
        tvFlatbuffer.setText("Parsing.. ");
        byte[] buffer = Utils.readRawResource(getApplication(), R.raw.sample_flatbuffer);
        long startTime = System.currentTimeMillis();
        ByteBuffer bb = ByteBuffer.wrap(buffer);
        PeopleList peopleList = PeopleList.getRootAsPeopleList(bb);
        long timeTaken = System.currentTimeMillis() - startTime;
        String logText = "FlatBuffer : " + timeTaken + "ms" + " \n size of array = " + peopleList.peoplesLength();
        tvFlatbuffer.setText(logText);
        Log.d(TAG, "loadFromFlatBuffer " + logText);
        btnLoadFlatBuffer.setEnabled(true);
        success.setVisibility(View.VISIBLE);
        tvJson.setText("");
    }

    private void loadFromJson() {
        btnLoadJson.setText("Parsing.. ");
        String jsonText = new String(Utils.readRawResource(getApplication(), R.raw.sample_json));
        long startTime = System.currentTimeMillis();
        PeopleListJson peopleList = new Gson().fromJson(jsonText, PeopleListJson.class);
        long timeTaken = System.currentTimeMillis() - startTime;
        String logText = "Json (Gson) : " + timeTaken + "ms" + " \n size of array = " + peopleList.peoples.size();
        tvJson.setText(logText);
        Log.d(TAG, "loadFromJson " + logText);
        btnLoadJson.setEnabled(true);
        btnLoadJson.setText("load From Json (Gson)");
        btnLoadFlatBuffer.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.btnLoadFlatBuffer, R.id.btnLoadJson})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLoadFlatBuffer:
                loadFromFlatBuffer();
                break;
            case R.id.btnLoadJson:
                loadFromJson();
                break;
        }
    }
}
