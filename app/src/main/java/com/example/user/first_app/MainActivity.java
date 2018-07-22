package com.example.user.first_app;

import android.content.Context;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.nio.file.WatchEvent;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    ProgressBar progressBar;
    WebView webView;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.edit_text);

        progressBar=findViewById(R.id.progressbar);
        progressBar.setMax(100);
        progressBar.setVisibility(View.GONE);

        button=findViewById(R.id.button);

        webView=findViewById(R.id.webview);
        if(savedInstanceState != null){
          webView.restoreState(savedInstanceState);
        }
        else {
           webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(false);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.setBackgroundColor(Color.WHITE);

            webView.setWebViewClient( new ourViewClient());
            webView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int Progress) {
                    progressBar.setProgress(Progress);
                    if(Progress <100 &&  progressBar.getVisibility() == progressBar.GONE){
                        progressBar.setVisibility(progressBar.VISIBLE);
                    }
                    if(Progress == 100){
                     progressBar.setVisibility(progressBar.GONE);
                    }
                }
            });
        }


       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
               inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken() ,InputMethodManager.HIDE_NOT_ALWAYS);
               webView.loadUrl(" https://" + editText.getText().toString());
               editText.setText("");
           }
       });

    }
    public class ourViewClient extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            CookieManager.getInstance().setAcceptCookie(true);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu ,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.back:
                if(webView.canGoBack()){
                    webView.goBack();
                }
                return true;

            case R.id.forward:
                if(webView.canGoForward()){
                    webView.goForward();
                }
                return true;

            case R.id.home:
                InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken() ,InputMethodManager.HIDE_NOT_ALWAYS);
                webView.loadUrl("https://google.com");
                editText.setText("");
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}
