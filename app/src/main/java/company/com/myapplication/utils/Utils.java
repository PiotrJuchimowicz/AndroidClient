package company.com.myapplication.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class Utils {
    public static void createToast(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    public static boolean isInputEmpty(EditText editText){
        return editText.getText().toString().trim().length() == 0;
    }
}
