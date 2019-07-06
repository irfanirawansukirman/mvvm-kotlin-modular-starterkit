package id.pamoyanan_dev.l_extras.util;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;

import java.util.concurrent.TimeUnit;

public class CountDownUtil {

    private static final String FORMAT = "%02d jam %02d menit %02d detik";

    public CountDownUtil() {

    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public CountDownTimer reverseTimer(long timeLong, final SingleLiveEvent<String> tv, String title) {
        return new CountDownTimer(timeLong, 1000) {
            public void onTick(long millisUntilFinished) {
                tv.setValue(String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                String message = "Waktu ";

                if (title.equals("Terbit")) {
                    message += title + " Sudah Tiba";
                } else {
                    message += "Sholat " + title + " Sudah Tiba";
                }
                tv.setValue(message);
            }
        };
    }
}
