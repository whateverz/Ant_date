package com.ant.antdate.db;


import androidx.lifecycle.ViewModel;


import com.ant.antdate.bean.Userinfo;
import com.ant.antdate.bean.Userinfo_;

import io.objectbox.Box;
import io.objectbox.android.ObjectBoxLiveData;

/**
 * Created by shuaqq on 2018/1/4.
 */

public class UserViewModel extends ViewModel {
    private ObjectBoxLiveData<Userinfo> userLiveData;

    public ObjectBoxLiveData<Userinfo> getUserLiveData(Box<Userinfo> userInfoBox) {
        if (userLiveData == null) {
            // query all notes, sorted a-z by their text (http://greenrobot.org/objectbox/documentation/queries/)
            userLiveData = new ObjectBoxLiveData<>(userInfoBox.query().notNull(Userinfo_.token).build());
        }
        return userLiveData;
    }

}
