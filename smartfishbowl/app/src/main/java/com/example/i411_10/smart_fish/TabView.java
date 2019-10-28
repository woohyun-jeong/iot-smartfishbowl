package com.example.i411_10.smart_fish;

/**
 * Created by I411-10 on 2016-07-12.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;


public class TabView extends Activity {

    private TextView temp;
    private String path;
    private VideoView mVideoView;
    ImageButton reset;
    private SharedPreferences mPref;
    private TextView  autoUpdateEnable,updateNofiti, notifiSound,datetxt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tabview);
        mPref = PreferenceManager.getDefaultSharedPreferences(this);


        autoUpdateEnable = (TextView) findViewById(R.id.autoUpdateEnable);
        updateNofiti = (TextView) findViewById(R.id.updateNofiti);
        notifiSound = (TextView) findViewById(R.id.notifiSound);
        datetxt = (TextView)findViewById(R.id.datetxt);

        getPreferencesData();


        TabHost tabHost = (TabHost) findViewById(R.id.tab_host);
        tabHost.setup();

        Intent i = getIntent();
        // Tab1 Setting
        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("Tab1");
        tabSpec1.setIndicator("정보"); // Tab Subject
        WebView view = (WebView) this.findViewById(R.id.webView);
        view.setWebViewClient(new WebViewClient());
        switch (i.getStringExtra("fish_name")) {
            case "구피":
                view.loadUrl("https://ko.wikipedia.org/wiki/%EA%B5%AC%ED%94%BC_(%EC%96%B4%EB%A5%98)");
                break;
            case "금붕어":
                view.loadUrl("https://ko.wikipedia.org/wiki/%EA%B8%88%EB%B6%95%EC%96%B4");
                break;
            case "니그로":
                view.loadUrl("https://ko.wikipedia.org/wiki/%EB%8B%88%EA%B7%B8%EB%A1%9C");
                break;
            case "디스커스":
                view.loadUrl("https://ko.wikipedia.org/wiki/%EB%94%94%EC%8A%A4%EC%BB%A4%EC%8A%A4");
                break;
            case "엔젤 피쉬":
                view.loadUrl("https://ko.wikipedia.org/wiki/%EC%97%90%EC%9D%B8%EC%A0%88%ED%94%BC%EC%8B%9C");
                break;
            case "엘리펀트 노즈":
                view.loadUrl("https://ko.wikipedia.org/wiki/%EC%BD%94%EB%81%BC%EB%A6%AC%EC%A3%BC%EB%91%A5%EC%9D%B4%EA%B3%A0%EA%B8%B0");
                break;
            case "피라냐":
                view.loadUrl("https://ko.wikipedia.org/wiki/%ED%94%BC%EB%9D%BC%EB%83%90");
                break;

        }
        view.getSettings().setJavaScriptEnabled(true);
        tabSpec1.setContent(R.id.tab_view1); // Tab Content


        tabHost.addTab(tabSpec1);

        // Tab2 Setting
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("Tab2");
        tabSpec2.setIndicator("화면"); // Tab Subject
        tabSpec2.setContent(R.id.tab_view2); // Tab Content
        tabHost.addTab(tabSpec2);


        if (!LibsChecker.checkVitamioLibs(this))
            return;
        mVideoView = (VideoView) findViewById(R.id.vitamio_videoView);
        //path = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
        path = "rtsp://192.168.159.2:8554/test";
        mVideoView.setVideoPath(path);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });

        // Tab3 Setting
        final TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("Tab3");
        tabSpec3.setIndicator("수온"); // Tab Subject
        tabSpec3.setContent(R.id.tab_view3); // Tab Content
        tabHost.addTab(tabSpec3);

        temp = (TextView) findViewById(R.id.temp);

        switch (i.getStringExtra("fish_name")) {
            case "구피":
                temp.setText("구피 적정 수온 : 24 ~ 28도\n" + "<현재 수온>");
                break;
            case "금붕어":
                temp.setText("금붕어 적정 수온 : 22 ~ 24도\n" + "<현재 수온>");
                break;
            case "니그로":
                temp.setText("니그로 적정 수온 : 24 ~ 26도\n" + "<현재 수온>");
                break;
            case "디스커스":
                temp.setText("디스커스 적정 수온 : 27 ~ 32도\n" + "<현재 수온>");
                break;
            case "엔젤 피쉬":
                temp.setText("엔젤 피쉬 적정 수온 : 24 ~ 28도\n" + "<현재 수온>");
                break;
            case "엘리펀트 노즈":
                temp.setText("엘리펀트 노즈 적정 수온 : 25 ~ 27도\n" + "<현재 수온>");
                break;

            case "피라냐":
                temp.setText("피라냐 적정 수온 : 26 ~ 28도\n" + "<현재 수온>");
                break;

        }

        final WebView view3 = (WebView) this.findViewById(R.id.webView2);
        reset = (ImageButton) findViewById(R.id.reset);
        view3.setWebViewClient(new WebViewClient());
        view3.loadUrl("http://192.168.159.3/viewdb.php");
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view3.reload();
            }
        });
        //4번탭 설정
        TabHost.TabSpec tabSpec4 = tabHost.newTabSpec("Tab4");
        tabSpec4.setIndicator("사용자 설정"); // Tab Subject
        tabSpec4.setContent(R.id.tab_view4);
        tabHost.addTab(tabSpec4);


        // show First Tab Content
        tabHost.setCurrentTab(0);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("onResume", "onResume()");

        getPreferencesData();
    }

    private void getPreferencesData() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String result =  pref.getString("histring","");
        if(result.equals(""))
            datetxt.setText("설정되지 않음");
        else
            datetxt.setText(result+"일");

        autoUpdateEnable.setText("" + mPref.getBoolean("autoUpdate", false));
        updateNofiti.setText("" + mPref.getBoolean("useUpdateNofiti", false));


        String Sound = mPref.getString("autoUpdate_ringtone", "설정되지 않음");
        if (TextUtils.isEmpty(Sound)) {
            notifiSound.setText("무음으로 설정됨");

        } else {
            Ringtone ringtone = RingtoneManager.getRingtone(getBaseContext(),
                    Uri.parse(Sound));

            if (ringtone == null) {
                notifiSound.setText(null);

            } else {
                String name = ringtone.getTitle(this);
                notifiSound.setText(name);
            }
        }
    }

    private int getArrayIndex(int array, String findIndex) {
        String[] arrayString = getResources().getStringArray(array);
        for (int e = 0; e < arrayString.length; e++) {
            if (arrayString[e].equals(findIndex))
                return e;
        }
        return -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_settings) {
            Intent SettingActivity = new Intent(this, SettingsActivity.class);
            startActivity(SettingActivity);
        }

        return super.onOptionsItemSelected(item);
    }

}
