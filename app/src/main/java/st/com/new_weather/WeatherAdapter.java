package st.com.new_weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;

public class WeatherAdapter extends RecyclerView.Adapter {

    private static String TAG = WeatherAdapter.class.getSimpleName();
    private static final int TYPE_ONE = 0;
    private static final int TYPE_TWO = 1;
    private static final int TYPE_THREE = 2;
    private static final int TYPE_FOUR = 3;

    private WeatherInfo mWeatherInfo;
    private AirQualityInfo mAirQualityInfo;
    private Context mContext;

    public WeatherAdapter(WeatherInfo weatherInfo, AirQualityInfo airQualityInfo) {
        this.mWeatherInfo = weatherInfo;
        this.mAirQualityInfo = airQualityInfo;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == WeatherAdapter.TYPE_ONE) {
            return WeatherAdapter.TYPE_ONE;
        }
        if (position == WeatherAdapter.TYPE_TWO) {
            return WeatherAdapter.TYPE_TWO;
        }
        if (position == WeatherAdapter.TYPE_THREE) {
            return WeatherAdapter.TYPE_THREE;
        }
        if (position == WeatherAdapter.TYPE_FOUR) {
            return WeatherAdapter.TYPE_FOUR;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType) {
            case TYPE_ONE:
                return new NowWeatherViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_temperature, parent, false));
            case TYPE_TWO:
                return new HoursWeatherViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_hour_info, parent, false));
            case TYPE_THREE:
                return new SuggestionViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_suggestion, parent, false));
            case TYPE_FOUR:
                return new ForecastViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_forecast, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case TYPE_ONE:
                ((NowWeatherViewHolder) holder).bind(mWeatherInfo, mAirQualityInfo);
                break;
            case TYPE_TWO:
                ((HoursWeatherViewHolder) holder).bind(mWeatherInfo, mAirQualityInfo);
                break;
            case TYPE_THREE:
                ((SuggestionViewHolder) holder).bind(mWeatherInfo, mAirQualityInfo);
                break;
            case TYPE_FOUR:
                ((ForecastViewHolder) holder).bind(mWeatherInfo, mAirQualityInfo);
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    // 当前天气情况
    class NowWeatherViewHolder extends BaseViewHolder<WeatherInfo ,AirQualityInfo> {
        @BindView(R.id.weather_location)
        TextView weatherLocation;
        @BindView(R.id.weather_icon)
        ImageView weatherIcon;
        @BindView(R.id.cond_txt_d)
        TextView weatherCondTxtD;
        @BindView(R.id.temp_flu)
        TextView tempFlu;
        @BindView(R.id.temp_max)
        TextView tempMax;
        @BindView(R.id.temp_min)
        TextView tempMin;
        @BindView(R.id.temp_pm)
        TextView tempPm;
        @BindView(R.id.temp_quality)
        TextView tempQuality;
        private String position;

        NowWeatherViewHolder(View itemView) {
            super(itemView);
        }

        protected void bind(WeatherInfo weather, AirQualityInfo airQuality) {
            try {
                weatherCondTxtD.setText(String.format("%s", weather.getHeWeather6().get(0).getNow().getCond_txt()));
                weatherLocation.setText(String.format("%s", weather.getHeWeather6().get(0).getBasic().getLocation()));
                tempFlu.setText(String.format("%s℃", weather.getHeWeather6().get(0).getNow().getTmp()));
                tempMax.setText(String.format("↑ %s ℃", weather.getHeWeather6().get(0).getDaily_forecast().get(0).getTmp_max()));
                tempMin.setText(String.format("↓ %s ℃", weather.getHeWeather6().get(0).getDaily_forecast().get(0).getTmp_min()));
                tempPm.setText(String.format("PM2.5:%sμg/m³",airQuality.getHeWeather6().get(0).getAir_now_city().getPm25()));
                tempQuality.setText(String.format("空气质量指数：%s",airQuality.getHeWeather6().get(0).getAir_now_city().getAqi()));
                position = weather.getHeWeather6().get(0).getNow().getCond_txt();
                switch (position) {
                    case "未知":
                        weatherIcon.setImageResource(R.mipmap.none);
                        break;
                    case "晴":
                        weatherIcon.setImageResource(R.mipmap.type_one_sunny);
                        break;
                    case "阴":
                        weatherIcon.setImageResource(R.mipmap.type_one_cloudy);
                        break;
                    case "多云":
                        weatherIcon.setImageResource(R.mipmap.type_one_cloudy);
                        break;
                    case "少云":
                        weatherIcon.setImageResource(R.mipmap.type_one_cloudy);
                        break;
                    case "晴间多云":
                        weatherIcon.setImageResource(R.mipmap.type_one_cloudytosunny);
                        break;
                    case "小雨":
                        weatherIcon.setImageResource(R.mipmap.type_one_light_rain);
                        break;
                    case "中雨":
                        weatherIcon.setImageResource(R.mipmap.type_one_heavy_rain);
                        break;
                    case "大雨":
                        weatherIcon.setImageResource(R.mipmap.type_one_heavy_rain);
                        break;
                    case "阵雨":
                        weatherIcon.setImageResource(R.mipmap.type_one_thunder_rain);
                        break;
                    case "雷阵雨":
                        weatherIcon.setImageResource(R.mipmap.type_one_thunder_rain);
                        break;
                    case "霾":
                        weatherIcon.setImageResource(R.mipmap.type_two_haze);
                        break;
                    case "雾":
                        weatherIcon.setImageResource(R.mipmap.type_one_fog);
                        break;
                    case "雨夹雪":
                        weatherIcon.setImageResource(R.mipmap.type_two_snowrain);
                        break;
                    case "中雪":
                        weatherIcon.setImageResource(R.mipmap.type_one_snow);
                        break;
                    default:
                        weatherIcon.setImageResource(R.mipmap.none);
                        break;
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    // 当日小时预告
    class HoursWeatherViewHolder extends BaseViewHolder<WeatherInfo, AirQualityInfo> {
        private LinearLayout itemHourInfoLayout;
        private TextView[] mClock = new TextView[4];
        private TextView[] mTemp = new TextView[4];
        private TextView[] mHumidity = new TextView[4];
        private TextView[] mWind = new TextView[4];

        HoursWeatherViewHolder(View itemView) {
            super(itemView);
            itemHourInfoLayout = (LinearLayout) itemView.findViewById(R.id.item_hour_info_linearlayout);
            for (int i = 0; i < 4; i++) {
                View view = View.inflate(mContext, R.layout.item_hour_info_line, null);
                mClock[i] = (TextView) view.findViewById(R.id.one_clock);
                mTemp[i] = (TextView) view.findViewById(R.id.one_temp);
                mHumidity[i] = (TextView) view.findViewById(R.id.one_humidity);
                mWind[i] = (TextView) view.findViewById(R.id.one_wind);
                itemHourInfoLayout.addView(view);
            }
        }

        protected void bind(WeatherInfo weather, AirQualityInfo airQuality) {

            try {
                for (int i = 0; i < 4; i++) {
                    //s.subString(s.length-3,s.length);
                    //第一个参数是开始截取的位置，第二个是结束位置。
                    String mDate = weather.getHeWeather6().get(0).getHourly().get(i).getTime();
                    mClock[i].setText(mDate.substring(mDate.length() - 5, mDate.length()));
                    mTemp[i].setText(String.format("%s℃", weather.getHeWeather6().get(0).getHourly().get(i).getTmp()));
                    mHumidity[i].setText(String.format("%s%%", weather.getHeWeather6().get(0).getHourly().get(i).getHum()));
                    mWind[i].setText(String.format("%sKm/h", weather.getHeWeather6().get(0).getHourly().get(i).getWind_spd())
                    );
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }


    // 当日建议
    class SuggestionViewHolder extends BaseViewHolder<WeatherInfo, AirQualityInfo> {
        @BindView(R.id.cloth_brief)
        TextView clothBrief;
        @BindView(R.id.cloth_txt)
        TextView clothTxt;
        @BindView(R.id.sport_brief)
        TextView sportBrief;
        @BindView(R.id.sport_txt)
        TextView sportTxt;
        @BindView(R.id.travel_brief)
        TextView travelBrief;
        @BindView(R.id.travel_txt)
        TextView travelTxt;
        @BindView(R.id.flu_brief)
        TextView fluBrief;
        @BindView(R.id.flu_txt)
        TextView fluTxt;

        SuggestionViewHolder(View itemView) {
            super(itemView);
        }

        protected void bind(WeatherInfo weather, AirQualityInfo airQuality) {

            try {
                clothBrief.setText(String.format("穿衣指数---%s", weather.getHeWeather6().get(0).getLifestyle().get(1).getBrf()));
                clothTxt.setText(mWeatherInfo.getHeWeather6().get(0).getLifestyle().get(1).getTxt());
                sportBrief.setText(String.format("运动指数---%s", weather.getHeWeather6().get(0).getLifestyle().get(3).getBrf()));
                sportTxt.setText(weather.getHeWeather6().get(0).getLifestyle().get(3).getTxt());
                travelBrief.setText(String.format("旅游指数---%s", weather.getHeWeather6().get(0).getLifestyle().get(4).getBrf()));
                travelTxt.setText(weather.getHeWeather6().get(0).getLifestyle().get(4).getTxt());
                fluBrief.setText(String.format("感冒指数---%s", weather.getHeWeather6().get(0).getLifestyle().get(2).getBrf()));
                fluTxt.setText(weather.getHeWeather6().get(0).getLifestyle().get(2).getTxt());
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    // 未来天气
    class ForecastViewHolder extends BaseViewHolder<WeatherInfo, AirQualityInfo> {

        private LinearLayout forecastLinear;
        private TextView[] forecastDate = new TextView[7];
        private TextView[] forecastTemp = new TextView[7];
        private TextView[] forecastTxt = new TextView[7];
        private ImageView[] forecastIcon = new ImageView[7];
        private String position;

        ForecastViewHolder(View itemView) {
            super(itemView);
            forecastLinear = (LinearLayout) itemView.findViewById(R.id.forecast_linear);
            for (int i = 0; i < 7; i++) {
                View view = View.inflate(mContext, R.layout.item_forecast_line, null);
                forecastDate[i] = (TextView) view.findViewById(R.id.forecast_date);
                forecastTemp[i] = (TextView) view.findViewById(R.id.forecast_temp);
                forecastTxt[i] = (TextView) view.findViewById(R.id.forecast_txt);
                forecastIcon[i] = (ImageView) view.findViewById(R.id.forecast_icon);
                forecastLinear.addView(view);
            }
        }

        protected void bind(WeatherInfo weather, AirQualityInfo airQuality) {
            try {
                //今日 明日
                forecastDate[0].setText("今日");
                forecastDate[1].setText("明日");
                for (int i = 0; i < 7; i++) {
                    if (i > 1) {
                        try {
                            forecastDate[i].setText(weather.getHeWeather6().get(0).getDaily_forecast().get(i).getDate().substring(5,weather.getHeWeather6().get(0).getDaily_forecast().get(i).getDate().length()));
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                    position = weather.getHeWeather6().get(0).getDaily_forecast().get(i).getCond_txt_d();
                    switch (position) {
                        case "未知":
                            forecastIcon[i].setImageResource(R.mipmap.none);
                            break;
                        case "晴":
                            forecastIcon[i].setImageResource(R.mipmap.type_one_sunny);
                            break;
                        case "阴":
                            forecastIcon[i].setImageResource(R.mipmap.type_one_cloudy);
                            break;
                        case "多云":
                            forecastIcon[i].setImageResource(R.mipmap.type_one_cloudy);
                            break;
                        case "少云":
                            forecastIcon[i].setImageResource(R.mipmap.type_one_cloudy);
                            break;
                        case "晴间多云":
                            forecastIcon[i].setImageResource(R.mipmap.type_one_cloudytosunny);
                            break;
                        case "小雨":
                            forecastIcon[i].setImageResource(R.mipmap.type_one_light_rain);
                            break;
                        case "中雨":
                            forecastIcon[i].setImageResource(R.mipmap.type_one_light_rain);
                            break;
                        case "大雨":
                            forecastIcon[i].setImageResource(R.mipmap.type_one_heavy_rain);
                            break;
                        case "阵雨":
                            forecastIcon[i].setImageResource(R.mipmap.type_one_heavy_rain);
                            break;
                        case "雷阵雨":
                            forecastIcon[i].setImageResource(R.mipmap.type_one_thunder_rain);
                            break;
                        case "霾":
                            forecastIcon[i].setImageResource(R.mipmap.type_two_haze);
                            break;
                        case "雾":
                            forecastIcon[i].setImageResource(R.mipmap.type_one_fog);
                            break;
                        case "雨夹雪":
                            forecastIcon[i].setImageResource(R.mipmap.type_two_snowrain);
                            break;
                        case "中雪":
                            forecastIcon[i].setImageResource(R.mipmap.type_one_snow);
                            break;
                        default:
                            forecastIcon[i].setImageResource(R.mipmap.none);
                            break;
                    }


                    forecastTemp[i].setText(
                            String.format("%s℃ - %s℃",
                                    weather.getHeWeather6().get(0).getDaily_forecast().get(i).getTmp_min(),
                                    weather.getHeWeather6().get(0).getDaily_forecast().get(i).getTmp_max()));
                    forecastTxt[i].setText(
                            String.format("%s。 %s %s %s km/h。 降水几率 %s%%。",
                                    weather.getHeWeather6().get(0).getDaily_forecast().get(i).getCond_txt_d(),
                                    weather.getHeWeather6().get(0).getDaily_forecast().get(i).getWind_sc(),
                                    weather.getHeWeather6().get(0).getDaily_forecast().get(i).getWind_dir(),
                                    weather.getHeWeather6().get(0).getDaily_forecast().get(i).getWind_spd(),
                                    weather.getHeWeather6().get(0).getDaily_forecast().get(i).getPop()));
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }
}
