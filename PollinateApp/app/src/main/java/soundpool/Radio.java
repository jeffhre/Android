package soundpool;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhrebena on 11/21/15.
 */
public class Radio {

    private static final String jl = "JEFF_RADIO";
    private static final String TRACK_FOLDER = "tracks";

    private AssetManager mAssets;
    private List<Track> mTracks;
    private SoundPool mSoundPool;

    public Radio(Context context) {
        Log.d(jl, "Radio created");
        mAssets = context.getAssets();
        mTracks = new ArrayList<Track>();


        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        String[] trackNames;

        try {
            trackNames = mAssets.list(TRACK_FOLDER);
            int i = 1;
            for (String filename : trackNames) {
                String path = TRACK_FOLDER + "/" + filename;
                Track track = new Track(path, "Track " + i);
                mTracks.add(track);

                try {
                    AssetFileDescriptor afd = mAssets.openFd(track.getPath());
                    int soundId = mSoundPool.load(afd, 1);
                    track.setId(soundId);
                } catch (IOException e) {
                    Log.e(jl, "Error loading sound from file: " + track.getPath(), e);
                }

                i = i+1;
            }
        } catch (IOException e) {
            Log.e(jl, "Error loading sound files", e);
        }

    }

    public void play(Track t) {
        Integer id = t.getId();
        if(id != null) {
            mSoundPool.play(
                    id,   // sound id
                    1.0f, // left volume
                    1.0f, // right volume
                    1,    // priority (ignored)
                    0,    // loop counter, 0 for no loop
                    1.0f  // playback rate
            );
        }
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public void release() {
        mSoundPool.release();
    }

    public Track getTrack(String name) {
        for (Track t : mTracks) {
            if (t.getPath().equals(TRACK_FOLDER + "/" + name)) {
                return t;
            }
        }
        return mTracks.get(0);
    }

}
