package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private LottieAnimationView animatedBackground;
    private TextView statusText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        animatedBackground = findViewById(R.id.animatedBackground);
        statusText = findViewById(R.id.statusText);
        progressBar = findViewById(R.id.progressBar);


        if (animatedBackground == null) {
            Log.e(TAG, "animatedBackground bulunamadı!");
        }
        if (statusText == null) {
            Log.e(TAG, "statusText bulunamadı!");
        }
        if (progressBar == null) {
            Log.e(TAG, "progressBar bulunamadı!");
        }


        animatedBackground.playAnimation();


        animatedBackground.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.d(TAG, "Animasyon tamamlandı");
                proceedToNextSteps();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void proceedToNextSteps() {
        Log.d(TAG, "proceedToNextSteps() çağrıldı");
        // İnternet bağlantısını kontrol et
        if (!isConnected()) {
            Log.d(TAG, "İnternet bağlantısı yok");
            showAlertDialog();
            return; // Eğer internet yoksa, diğer işlemleri yapma
        } else {
            Log.d(TAG, "İnternet bağlantısı var");
        }


        Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(() -> {
            Log.d(TAG, "Hava Durumu Yükleniyor...");
            if (statusText != null) {
                statusText.setText("Hava Durumu Yükleniyor...");
            }
        }, 1000);

        handler.postDelayed(() -> {
            Log.d(TAG, "OK");
            if (statusText != null) {
                statusText.setText("OK");
            }
        }, 1700);

        handler.postDelayed(() -> {
            Log.d(TAG, "Database Kontrol ediliyor...");
            if (statusText != null) {
                statusText.setText("Database Kontrol ediliyor...");
            }
        }, 2000);

        handler.postDelayed(() -> {
            Log.d(TAG, "OK");
            if (statusText != null) {
                statusText.setText("OK");
            }
        }, 2700);

        handler.postDelayed(() -> {
            Log.d(TAG, "Başlamaya Hazır!");
            if (statusText != null) {
                statusText.setText("Başlamaya Hazır!");
            }
        }, 3000);


        handler.postDelayed(() -> {
            Log.d(TAG, "MainActivity'e geçiliyor");
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 4000);
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
        }
        return false;
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Internet Bağlantısı Yok")
                .setMessage("Lütfen bağlantınızı kontrol edin.")
                .setPositiveButton("OK", (dialog, which) -> finish())
                .show();
    }
}
