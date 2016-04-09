package com.nnoco.data;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.nnoco.woodman.R;

public class PlaySound {
	private static int SOUND_TREE_CLICK = 0;
	private static int SOUND_REMOVE_CLICK = 1;

	Context context;

	private SoundPool soundPool;

	public PlaySound(Context context) {
		this.context = context;

		soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 100);
		
		SOUND_TREE_CLICK = soundPool.load(context, R.raw.snd_add, 1);
		SOUND_REMOVE_CLICK = soundPool.load(context, R.raw.snd_substract, 1);
	}

	public void playSound(int sound) {
		 AudioManager mgr =
		 (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		
		 float streamVolumeCurrent =
		 mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		 float streamVolumeMax =
		 mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		 float volume = streamVolumeCurrent / streamVolumeMax;
		
		 soundPool.play(sound, volume, volume, 1, 0, 1f);

	}

	public void clickTree() {
		playSound(SOUND_TREE_CLICK);
	}

	public void clickRemove() {
		playSound(SOUND_REMOVE_CLICK);
	}
}
