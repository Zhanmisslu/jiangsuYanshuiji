package com.example.zhan.heathmanage.Main.EvaluteFragment.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.zhan.heathmanage.BasicsTools.HomeKeyWatcher;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

public class CompatHomeKeyFragment extends Fragment {
    private boolean pressedHome;
    private HomeKeyWatcher mHomeKeyWatcher;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHomeKeyWatcher = new HomeKeyWatcher(getActivity());
        mHomeKeyWatcher.setOnHomePressedListener(new HomeKeyWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                pressedHome = true;
            }
        });
        pressedHome = false;
        mHomeKeyWatcher.startWatch();
    }

    @Override
    public void onStart() {
        mHomeKeyWatcher.startWatch();
        pressedHome = false;
        super.onStart();
        NiceVideoPlayerManager.instance().resumeNiceVideoPlayer();
    }

    @Override
    public void onStop() {
        // 在OnStop中是release还是suspend播放器，需要看是不是因为按了Home键
        if (pressedHome) {
            NiceVideoPlayerManager.instance().suspendNiceVideoPlayer();
        } else {
            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        }
        super.onStop();
        mHomeKeyWatcher.stopWatch();
    }
}
