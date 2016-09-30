package wang.kobi.sequencealertview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MacBook on 16/9/30.
 */
public class AlertViewManager {
    static List<AlertView> alertViewList;


    public static void add(AlertView view){
        if(alertViewList == null){
            alertViewList = new ArrayList<>();
        }
        alertViewList.add(view);
    }

    public static void remove(AlertView view){
        if(alertViewList != null){
            alertViewList.remove(view);
        }
    }

    public static AlertView getNext(){

        if(alertViewList == null || alertViewList.size() == 0) return null;
        return alertViewList.get(0);
    }




}
