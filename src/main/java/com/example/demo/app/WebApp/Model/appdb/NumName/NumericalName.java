package com.example.demo.app.appdb.NumName;

import lombok.Data;

@Data
public class NumericalName {
    private String id;
    private String numName;
    private String min;
    private String max;

    public NumericalName() {
        // デフォルトコンストラクタ
    }

    public NumericalName(String id, String numName, String min, String max) {
        this.id = id;
        this.numName = numName;
        this.min = min;
        this.max = max;
    }

    // ゲッターセッターなどのメソッドも追加する
}
