package com.zp.dragtocloseimageviewpager;

import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> urls;
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    //存放返回时当前页码
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urls=new ArrayList<>();
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524477979306&di=3eb07e9302606048abe13d7b6a2bc601&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201406%2F12%2F20140612211118_YYXAC.jpeg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524133463580&di=1315bc4db30999f00b89ef79c3bb06e5&imgtype=0&src=http%3A%2F%2Fpic36.photophoto.cn%2F20150710%2F0005018721870517_b.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524133463575&di=6221f21bcb761675c5d161ebc53d5948&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201410%2F03%2F20141003112442_AkkuH.thumb.700_0.jpeg");
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mainAdapter = new MainAdapter(urls);
        mainAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                ImagePagerActivity.startImagePage(MainActivity.this,
                        urls,pos,recyclerView.getLayoutManager().findViewByPosition(pos));
            }
        });
        recyclerView.setAdapter(mainAdapter);

        //设置转场动画的共享元素，因为跳转和返回都会调用，需要判断bundle是否为空
        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                if (bundle!=null){
                    int index = bundle.getInt(ImagePagerActivity.STATE_POSITION,0);
                    sharedElements.clear();
                    sharedElements.put("img", recyclerView.getLayoutManager().findViewByPosition(index));
                    bundle=null;
                }
            }
        });
    }
}
