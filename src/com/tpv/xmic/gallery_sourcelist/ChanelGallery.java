package com.tpv.xmic.gallery_sourcelist;

/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class ChanelGallery extends Activity {
	private int		mSelectedPosition;
	private int		mItemSize;
	private Gallery	mGallery;
	private Handler	mHandler;
	private boolean	mLongPressFlag	= false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_layout);
		// Set the adapter to our custom adapter (below)
		final ImageAdapter adapter = new ImageAdapter(this);
		// Reference the Gallery view
		mGallery = (Gallery) findViewById(R.id.gallery);
		mGallery.setAdapter(adapter);
		// mGallery.setSpacing(5);

		mItemSize = mGallery.getCount();
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
							// 处理完成后重置初始值，供下次相同操作检验
							mLongPressFlag = false;
						} else {
							// if haven't long press ,just show short press
							// event.
							Toast.makeText(ChanelGallery.this, "OnItemClick：" + p, Toast.LENGTH_SHORT).show();

						}
					}
				}, delayMillis);

			}
		});

		/*
		 * 长按某item操作
		 */
		mGallery.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// 长按了做好标识，以阻止遥控器短按确定同时与长按一起执行
				mLongPressFlag = true;
				new AlertDialog.Builder(ChanelGallery.this).setMessage("这是一个长按操作提示信息:" + position + "  id:" + id).show();

				return true;
			}
		});

		/*
		 * ItemSelectedListener选中item触发调用放大效果
		 */
		mGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO　选中item触发调用放大效果
				mSelectedPosition = position;
				adapter.myNotifiData(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		// We also want to show context menu for longpressed items in the
		// gallery. 注册上下文菜单
		// registerForContextMenu(mGallery);

	}

	// // 添加上下文菜单
	//
	// @Override
	// public void onCreateContextMenu(ContextMenu menu, View v,
	// ContextMenuInfo menuInfo) {
	// menu.add(R.string.gallery_2_text);// 显示Testing上下文菜单
	// }
	//
	// // item选中时显示上下文菜单（只有鼠标操作时显示？）
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
	 * 监听按键事件
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 上方向键显示菜单
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			new AlertDialog.Builder(ChanelGallery.this).setSingleChoiceItems(new String[] { "menu 1", "menu 2", "menu 3" }, 0, null).show();

		}
		// // 右方向键到头时循环跳到第一个item
		// if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
		// if (mSelectedPosition == mItemSize - 1) {
		// mGallery.setSelection(0, true);
		// }
		// }
		// // 左方向键到头时循环跳到最后一个item
		// if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
		// if (mSelectedPosition == 0) {
		// mGallery.setSelection(mItemSize - 1, false);
		// }
		// }
		return true;
	}

	/**
	 * item适配器
	 * 
	 * @author john.lin
	 * 
	 */
	public class ImageAdapter extends BaseAdapter {
		// int mGalleryItemBackground;
		int						mPosition;
		private final Context	mContext;
		/*
		 * item 资源文件id
		 */
		private final Integer[]	mImageIds	= { R.drawable.g1, R.drawable.g2, R.drawable.g3, R.drawable.g4, R.drawable.g5, R.drawable.g6,
													R.drawable.g7,

											};

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
			return mImageIds.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			int wigth;
			int height;
			int scaleValue = 40;// 放大值
			View view = View.inflate(mContext, R.layout.item, null);
			ImageView itemImgView = (ImageView) view.findViewById(R.id.item_img);
			// ImageView itemImgView = new ImageView(mContext);//
			// itemImgView.setImageResource(mImageIds[position]);
			// itemImgView.setScaleType(ImageView.ScaleType.FIT_XY);
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
			itemImgView.setBackgroundResource(mImageIds[position]);
			
			return itemImgView;
		}

		/*
		 * 外部调用函数刷新界面放大选中项
		 */
		public void myNotifiData(int position) {
			mPosition = position;
			this.notifyDataSetChanged();
		}
	}

}
