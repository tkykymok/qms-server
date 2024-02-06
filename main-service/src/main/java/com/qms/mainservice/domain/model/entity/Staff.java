package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.SingleKeyBaseEntity;
import com.qms.shared.domain.model.valueobject.TrackingInfo;
import lombok.Getter;

@Getter
public class Staff extends SingleKeyBaseEntity<StaffId> {

    private CompanyId companyId; // 企業ID
    private LastName lastName; // 姓
    private FirstName firstName; // 名
    private ImageUrl imageUrl; // 画像URL
    private CognitoUserId cognitoUserId; // CognitoユーザーID

    private Staff() {
    }

    // DBから取得したデータをドメインオブジェクトに変換する
    public static Staff reconstruct(
            StaffId staffId,
            CompanyId companyId,
            LastName lastName,
            FirstName firstName,
            ImageUrl imageUrl,
            CognitoUserId cognitoUserId,
            TrackingInfo trackingInfo
    ) {
        Staff staff =  new Staff();
        staff.id = staffId;
        staff.companyId = companyId;
        staff.lastName = lastName;
        staff.firstName = firstName;
        staff.imageUrl = imageUrl;
        staff.cognitoUserId = cognitoUserId;
        staff.trackingInfo = trackingInfo;
        return staff;
    }
}
