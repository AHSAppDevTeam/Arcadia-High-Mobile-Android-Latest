package com.hsappdev.ahs;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebMessage;
import android.webkit.WebMessagePort;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MapFragment extends Fragment {

    private static final String TAG = "MapFragment";

    private WebMessagePort[] webMessagePorts;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        WebView webView = view.findViewById(R.id.mapWebView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                android.util.Log.d("WebView", consoleMessage.message());
                return true;
            }
        });

        webView.loadUrl("https://map.ahs.app/map?quality=3");

        webMessagePorts = webView.createWebMessageChannel();

        for(WebMessagePort port : webMessagePorts) {
            WebMessage passwordMessage = new WebMessage("{password:\"" /*+ getResources().getString(R.string.map_secret) + "\"}"*/);



            port.setWebMessageCallback(new WebMessagePort.WebMessageCallback() {
                @Override
                public void onMessage(WebMessagePort port, WebMessage message) {
                    Log.d(TAG, "i sent a message" + message.getData());
                }
            });

            port.postMessage(passwordMessage);
        }




        return view;
    }
}