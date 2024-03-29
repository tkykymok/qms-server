package com.qms.mainservice.presentation.controller;

import com.qms.mainservice.application.usecase.reservation.*;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.mainservice.presentation.presenter.ReservationPresenter;
import com.qms.mainservice.presentation.web.request.reservation.UpdateReservationStatusRequest;
import com.qms.mainservice.presentation.web.response.reservation.GetLastWaitingInfoResponse;
import com.qms.mainservice.presentation.web.response.reservation.GetReservationDetailResponse;
import com.qms.mainservice.presentation.web.response.reservation.GetReservationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final FetchReservationsUsecase fetchReservationsUsecase;
    private final FetchLastWaitTimeUsecase fetchLastWaitTimeUsecase;
    private final FetchReservationDetailUsecase fetchReservationDetailUsecase;
    private final UpdateReservationStatusUsecase updateReservationStatusUsecase;

    private final ReservationPresenter presenter;

    // 予約一覧を取得する(店舗)
    @GetMapping("/{storeId}")
    public ResponseEntity<GetReservationsResponse> getReservations(
            @PathVariable("storeId") Long storeId,
            @RequestParam(value = "date", required = false) String date) {
//        // ログインユーザー情報を取得
//        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getPrincipal();
//        // 店舗IDの検証
//        userDetails.validateStoreId(storeId);

        // 予約日
        ReservedDate reservedDate = date == null ? null : ReservedDate.of(LocalDate.parse(date));
        // inputクラスに変換
        var input = FetchReservationsInput.builder()
                .storeId(StoreId.of(1L))
                .reservedDate(reservedDate)
                .build();

        // 予約一覧を取得する
        var output = fetchReservationsUsecase.execute(input);

        return presenter.present(output);
    }

    // 予約の最後尾の待ち時間を取得する(店舗 & 顧客)
    @GetMapping("/{storeId}/last-waiting-info")
    public ResponseEntity<GetLastWaitingInfoResponse> getLastWaitingInfo(@PathVariable("storeId") Long storeId) {
        // 予約の最後尾の待ち時間を取得する
        var output = fetchLastWaitTimeUsecase.execute(StoreId.of(storeId));

        return presenter.present(output);
    }

    // 顧客に紐づく予約詳細を取得する(顧客)
    @GetMapping("/detail")
    public ResponseEntity<GetReservationDetailResponse> getReservationDetail() {
        // 顧客ID
        CustomerId customerId = CustomerId.of(1L);
        // 予約詳細を取得する
        var output = fetchReservationDetailUsecase.execute(customerId);

        return presenter.present(output);
    }

    // 予約ステータスを更新する(店舗)
    @PutMapping("/{reservationId}/update-status")
    public ResponseEntity<?> updateReservationStatus(@PathVariable("reservationId") Long reservationId,
                                                     @RequestBody UpdateReservationStatusRequest request) {
        // 店舗ID TODO tokenから取得する想定
        StoreId storeId = StoreId.of(1L);

        // リクエストをinputクラスに変換
        var input = request.toInput(storeId, ReservationId.of(reservationId));

        // 予約ステータスを更新する
        updateReservationStatusUsecase.execute(input);

        // 案内済みの場合、案内済みメッセージを返却
        String message = null;
        if (input.status().equals(ReservationStatus.DONE)) {
            message = "お会計が完了しました。";
        }

        return presenter.presentSuccess(message);
    }

    // 予約をキャンセルする
    @PutMapping("/cancel")
    public ResponseEntity<?> cancelReservation() {
        // 予約をキャンセルする

        return null;
    }


}
