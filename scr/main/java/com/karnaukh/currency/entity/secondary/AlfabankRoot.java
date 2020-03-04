package com.karnaukh.currency.entity.secondary;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AlfabankRoot {
    @SerializedName("rates")
    private List<Alfabank> list;

    public AlfabankRoot() {
    }

    public List<Alfabank> getList() {
        return list;
    }


}
