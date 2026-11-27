package com.almaylente.studio;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.webkit.*;
import android.provider.MediaStore;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;
import java.io.OutputStream;

public class MainActivity extends Activity {
    private WebView web;
    private ValueCallback<Uri[]> uploadCallback;
    private static final int PICK_IMAGE = 41;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(android.graphics.Color.rgb(8,7,7));
        getWindow().setNavigationBarColor(android.graphics.Color.rgb(8,7,7));
        web = new WebView(this);
        web.setBackgroundColor(android.graphics.Color.rgb(8,7,7));
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setAllowFileAccess(true);
        web.getSettings().setAllowContentAccess(true);
        web.addJavascriptInterface(new Bridge(), "AndroidBridge");
        web.setWebChromeClient(new WebChromeClient() {
            @Override public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> callback, FileChooserParams params) {
                if (uploadCallback != null) uploadCallback.onReceiveValue(null);
                uploadCallback = callback;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Selecciona una fotografía"), PICK_IMAGE);
                return true;
            }
        });
        setContentView(web);
        web.loadUrl("file:///android_asset/index.html");
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && uploadCallback != null) {
            Uri[] result = (resultCode == Activity.RESULT_OK && data != null && data.getData() != null)
                ? new Uri[]{data.getData()} : null;
            uploadCallback.onReceiveValue(result);
            uploadCallback = null;
        }
    }

    private class Bridge {
        @JavascriptInterface public void saveImage(String dataUrl, String filename, String mime) {
            try {
                String raw = dataUrl.substring(dataUrl.indexOf(",") + 1);
                byte[] bytes = Base64.decode(raw, Base64.DEFAULT);
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
                values.put(MediaStore.Images.Media.MIME_TYPE, mime);
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Alma y Lente Studio");
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                OutputStream out = getContentResolver().openOutputStream(uri);
                out.write(bytes); out.flush(); out.close();
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Fotografía guardada en Galería", Toast.LENGTH_LONG).show());
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "No se pudo guardar la imagen", Toast.LENGTH_LONG).show());
            }
        }
    }

    @Override public void onBackPressed() {
        if (web != null && web.canGoBack()) web.goBack();
        else super.onBackPressed();
    }
}
