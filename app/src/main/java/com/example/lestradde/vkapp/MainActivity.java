package com.example.lestradde.vkapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lestradde.vkapp.menuBar.GroupsMenuItem;
import com.example.lestradde.vkapp.my.PageFragment;
import com.example.lestradde.vkapp.sliderMenu.SlidingRootNavBuilder;
import com.example.lestradde.vkapp.vkRequestDifferential.Adder;
import com.example.lestradde.vkapp.vkRequestDifferential.ThreadRequest;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiMessage;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.util.VKUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends FragmentActivity {
    public static DisplayMetrics metricsB;
    Adder adder = new Adder();
    //ThreadRequest threadRequest = new ThreadRequest(adder);
    static final String TAG = "myLogs";
    static final int PAGE_COUNT = 4;

    ViewPager pager;
    PagerAdapter pagerAdapter;


    private String [] scope = new String[]{VKScope.MESSAGES,VKScope.FRIENDS,VKScope.WALL,VKScope.PHOTOS};
    private ListView listView;
    //private ListView groupsListview;
    private ListView listViewLayout;
    //ImageView imageView = new ImageView(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.pager);
        listView = (ListView) findViewById(R.id.mainList);
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        System.out.println(Arrays.asList(fingerprints));
        VKSdk.login(this,scope);
    }
    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

                // Size screen
                Display display = getWindowManager().getDefaultDisplay();
                metricsB = new DisplayMetrics();
                display.getMetrics(metricsB);

                pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
                pager.setAdapter(pagerAdapter);
                pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int position) {
                        Log.d(TAG, "onPageSelected, position = " + position);
                    }

                    @Override
                    public void onPageScrolled(int position, float positionOffset,
                                               int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });

                //Toast.makeText(getApplicationContext(),Runtime.getRuntime().maxMemory()+"", Toast.LENGTH_LONG).show();

               /* VKRequest reqUser = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,"photo_200_orig"));
                reqUser.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        ImageView imageView;
                        imageView = (ImageView) findViewById(R.id.imageView3);
                        try {
                            new DownloadImageTask(imageView).execute(response.json.getJSONArray("response").getJSONObject(0).getString("photo_200_orig"));
                            System.out.println(response.json.getJSONArray("response").getJSONObject(0).getString("photo_200_orig"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });*/




                /*VKRequest reqUser = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,"photo_200_orig"));
                VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS,"first_name,last_name"));
                VKBatchRequest batch = new VKBatchRequest(reqUser, request);
                batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {
                    @Override
                    public void onComplete(final VKResponse[] response) {
                        super.onComplete(response);
                        //final TextView textView = (TextView) findViewById(R.id.textView);
                        //final VKList list = (VKList) response[1].parsedModel;
                        //listView = (ListView) findViewById(R.id.mainList);
                        //listViewLayout = (ListView)findViewById(R.id.listViewLayout);
                        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, list);
                            //listView.setAdapter(new MainAdapter(MainActivity.this, new String[]{response[0].json.getJSONArray("response").getJSONObject(0).getString("photo_200_orig"),"ghj"}));
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, new String[]{"ddddd","ghj"});
                            listView.setAdapter(arrayAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            String userId;
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    userId = ((VKList) response[1].parsedModel).get(position).fields.getString("id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                VKRequest requestMessage = new VKRequest("messages.getHistory",VKParameters.from(VKApiConst.USER_ID,userId, VKApiConst.COUNT,100));
                                requestMessage.executeWithListener(new VKRequest.VKRequestListener() {
                                    VKApiMessage mes;
                                    @Override
                                    public void onComplete(VKResponse response) {
                                        super.onComplete(response);
                                        try {
                                            JSONArray array = response.json.getJSONObject("response").getJSONArray("items");
                                            listView.setAdapter(new MessageRead(MainActivity.this, array));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(VKError error) {
                                        super.onError(error);
                                        //textView.setText(error+"");
                                    }
                                });
                            }
                        });

                    }
                });*/

                ImageButton newsButton = (ImageButton) findViewById(R.id.newsButton);
                newsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //VKRequest requestNews = new VKRequest("newsfeed.get");
                        final Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                        startActivity(intent);
                    }
                });

                new SlidingRootNavBuilder(MainActivity.this)
                        .withMenuLayout(R.layout.menu_left_drawer)
                        .inject();

                TextView groups = (TextView)findViewById(R.id.menuItemGroups);
                groups.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent = new Intent(MainActivity.this, GroupsMenuItem.class);
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onError(VKError error) {
                Toast.makeText(getApplicationContext(),"err", Toast.LENGTH_SHORT).show();            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

    }

}
