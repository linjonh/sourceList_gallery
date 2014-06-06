package com.tpv.xmic.gallery_sourcelist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChanelGallery extends Activity implements OnCheckedChangeListener {
	private int				mSelectedPosition;
	private int				mItemSize		= 0;
	private Gallery			mGallery;
	private Handler			mHandler;
	private boolean			mLongPressFlag	= false;
	// CheckBoxģ���ź�Դ���
	private CheckBox		mCheckBox1, mCheckBox2, mCheckBox3, mCheckBox4, mCheckBox5, mCheckBox6, mCheckBox7;
	private CheckBox[]		arrCheckBox		= { mCheckBox1, mCheckBox2, mCheckBox3, mCheckBox4, mCheckBox5, mCheckBox6, mCheckBox7, };
	private int[]			arrCheckBoxId	= { R.id.s1, R.id.s2, R.id.s3, R.id.s4, R.id.s5, R.id.s6, R.id.s7, };
	private final Integer[]	mImageIds		= { R.drawable.g1, R.drawable.g2, R.drawable.g3, R.drawable.g4, R.drawable.g5, R.drawable.g6,
			R.drawable.g7,					};
	private final int[]		mTextString		= { R.string.s1, R.string.s2, R.string.s3, R.string.s4, R.string.s5, R.string.s6, R.string.s7 };
	private ImageAdapter	mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_layout);

		intiComponet();

	}

	private void intiComponet() {
		getViewRefrence();
		registListener(mAdapter);
		mItemSize = findSignal().size();
		mAdapter.notifyDataSetChanged();
	}

	private void getViewRefrence() {
		// Set the adapter to our custom adapter (below)
		mAdapter = new ImageAdapter(this);
		// Reference the Gallery view
		mGallery = (Gallery) findViewById(R.id.gallery);
		mGallery.setAdapter(mAdapter);
		mGallery.setSpacing(5);

		for (int i = 0; i < arrCheckBox.length; i++) {
			arrCheckBox[i] = (CheckBox) this.findViewById(arrCheckBoxId[i]);
			arrCheckBox[i].setChecked(true);
		}
	}

	private void registListener(final ImageAdapter adapter) {
		mHandler = new Handler();
		/*
		 * Set a item click listener, and just Toast the clicked position
		 */
		mGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				final int p = position;
				int delayMillis = 200;
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (mLongPressFlag) {
							// if long press, short press don't show anything.
							// ������ɺ����ó�ʼֵ�����´���ͬ��������
							mLongPressFlag = false;
						} else {
							// if haven't long press ,just show short press
							// event.
							Toast.makeText(ChanelGallery.this, "OnItemClick��" + p, Toast.LENGTH_SHORT).show();

						}
					}
				}, delayMillis);

			}
		});

		/*
		 * ����ĳitem����
		 */
		mGallery.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// ���������ñ�ʶ������ֹң�����̰�ȷ��ͬʱ�볤��һ��ִ��
				mLongPressFlag = true;
				new AlertDialog.Builder(ChanelGallery.this).setMessage("����һ������������ʾ��Ϣ:" + position + "  id:" + id).show();

				return true;
			}
		});

		/*
		 * ItemSelectedListenerѡ��item�������÷Ŵ�Ч��
		 */
		mGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO��ѡ��item�������÷Ŵ�Ч��
				mSelectedPosition = position;
				adapter.myNotifiData(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		// We also want to show context menu for longpressed items in the
		// gallery. ע�������Ĳ˵�
		// registerForContextMenu(mGallery);

		// ģ���ź�Դ
		for (int i = 0; i < arrCheckBox.length; i++) {
			arrCheckBox[i].setOnCheckedChangeListener(this);
		}

	}

	// // ��������Ĳ˵�
	//
	// @Override
	// public void onCreateContextMenu(ContextMenu menu, View v,
	// ContextMenuInfo menuInfo) {
	// menu.add(R.string.gallery_2_text);// ��ʾTesting�����Ĳ˵�
	// }
	//
	// // itemѡ��ʱ��ʾ�����Ĳ˵���ֻ��������ʱ��ʾ����
	//
	// @Override
	// public boolean onContextItemSelected(MenuItem item) {
	// AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
	// .getMenuInfo();
	// Toast.makeText(this, "Longpress: " + info.position, Toast.LENGTH_SHORT)
	// .show();
	// return true;
	// }

	/*
	 * ���������¼�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// // �Ϸ������ʾ�˵�
		// if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
		// new AlertDialog.Builder(ChanelGallery.this).setSingleChoiceItems(new
		// String[] { "menu 1", "menu 2", "menu 3" }, 0, null).show();
		//
		// }
		// // �ҷ������ͷʱѭ��������һ��item
		// if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
		// if (mSelectedPosition == mItemSize - 1) {
		// mGallery.setSelection(0, true);
		// }
		// }
		// // �������ͷʱѭ���������һ��item
		// if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
		// if (mSelectedPosition == 0) {
		// mGallery.setSelection(mItemSize - 1, false);
		// }
		// }
		return super.onKeyDown(keyCode, event);

	}

	/**
	 * item������
	 * 
	 * @author john.lin
	 * 
	 */
	public class ImageAdapter extends BaseAdapter {
		// int mGalleryItemBackground;
		int						mPosition;
		private final Context	mContext;

		/*
		 * item ��Դ�ļ�id
		 */

		public ImageAdapter(Context c) {
			mContext = c;
			// See res/values/attrs.xml for the <declare-styleable> that defines
			// Gallery1.
			TypedArray a = obtainStyledAttributes(R.styleable.ChanelGallery);
			// mGalleryItemBackground =
			// a.getResourceId(R.styleable.ChanelGallery_android_galleryItemBackground,
			// 0);
			a.recycle();
		}

		@Override
		public int getCount() {
			Log.i("getCount", "getCount" + mItemSize);
			return mItemSize;

		}

		@Override
		public Object getItem(int position) {
			Log.i("getItem", "getItem" + position);
			return position;
		}

		@Override
		public long getItemId(int position) {
			Log.i("getItemId", "getItemId" + position);
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.i("getView", "getView" + position);
			int wigth;
			int height;
			int scaleValue = 40;// �Ŵ�ֵ
			RelativeLayout view = (RelativeLayout) View.inflate(mContext, R.layout.item, null);
			ImageView itemImgView = (ImageView) view.findViewById(R.id.item_img);
			TextView textView = (TextView) view.findViewById(R.id.signal_text);
			textView.setText(getResources().getString(mTextString[position]));

			LayoutParams lay = itemImgView.getLayoutParams();
			wigth = lay.width;
			height = lay.height;
			if (position != mPosition) {
				itemImgView.setLayoutParams(new Gallery.LayoutParams(wigth, height));
			} else {
				itemImgView.setLayoutParams(new Gallery.LayoutParams(wigth + scaleValue, height + scaleValue));
			}

			// The preferred Gallery item background
			// itemImgView.setBackgroundResource(mGalleryItemBackground);
			SparseIntArray ItemArray = findSignal();
			int signal = ItemArray.get(position);
			textView.setText(getResources().getString(mTextString[signal]));
			itemImgView.setBackgroundResource(mImageIds[signal]);

			return itemImgView;
		}

		/*
		 * �ⲿ���ú���ˢ�½���Ŵ�ѡ����
		 */
		public void myNotifiData(int position) {
			mPosition = position;
			this.notifyDataSetChanged();
		}

	}

	private SparseIntArray findSignal() {

		SparseIntArray intArray = new SparseIntArray();
		intArray.clear();
		int j = 0;// �����
		int i;// �ź�Դ
		for (i = 0; i < arrCheckBox.length; i++) {
			if (arrCheckBox[i].isChecked()) {
				intArray.append(j, i);
				j++;
			}
		}
		return intArray;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		int id = buttonView.getId();
		switch (id) {
		case R.id.s1:
		case R.id.s2:
		case R.id.s3:
		case R.id.s4:
		case R.id.s5:
		case R.id.s6:
		case R.id.s7:
			mItemSize = findSignal().size();
			mAdapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}

}
