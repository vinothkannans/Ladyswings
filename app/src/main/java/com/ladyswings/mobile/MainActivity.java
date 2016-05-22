package com.ladyswings.mobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.vinkas.webkit.BrowserLayout;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashMap<String, Object> defaults = new HashMap<>();
        defaults.put("landing_page_url", "http://ladyswings.com/community/forum.php?styleid=135");
        defaults.put("admob_banner_ad_unit_id", "");

        FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();
        config.setDefaults(defaults);
        config.fetch();
        config.activateFetched();

        String landing_page_url = config.getString("landing_page_url");
        String ad_unit_id = config.getString("admob_banner_ad_unit_id");

        BrowserLayout browserLayout = (BrowserLayout) findViewById(R.id.browser);
        browserLayout.loadUrl(landing_page_url);

        if (ad_unit_id != null && !ad_unit_id.isEmpty()) {
            AdView adView = new AdView(getApplicationContext());
            adView.setAdSize(AdSize.SMART_BANNER);
            BrowserLayout.LayoutParams params = new BrowserLayout.LayoutParams(BrowserLayout.LayoutParams.MATCH_PARENT, BrowserLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(BrowserLayout.ALIGN_PARENT_BOTTOM);
            params.addRule(BrowserLayout.CENTER_HORIZONTAL);
            adView.setLayoutParams(params);
            adView.setAdUnitId(ad_unit_id);
            browserLayout.addView(adView);

            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            adView.loadAd(adRequest);
        }
    }

}
