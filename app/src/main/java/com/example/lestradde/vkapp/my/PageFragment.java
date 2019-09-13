package com.example.lestradde.vkapp.my;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.os.ParcelableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lestradde.vkapp.MainActivity;
import com.example.lestradde.vkapp.MainAdapter;
import com.example.lestradde.vkapp.MessageRead;
import com.example.lestradde.vkapp.R;
import com.example.lestradde.vkapp.messages.MessagesRecyclerAdapter;
import com.example.lestradde.vkapp.vkRequestDifferential.Adder;
import com.example.lestradde.vkapp.vkRequestDifferential.Rea;
import com.example.lestradde.vkapp.vkRequestDifferential.ThreadRequest;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiPhotos;
import com.vk.sdk.api.model.VKApiMessage;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lestradde on 02.10.18.
 */

public class PageFragment extends Fragment {
    public RecyclerView mRecyclerView;
    public static Adder adder = new Adder();
    public static Rea rea = new Rea(adder);
    public static ThreadRequest threadRequest = new ThreadRequest(adder,rea);
    public static void methodMainAdapter(Context context, String str, RecyclerView mRecyclerView, ListView listView) {
        MainAdapter test = new MainAdapter(context, str, mRecyclerView);
        listView.setAdapter(test);
    }
    String photoAvaConvers [];
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    //public RecyclerView mRecyclerView;
    public static Bundle mBundleRecyclerViewState ;
    int pageNumber;
    ListView listView;


    public static PageFragment newInstance(int page) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        mBundleRecyclerViewState = new Bundle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View defaultView = inflater.inflate(R.layout.fragment_activity_main, null);
        switch (pageNumber) {
            case 0 :
            View view = inflater.inflate(R.layout.fragment_activity_main, null);
            listView = view.findViewById(R.id.mainList);
            //VKRequest reqUser = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_200_orig"));
            //VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS,"first_name,last_name"));

                VKRequest reqWall = new VKRequest("wall.get");
                VKRequest reqPhotos = new VKRequest("photos.getAll", VKParameters.from("count", 21));
                VKRequest reqUserInfo = new VKRequest("users.get", VKParameters.from(VKApiConst.FIELDS, "status,counters,city,photo_200_orig"));
                VKRequest [] del = new VKRequest[3];
                /*VKRequest newRec = new VKRequest("wall.get",VKParameters.from("extended", 1, "fields", VKParameters.from(VKApiConst.FIELDS, "status,counters,city,photo_200_orig")));
                newRec.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        try {
                            System.out.println("NEW REQUE"+response.json.getJSONObject("response").getJSONArray("profiles").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });*/

                del[0]= reqUserInfo;
                del[1] = reqPhotos;
                del[2] = reqWall;
                adder.addItem(del,getActivity().getApplicationContext(),listView, "MainAdapter");
                adder.starter(threadRequest);

    /*            VKBatchRequest batch = new VKBatchRequest(del);
            //VKBatchRequest batch = new VKBatchRequest(reqUserInfo, reqPhotos, reqWall);
            batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {
                @Override
                public void onComplete(final VKResponse[] response) {
                    super.onComplete(response);
                    String str = null;
                    try {
                        //System.out.println("RESP " + response[1].json.toString());
                        str = "[" + response[0].json.getJSONArray("response").getJSONObject(0).toString() + "," + response[1].json.getJSONObject("response").toString();
                        for (int i = 0; i < response[2].json.getJSONObject("response").getJSONArray("items").length(); i++) {
                            str += "," + response[2].json.getJSONObject("response").getJSONArray("items").getJSONObject(i).toString();
                        }
                        str += "]";
                        //System.out.println(str);
                        //listView.setAdapter(new MainAdapter(getActivity().getApplicationContext(), new JSONArray[]{response[0].json.getJSONArray("response"),response[1].json.getJSONArray("response"),response[2].json.getJSONObject("response").getJSONArray("items"), new JSONArray(response[3].json.getJSONObject("response"))}));
                        listView.setAdapter(new MainAdapter(getActivity().getApplicationContext(), str, mRecyclerView));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(VKError error) {
                    super.onError(error);
                    System.out.println(error + " ошибка");
                }
            });*/

            return view;

            case 1:
                defaultView = inflater.inflate(R.layout.fragment_activity_main, null);
                listView = defaultView.findViewById(R.id.mainList);
                listView.setBackgroundColor(Color.BLUE);
                VKRequest reqMessage = new VKRequest("messages.getConversations", VKParameters.from("count",20));
                VKRequest [] del1 = new VKRequest[1];
                del1[0]=reqMessage;
                //adder.addItem(del1,getActivity().getApplicationContext(),listView, "MessagesRecyclerAdapter");
                //adder.starter(threadRequest);
                /*reqMessage.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        ArrayList<VKRequest> vkRequests = null;
                        VKRequest request;
                        try {
                            for (int i = 0; i< response.json.getJSONObject("response").getJSONArray("items").length(); i++){
                                request =new VKRequest("users.get", VKParameters.from("user_ids", response.json.getJSONObject("response").getJSONArray("items").getJSONObject(i).getJSONObject("conversation").getJSONObject("peer").getString("id"), "fields", "photo_50"));
                                //vkRequests.add(request);
                            }

                            //listView.setAdapter(new MessagesRecyclerAdapter(getActivity().getApplicationContext(),response.json.getJSONObject("response").getJSONArray("items")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });*/
                return defaultView;

        }
        return defaultView;
    }
}
