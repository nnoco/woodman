package com.nnoco.woodman;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.nnoco.data.CompressionUtil;
import com.nnoco.data.HandleExternalData;
import com.nnoco.data.ZipUtils;

public class TransmissionActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transmission);

		init();
		setListeners();
		
		final ArrayList<HashMap<String, String>> jobs = new ArrayList<HashMap<String, String>>();

		String[] keys = { "date", "summary" };
		int[] to = { android.R.id.text1, android.R.id.text2 };
		String[] dirs = new File(HandleExternalData.BASE_DIR).list();

		File file;
		FileInputStream input;
		HashMap<String, String> map;
		for (String dir : dirs) {
			if (new File(HandleExternalData.BASE_DIR + dir).isDirectory()) {
				map = new HashMap<String, String>();
				map.put(keys[0], dir);
				try {
					file = new File(HandleExternalData.BASE_DIR + dir
							+ HandleExternalData.GENERALS);

					if (file.exists()) {
						input = new FileInputStream(file);
						byte[] buf = new byte[input.available()];
						input.read(buf);
						String info = new String(buf).replace("\r\n", ", ")
								.replace("\t", "-");
						map.put(keys[1], info);
						input.close();
					} else {
						map.put(keys[1], "기본 정보 없음");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				jobs.add(map);
			}
		}

		// 어댑터 준비
		SimpleAdapter adapter = new SimpleAdapter(this, jobs,
				android.R.layout.simple_list_item_2, keys, to);
		ListView lvJobs = (ListView) findViewById(R.id.transmission_listJob);
		lvJobs.setAdapter(adapter);
		lvJobs.setDivider(new ColorDrawable(0xffffffff));
		lvJobs.setDividerHeight(1);

		lvJobs.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int pos,
					long id) {
				HandleExternalData.loadJob(jobs.get(pos).get("date"));

				finish();
				String date = jobs.get(pos).get("date");
				String dir = HandleExternalData.BASE_DIR + date;
				try {
					CompressionUtil cu = new CompressionUtil();
					cu.zip(new File(dir));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.putExtra(Intent.EXTRA_SUBJECT, "나무꾼 " + date + " 작업 파일");
				intent.putExtra(Intent.EXTRA_STREAM,
						Uri.parse("file://" + dir + ".zip"));
				intent.putExtra(Intent.EXTRA_TEXT, "나무꾼 " + date + " 작업 파일");
				intent.setType("application/zip");
				startActivity(Intent.createChooser(intent, "파일 보내기"));
			}
		});
	}

	private void init() {
		
	}

	private void setListeners() {
		findViewById(R.id.transmission_btBack).setOnClickListener(this);
	}

	public void onClick(View v) {
		int id = v.getId();
		
		switch (id) {
		case R.id.transmission_btBack:
			finish();
			break;

		default:
			break;
		}
	}
}