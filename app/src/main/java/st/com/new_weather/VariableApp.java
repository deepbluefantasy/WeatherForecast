package st.com.new_weather;

import android.app.Application;
import android.util.Log;
import java.util.List;

public class VariableApp extends Application {
    private static VariableApp instance = null;
    private static String head1 = "https://free-api.heweather.net/s6/weather?location=";
    private static String head2 = "https://free-api.heweather.net/s6/air/now?location=";
    private static String key = "&key=d6cfdce8193445ba894d889e7ab2de5b";
    private static String location = "扬州";


    public static VariableApp getInstance(){
        Log.e("variable", location);
        return instance;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        head1 = "https://free-api.heweather.net/s6/weather?location=";
        head2 = "https://free-api.heweather.net/s6/air/now?location=";
        key = "&key=d6cfdce8193445ba894d889e7ab2de5b";
        location = "南京";
        Log.e("variable", location);
    }

    public String getHead1(){
        return head1;
    }

    public static String getHead2(){
        return head2;
    }

    public static String getKey(){
        return head1;
    }

    public  static String getLocation(){
        return head1;
    }

    public static String getUrl(){
        return head1 + location + key;
    }

    public static String getairUrl() {
        return head2 + location + key;
    }

    public static void setLocation(String loc){
        location = loc;
    }


}