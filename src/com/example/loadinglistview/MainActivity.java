package com.example.loadinglistview;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.ms.service.TestService;
import com.ms.service.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	// 2:声明
	private ListView listView;
	private List<String> datas;
	private ProgressDialog pd;
	private LayoutInflater inflater;
	private TestService.Client client;
	// private TTransport transport;
	private THttpClient thc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 1:加载布局文件
		setContentView(R.layout.activity_main);
		// 3:获得组件
		listView = (ListView) findViewById(R.id.listView);
		// 注意实例化
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		// 4:填写数据
		datas = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			datas.add("Jack" + i);
		}

		pd = new ProgressDialog(this);

		final MyAdapter ma = new MyAdapter();
		Button btn = (Button) View.inflate(this, R.layout.btn, null);
		// 一定要在添加数居前，设置数据头和尾
		listView.addFooterView(btn);
		// 设置适配器
		listView.setAdapter(ma);

		// 添加按钮事件
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * new Thread(){ public void run() {}; }.start();
				 */
				try {
					thc = null;
					TProtocol loPFactory = null;
					String formalUrl = "http://192.168.1.103:8080/thrifttest/androidServlet";
					thc = new THttpClient(formalUrl);
					loPFactory = new TCompactProtocol(thc);
					client = new TestService.Client(loPFactory);
					System.out.println("--------22-----");
					System.out.println(client.doLogin("ddd"));
					System.out.println("--------33-----");
				} catch (Exception e) {
					e.printStackTrace();
				}
				User u = new User();
				u.loginId = "333";
				System.out.println("--------55522-----");
				// try {
				// // thc.open();
				// // u=client.doLogin("ddd");
				// // System.out.println("--------666-----");
				// } catch (TException e) {
				// System.out.println("--------777-----");
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }finally{
				// thc.close();
				// }
				thc.close();
				for (int i = 0; i < 10; i++) {
					datas.add("Rose" + i + u.getLoginId());
				}
				// 通知数据适配器，数据发生了变化
				ma.notifyDataSetChanged();
			}
		});
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			String item = datas.get(position);
			// 获取到布局文件
			View view = inflater.inflate(R.layout.list_item, null);
			TextView title = (TextView) view.findViewById(R.id.title_name);
			title.setText(item);
			return view;
		}

	}
}
