package info.telekom.guide;


import info.telekom.guide.rest_modell.Version;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.support.design.widget.Snackbar;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class MainActivity extends AppCompatActivity {

	// fejléc tárolás
	private CharSequence mTitle;
	// pozíció megtartást segítő változók

	private String brand;

    String verName;

	private static final int RESULT_SETTINGS = 1;


    /**
	 *
	 * NavigationBar / Toolbar
     */
	private DrawerLayout mDrawer;
	private Toolbar toolbar;
	private NavigationView nvDrawer;
	private ActionBarDrawerToggle drawerToggle;

	private boolean isDark = false;
	private boolean dataSaver = false;
	private boolean tabletMode = false;


    @SuppressWarnings("ResourceType")
	@Override
	protected void onCreate(Bundle savedInstanceState) {


		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		if(sharedPrefs.getBoolean("darkMode", false)){
			setTheme(R.style.AppDarkTheme);
			isDark = true;
		}

		if(sharedPrefs.getBoolean("dataSaver", false)){
			dataSaver = true;
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// kijelző DP meghatározás

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int widthPixels = metrics.widthPixels;
		int heightPixels = metrics.heightPixels;
		float scaleFactor = metrics.density;
		float widthDp = widthPixels / scaleFactor;
		float heightDp = heightPixels / scaleFactor;
		float smallestWidth = Math.min(widthDp, heightDp);

		if (smallestWidth > 720) {
			// Ha több mint 720 DP (~Tablet) akkor landscape
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			tabletMode = true;
		}else {
			// Különben portrait
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

        CheckVersion();

		// Set a Toolbar to replace the ActionBar.
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		// Find our drawer view
		mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerToggle = setupDrawerToggle();

		// Tie DrawerLayout events to the ActionBarToggle
		mDrawer.addDrawerListener(drawerToggle);

		// Find our drawer view
		nvDrawer = (NavigationView) findViewById(R.id.nvView);
		// Setup drawer view
		setupDrawerContent(nvDrawer);


		Fragment fragment = new HomeFragment();

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
		} else { Log.e("MainActivity", "Error in creating fragment"); }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		switch (item.getItemId()) {
			case android.R.id.home:
				mDrawer.openDrawer(GravityCompat.START);
				return true;
            case R.id.menu_settings:
                Intent i = new Intent(this, UserSettingsActivity.class);
				i.putExtra("darkMode", isDark);
               	startActivityForResult(i, RESULT_SETTINGS);
                break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setupDrawerContent(NavigationView navigationView) {
		navigationView.setNavigationItemSelectedListener(
				new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(MenuItem menuItem) {
						selectDrawerItem(menuItem);
						return true;
					}
				});
	}

	public void selectDrawerItem(MenuItem menuItem) {
		Fragment fragment = new ListFragment();
		Bundle bundle = new Bundle();

		switch(menuItem.getItemId()) {
			case R.id.alcatel_frag:
				brand = "Alcatel";
				bundle.putString("brand", brand);
				fragment.setArguments(bundle);
				break;
			case R.id.apple_frag:
				brand = "Apple";
				bundle.putString("brand", brand);
				fragment.setArguments(bundle);
				break;
			case R.id.balckberry_frag:
				brand = "BlackBerry";
				bundle.putString("brand", brand);
				fragment.setArguments(bundle);
				break;
			case R.id.coolpad_frag:
				brand = "Coolpad";
				bundle.putString("brand", brand);
				fragment.setArguments(bundle);
				break;
			case R.id.honor_frag:
				brand = "Honor";
				bundle.putString("brand", brand);
				fragment.setArguments(bundle);
				break;
			case R.id.HTC_frag:
				brand = "HTC";
				bundle.putString("brand", brand);
				fragment.setArguments(bundle);
				break;
			case R.id.huawei_frag:
				brand = "Huawei";
				bundle.putString("brand", brand);
				fragment.setArguments(bundle);
				break;
			case R.id.lg_frag:
				brand = "LG";
				bundle.putString("brand", brand);
				fragment.setArguments(bundle);
				break;
			case R.id.microsoft_frag:
				brand = "Microsoft";
				bundle.putString("brand", brand);
				fragment.setArguments(bundle);
				break;
			case R.id.samsung_frag:
				brand = "Samsung";
				bundle.putString("brand", brand);
				fragment.setArguments(bundle);
				break;
			case R.id.sony_frag:
				brand = "Sony";
				bundle.putString("brand", brand);
				fragment.setArguments(bundle);
				break;

			case R.id.watch_frag:
				fragment = new WatchFragment();
				break;

			case R.id.tablet_frag:
				fragment = new TabletFragment();
				break;

			case R.id.postpaid_frag:
				fragment = new PostPaidFragment();
				break;
			case R.id.prepaid_frag:
				fragment = new PrePaidFragment();
				break;

			case R.id.net_frag:
				fragment = new ConstructionFragment();
				break;

			case R.id.magicbook_frag:
				fragment = new MagicbookFragment();
				break;

			case R.id.others_frag:
				fragment = new ConstructionFragment();
				break;
			default:
				break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
		} else { Log.e("MainActivity", "Error in creating fragment"); }

		// Highlight the selected item has been done by NavigationView
		menuItem.setChecked(true);
		// Set action bar title
		setTitle(menuItem.getTitle());
		// Close the navigation drawer
		mDrawer.closeDrawers();
	}

	/**
	 * ActionBarDrawerToggle szinkronizáció
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// Sync the toggle state after onRestoreInstanceState has occurred.
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	private ActionBarDrawerToggle setupDrawerToggle() {
		return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
	}

	/**
	 *  Publikus függvények / Metódusok
	 */

	public void sendSnack(String msg) {
		View Snack = findViewById(R.id.snack_container);

		Snackbar snackbar = Snackbar
				.make(Snack, msg, Snackbar.LENGTH_LONG);
		snackbar.show();
	}

	public boolean checkInternet(){
		ConnectivityManager connectivityManager
				= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public void sendFeedback(View view) {
		String version = BuildConfig.VERSION_NAME;

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("plain/text");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "zelena.patrikgergo@ext.telekom.hu" });
		intent.putExtra(Intent.EXTRA_SUBJECT, "Mobiltudós Guide - Visszajelző");
		intent.putExtra(Intent.EXTRA_TEXT, "Verzió: " + version);
		startActivity(Intent.createChooser(intent, ""));

	}

	public boolean getSaverMode(){
		return dataSaver;
	}

    public void  startTutorial(View view){
        Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
        startActivity(intent);
    }

	/**
	 *
	 * Back gomb gombnyomásra történő NavBar zárás vagy kilépés
     */

	@Override
	public void onBackPressed() {
		if(mDrawer.isDrawerOpen(GravityCompat.START)){
			mDrawer.closeDrawers();
		} else finish();
	}


	/**
	 *  Menu Layoutok létrehozása
	 */

	public void aboutClick(MenuItem item) {
		Fragment fragment = new AboutFragment();

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
            setTitle("Névjegy");
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

    public void changelogClick(MenuItem item) {
        Fragment fragment = new ChangeLogFragment();

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
			setTitle("Changelog");
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    /**
     * Verzió ellenőrzés (async)
     */

    private void VersionNotify(){

        NotificationManager manager;
        Notification myNotication;

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(Intent.ACTION_VIEW);

        String url = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/version/current/guide.apk";
        intent.setData(Uri.parse(url));
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(MainActivity.this);

        builder.setAutoCancel(false);
        builder.setTicker("Új verzió érhető el");
        builder.setContentTitle("Új verzió érhető el");
        builder.setContentText("Letöltéshez érintsd meg");
        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setContentIntent(pendingIntent);
        builder.setSubText("Verzió változás");   //API level 16
        builder.setNumber(100);
        builder.build();
        builder.setAutoCancel(true);

        myNotication = builder.getNotification();
        manager.notify(11, myNotication);
	}

    private class HttpRequestVersion extends AsyncTask<Void, Void, Version> {
        @Override
        protected Version doInBackground(Void... params) {
            try {
                final String url = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/version/current/ver";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Version ver= restTemplate.getForObject(url, Version.class);
                return ver;
            } catch (Exception e) {
                Log.e("List Fragment", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Version ver) {
            verName = ver.getVersion();


            if (Integer.valueOf(verName) > Integer.valueOf(BuildConfig.VERSION_CODE)){
                VersionNotify();
            }
        }
    }


    private void CheckVersion(){

        if (checkInternet() == true) {
            new HttpRequestVersion().execute();
        }

    }

	public void magicbookClick(View view){
		Intent i = new Intent(this, WebViewActivity.class);
		i.putExtra("URL","http://magicbook.telekom.intra/");
		i.putExtra("title","Magic Book");
        i.putExtra("tabMode",tabletMode);
		startActivity(i);
	}

	public void phoneBookClick(View view){
		Intent i = new Intent(this, WebViewActivity.class);
		i.putExtra("URL","http://telefonkonyv.telekom.intra/applications/phonebook/");
		i.putExtra("title","Telefonkönyv");
        i.putExtra("tabMode",tabletMode);
		startActivity(i);
	}

	public void phoneListkClick(View view){
		Intent i = new Intent(this, WebViewActivity.class);
		i.putExtra("URL","https://docs.google.com/gview?embedded=true&url=http://www.telekom.hu/static-la/sw/file/Akcios_keszulek_arlista_kijelolt_dijcsomagokhoz.pdf");
		i.putExtra("title","Készülék árlista");
        i.putExtra("tabMode",tabletMode);
		startActivity(i);
	}

	public void repairListClick(View view){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String url = "http://magicbook.telekom.intra/mb/tmobile/tevekenysegek/jav_kesz_atv/gyartok_gyartoi_szervizek.pdf";
		intent.setData(Uri.parse(url));
        intent.putExtra("tabMode",tabletMode);
		startActivity(intent);
	}

	public void stickCompClick(View view){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String url = "http://magicbook.telekom.intra/mb/tmobile/tevekenysegek/jav_kesz_atv/Adateszkozok_operacios_rendszer_kompatibilitasa.pdf";
		intent.setData(Uri.parse(url));
        intent.putExtra("tabMode",tabletMode);
		startActivity(intent);
	}

	public void accessoryListClick(View view){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String url = "http://magicbook.telekom.intra/mb/tmobile/arlistak/tartozek_arlista.xls";
		intent.setData(Uri.parse(url));
        intent.putExtra("tabMode",tabletMode);
		startActivity(intent);
	}

	public void rssRead(View view) {
		Intent i = new Intent(this, RssActivity.class);
        i.putExtra("tabMode",tabletMode);
        i.putExtra("darkMode",isDark);
		startActivity(i);
	}

    /**
	 *  Phone/Tablet Click-ek
	 */

	public void SpecAct(boolean isTab, String no){

		if(!isTab){
			Intent i = new Intent(MainActivity.this, SpecsAvtivity.class);
			i.putExtra("brand", brand);
			i.putExtra("phone", no);
			i.putExtra("darkMode", isDark);
            i.putExtra("tabMode",tabletMode);
			startActivity(i);
		} else{
			Intent i = new Intent(MainActivity.this, SpecsAvtivity.class);
			i.putExtra("brand", "tablet");
			i.putExtra("phone", no);
			i.putExtra("darkMode", isDark);
            i.putExtra("tabMode",tabletMode);
			startActivity(i);
		}
	}

	public void phone1Click(View view){

		SpecAct(false,"1");

	}
	public void phone2Click(View view){

		SpecAct(false,"2");
	}
	public void phone3Click(View view){
		SpecAct(false,"3");
	}
	public void phone4Click(View view){

		SpecAct(false,"4");
	}
	public void phone5Click(View view){

		SpecAct(false,"5");
	}
	public void phone6Click(View view){

		SpecAct(false,"6");
	}
	public void phone7Click(View view){

		SpecAct(false,"7");
	}
	public void phone8Click(View view){

		SpecAct(false,"8");
	}
	public void phone9Click(View view){

		SpecAct(false,"9");
	}
	public void phone10Click(View view){

		SpecAct(false,"10");
	}
	public void phone11Click(View view){

		SpecAct(false,"11");
	}
	public void phone12Click(View view){

		SpecAct(false,"12");
	}
	public void phone13Click(View view){

		SpecAct(false,"13");
	}
	public void phone14Click(View view){

		SpecAct(false,"14");
	}
	public void phone15Click(View view){

		SpecAct(false,"15");
	}

	public void tab1Click(View view){
        SpecAct(true,"1");
	}
	public void tab2Click(View view){
		SpecAct(true,"2");
	}
	public void tab3Click(View view){
		SpecAct(true,"3");
	}
	public void tab4Click(View view){
		SpecAct(true,"4");
	}
	public void tab5Click(View view){
		SpecAct(true,"5");
	}
	public void tab6Click(View view){
		SpecAct(true,"6");
	}
}

