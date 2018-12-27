package st.com.new_weather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.iv_erro)
    ImageView mIvError;

    private View view;

    private WeatherAdapter mWeatherAdapter;

    Gson gson = new Gson();
    private String responseData; // 从url获取的json信息
    private String airResponseData;
    private WeatherInfo mWeatherInfo =  new WeatherInfo(); // 天气信息对象json——>class对象
    private AirQualityInfo mAirQualityInfo = new AirQualityInfo();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.content_main, container, false);
            ButterKnife.bind(this, view);
        }
        sendRequestWithOkhttp();
        initView();
        mIsCreateView = true;
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWeatherAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void sendRequestWithOkhttp() {
        String url = VariableApp.getUrl();
        String airurl = VariableApp.getairUrl();

        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 处理异常情况
                Log.e("执行请求失败：","网络异常？");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 得到服务器返回的具体内容
                responseData = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWeatherInfo.setHeWeather6(gson.fromJson(responseData, WeatherInfo.class).getHeWeather6());
                        mWeatherAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        HttpUtil.sendOkHttpRequest(airurl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 处理异常情况
                Log.e("执行请求失败：","网络异常？");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                airResponseData = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAirQualityInfo.setHeWeather6(gson.fromJson(airResponseData, AirQualityInfo.class).getHeWeather6());
                        mWeatherAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }



    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWeatherAdapter = new WeatherAdapter(mWeatherInfo, mAirQualityInfo);
        mRecyclerView.setAdapter(mWeatherAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // 加载数据操作,在视图创建之前初始化
    @Override
    protected void lazyLoad() {
        sendRequestWithOkhttp();
        initView();
    }

//    public WeatherInfo getmWeatherInfo() {
//        return mWeatherInfo;
//    }
}
