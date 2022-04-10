package com.scube.localnews.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.scube.localnews.ItemCallback;
import com.scube.localnews.R;
import com.scube.localnews.config.Constants;


public class WebViewFragment extends Fragment {
ItemCallback itemCallback;
    ProgressBar progressBar;
    WebView webView;
    public WebViewFragment(ItemCallback itemCallback) {
    this.itemCallback=itemCallback;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        webView=view.findViewById(R.id.webView_container);
         progressBar=view.findViewById(R.id.progressBar);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }

        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.webView.loadUrl(itemCallback.getItem().getLinkAddress());
    }

    @Override
    public void onPause() {
        this.webView.setVisibility(View.INVISIBLE);
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_web_view, container, false);
        return v;
    }
}