package com.qms.mainservice.domain.model.valueobject;

import lombok.Getter;

@Getter
public enum TagColor {
    LightGray("#E9E9E9", "black"),
    Gray("#C0C0C0", "black"),
    Brown("#D29494", "black"),
    Orange("#FFD280", "black"),
    Yellow("#FFFF80", "black"),
    Green("#0BFA0B", "black"),
    Blue("#58A7F5", "black"),
    Purple("#C080C0", "black"),
    Pink("#FFE0E5", "black"),
    Red("#FF8080", "black");

    // カラーコードを格納するフィールド
    private final String hexCode;
    // テキストカラーを格納するフィールド
    private final String textColor;

    // コンストラクタでカラーコードとテキストカラーを受け取り、フィールドに格納
    TagColor(String hexCode, String textColor) {
        this.hexCode = hexCode;
        this.textColor = textColor;
    }

}
