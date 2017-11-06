package edu.plan9.naseemdev.naseem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import BussinessObjects.Game_BO;

public class ViewGame extends Activity {

    private Game_BO game_bo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game);

        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        game_bo = (Game_BO) bundle.getSerializable("game_bo");

        WebView webview = (WebView) findViewById(R.id.gameview);
        WebSettings webViewSettings = webview.getSettings();
        //webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        //webViewSettings.setPluginsEnabled(true);
        //webViewSettings.setBuiltInZoomControls(true);
        webViewSettings.setPluginState(WebSettings.PluginState.ON);
        //webview.loadUrl("https://www.google.com.pk/");
        //webview.loadUrl("http://www.knowledgeadventure.com/games/ivy's-meadow/");


        String dusk_drive = "";
        String the_walls = "";
        String bullet_heaven = "";
        String the_last_dinosaurs = "";
        String roller_ride = "";
        // 99 Balls
        //webview.loadUrl("https://cloudgames.com/games/html5/99balls-new-en-s-iga-cloud/index.html?pub=10");
        //webview.loadUrl("http://cdn.htmlgames.com/JollyJong2.5/");
        webview.loadData(game_bo.getUrl(), "text/html", "utf-8");


        // Sweet Baby
        //webview.loadUrl("https://cloudgames.com/games/html5/sweet-baby-en-s-new-iga-cloud/index.html?pub=10");
        //webview.loadData("<iframe src=\"https://cloudgames.com/games/html5/sweet-baby-en-s-new-iga-cloud/index.html?pub=10\" name=\"cloudgames-com\" width=\"955\" height=\"638\" frameborder=\"0\" scrolling=\"no\"></iframe>", "text/html", "utf-8");
    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
}
