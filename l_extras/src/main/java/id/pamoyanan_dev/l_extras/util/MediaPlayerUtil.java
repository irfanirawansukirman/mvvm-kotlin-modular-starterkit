package id.pamoyanan_dev.l_extras.util;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.HashSet;
import java.util.Objects;

// reference : https://gist.github.com/jonjensen/850599/1f529e812896efe61f217f11b9565c34589495ee
public class MediaPlayerUtil {

    private static HashSet<MediaPlayer> mpSet = new HashSet<>();
    private static MediaPlayer mp;

    public static void play(Context context, int resId) {
        mp = MediaPlayer.create(context, resId);
        mpSet.add(mp);
        mp.setOnCompletionListener(mp1 -> {
            mpSet.remove(mp1);
            mp1.stop();
            mp1.release();
        });
        mp.start();
    }

    public static void stop() {
        for (MediaPlayer mp : mpSet) {
            if (mp != null) {
                mp.stop();
                mp.release();
            }
        }
        mpSet.clear();
    }

    public static MediaPlayer getMediaPlayer() {
        Objects.requireNonNull(mp);
        return mp;
    }
}