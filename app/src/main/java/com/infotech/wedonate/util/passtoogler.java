package com.infotech.wedonate.util;

import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

public class passtoogler {
    public void tooglevisiblity(Boolean isChecked, EditText ed)
    {
        int start,end;
        // Log.i("inside checkbox chnge",""+isChecked);
        if(!isChecked){
            start=ed.getSelectionStart();
            end=ed.getSelectionEnd();
            ed.setTransformationMethod(new PasswordTransformationMethod());;
            ed.setSelection(start,end);
        }else{
            start=ed.getSelectionStart();
            end=ed.getSelectionEnd();
            ed.setTransformationMethod(null);
            ed.setSelection(start,end);
        }
    }
}
