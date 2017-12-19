package us.xingkong.webviewdemo;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button confirm;
    Button goForward;
    EditText et_url;
    WebView webView;
    WebSettings webSettings;
    SwipeRefreshLayout swipeRefreshLayout;

    private static final String BAIDU_URL = "https://www.baidu.com/";

    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        触摸焦点起作用
        webView.requestFocus();
//        设置WebView的属性
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                et_url.setText("");
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

//        解决在用WebView浏览网页时下拉会触发SwipeRefreshLayout下拉刷新的问题。
//        注意：使用当前监听器API版本好像要用到23以上，不过这里可以声明其不报错。
        webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (webView.getScrollY() == 0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });

//        下来刷新重新加载当前URL
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });

//        初始默认加载百度
        webView.loadUrl(BAIDU_URL);
    }

    /**
     * 初始化界面
     */
    private void initView() {
        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(this);

        goForward = findViewById(R.id.go_forward);
        goForward.setOnClickListener(this);

        et_url = findViewById(R.id.net_adress);
        webView = findViewById(R.id.webView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    /**
     * 重写返回键逻辑
     * super.onBackPressed()    方法会调用自带的finish()方法。
     */

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                loadUrlFromET();
                break;
            case R.id.go_forward:
                if (webView.canGoForward()) {
                    webView.goForward();
                }
                break;
        }
    }

    /**
     * 判断是否有http头并且加载url
     */
    private void loadUrlFromET() {
        String url = et_url.getText().toString().trim();
        if (!url.startsWith("http")
                || !url.startsWith("https")
                || !url.startsWith("HTTP")
                || !url.startsWith("HTTPS")) {
            webView.loadUrl("http://" + url);
        }else {
            webView.loadUrl(url);
        }
    }
}
