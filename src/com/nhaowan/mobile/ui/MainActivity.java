package com.nhaowan.mobile.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nhaowan.mobile.R;
import com.nhaowan.mobile.base.BaseFragment;
import com.nhaowan.mobile.base.bean.InnerMenu;
import com.nhaowan.mobile.base.bean.User;
import com.nhaowan.mobile.base.view.UserSectionView;
import com.nhaowan.mobile.ui.adapter.LeftMenuAdapter;
import com.nhaowan.mobile.ui.fragment.GameNewsListFragment;
import com.nhaowan.mobile.ui.fragment.MyCricleConfFragment;
import com.nhaowan.mobile.ui.fragment.SettingFragment;

public class MainActivity extends ActionBarActivity {
	private DrawerLayout mDrawer;
	private ListView mDrawerList;
	private CustomActionBarDrawerToggle mDrawerToggle;
	private BaseFragment mBaseFragment;
	private LeftMenuAdapter leftMenuAdapter;
	private ArrayList<InnerMenu> menus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_my_main);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		_initMenu();
		_initFragment(savedInstanceState);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void _initFragment(Bundle savedInstanceState) {
		mDrawerToggle = new CustomActionBarDrawerToggle(this, mDrawer);
		mDrawer.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			openFragment(0);
		}
	}

	private void _initMenu() {
		mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout_extras);
		mDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawerList = (ListView) findViewById(R.id.drawer_extras);
		menus = new ArrayList<InnerMenu>();
		menus.add(new InnerMenu(0, getString(R.string.page_indexpage), true));
		menus.add(new InnerMenu(1, getString(R.string.page_circle), false));
		menus.add(new InnerMenu(2, getString(R.string.page_download), false));
		menus.add(new InnerMenu(3, getString(R.string.page_setting), false));
		leftMenuAdapter = new LeftMenuAdapter(this, menus);

		// addHeader
		mDrawerList.addHeaderView(addHeaderView());

		if (mDrawerList != null) {
			mDrawerList.setAdapter(leftMenuAdapter);
			mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		}
	}

	private View addHeaderView() {
//		View headerView = LayoutInflater.from(this).inflate(R.layout.user_heard_cardview, null, false);
		UserSectionView userSectionView = new UserSectionView(this);
//		CardView cardView = (CardView) headerView.findViewById(R.id.user_heard_cardview);
//		UserSectionCard userSectionView = new UserSectionCard(this);
//		cardView.setCard(userSectionView);
//		userSectionView.setOnAuthinizeListener(new IUserLogin() {
//
//			@Override
//			public void onStartLogin(PLATFORM form) {
//
//			}
//		});
		return userSectionView;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else if (item.getItemId() == R.id.menu_mycricle) {
			if(!TextUtils.isEmpty(User.getInstance().getId())) {
				openFragment(new MyCricleConfFragment());
			} else {
				openFragment(new MyCricleConfFragment());
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class CustomActionBarDrawerToggle extends ActionBarDrawerToggle {

		public CustomActionBarDrawerToggle(Activity mActivity, DrawerLayout mDrawerLayout) {
			super(mActivity, mDrawerLayout, R.drawable.ic_navigation_drawer, R.string.app_name,
					R.string.app_name);
		}

		@Override
		public void onDrawerClosed(View view) {
			invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
		}

		@Override
		public void onDrawerOpened(View drawerView) {
			getActionBar().setTitle(getString(R.string.app_name));
			invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
		}
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			InnerMenu menu = (InnerMenu) mDrawerList.getItemAtPosition(position);

			getActionBar().setTitle(menu.getTitle());
			openFragment(menu.getId());
			mDrawer.closeDrawer(mDrawerList);

			checkSelected(menu.getId());
		}
	}

	public void openFragment(int position) {
		if (position == 0) {
			// 首页
			openFragment(new GameNewsListFragment()/*GameNewsListFragment()*/);
		} else if (position == 1) {
			// 圈子
		} else if (position == 2) {
			// 下载
		} else if (position == 3) {
			// 设置
			openFragment(new SettingFragment());
		}
	}

	public void checkSelected(int id) {
		for (int i = 0; i < menus.size(); i++) {
			if (id == menus.get(i).getId()) {
				menus.get(i).setChecked(true);
			} else {
				menus.get(i).setChecked(false);
			}
		}
		leftMenuAdapter.notifyDataSetChanged();
	}

	private void openFragment(Fragment baseFragment) {
		if (baseFragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

			fragmentTransaction.replace(R.id.fragment_main_extras, baseFragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		}
	}
}
