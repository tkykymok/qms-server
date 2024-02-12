package com.qms.mainservice.domain.model.valueobject;

import lombok.Getter;

@Getter
public enum TagColor {
    WHITE("#FFFFFF"),
    GRAY("#DFDFDF"),
    BROWN("#E8C9C9"),
    ORANGE("#FFE8BF"),
    YELLOW("#FFFFBF"),
    GREEN("#85FC85"),
    BLUE("#ABD3FA"),
    PURPLE("#DFBFDF"),
    PINK("#FFEFF2"),
    RED("#FFBFBF");

    // カラーコードを格納するフィールド
    private final String hexCode;

    // コンストラクタでカラーコードとテキストカラーを受け取り、フィールドに格納
    TagColor(String hexCode) {
        this.hexCode = hexCode;
    }

    public static TagColor fromValue(String value) {
        for (TagColor tagColor : TagColor.values()) {
            if (tagColor.hexCode.equals(value)) {
                return tagColor;
            }
        }
        // 一致するものがない場合はデフォルトカラーを返す
        return defaultColor();
    }

    public static TagColor defaultColor() {
        return TagColor.WHITE;
    }

}
