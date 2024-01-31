package com.qms.mainservice.presentation.controller;

import com.qms.mainservice.application.usecase.reservation.*;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.mainservice.infrastructure.config.ApplicationProperties;
import com.qms.mainservice.presentation.presenter.ReservationPresenter;
import com.qms.mainservice.presentation.web.request.reservation.UpdateReservationRequest;
import com.qms.mainservice.presentation.web.response.reservation.GetLastWaitingInfoResponse;
import com.qms.mainservice.presentation.web.response.reservation.GetReservationDetailResponse;
import com.qms.mainservice.presentation.web.response.reservation.GetReservationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final FetchReservationsUsecase fetchReservationsUsecase;
    private final FetchLastWaitTimeUsecase fetchLastWaitTimeUsecase;
    private final FetchReservationDetailUsecase fetchReservationDetailUsecase;
    private final UpdateReservationStatusUsecase updateReservationStatusUsecase;

    private final ReservationPresenter presenter;

    // 予約一覧を取得する(店舗)
    @GetMapping("/list/today")
    public ResponseEntity<GetReservationsResponse> getTodayReservations() {
        // 店舗ID
        StoreId storeId = StoreId.of(1L);
        // 予約一覧を取得する
        FetchReservationsOutput output = fetchReservationsUsecase.execute(storeId);

        return presenter.present(output);
    }

    // 予約の最後尾の待ち時間を取得する(店舗 & 顧客)
    @GetMapping("/last-waiting-info/{storeId}")
    public ResponseEntity<GetLastWaitingInfoResponse> getLastWaitingInfo(@PathVariable("storeId") Long storeId) {
        // 予約の最後尾の待ち時間を取得する
        FetchLastWaitTimeOutput output = fetchLastWaitTimeUsecase.execute(StoreId.of(storeId));

        return presenter.present(output);
    }

    // 顧客に紐づく予約詳細を取得する(顧客)
    @GetMapping("/detail")
    public ResponseEntity<GetReservationDetailResponse> getReservationDetail() {
        // 顧客ID
        CustomerId customerId = CustomerId.of(1L);
        // 予約詳細を取得する
        FetchReservationDetailOutput output =
                fetchReservationDetailUsecase.execute(customerId);

        return presenter.present(output);
    }

    // 予約ステータスを更新する(店舗)
    @PutMapping("/update-status")
    public ResponseEntity<?> updateReservationStatus(@RequestBody UpdateReservationRequest request) {
        // ログインユーザーの店舗IDを取得する TODO トークンから取得する想定
        Long storeId = 1L;
        // リクエストの店舗IDとログインユーザーの店舗IDが一致するか確認する
        if (!storeId.equals(request.storeId())) {
            // 一致しない場合はエラーを返す
            return null;
        }

        UpdateReservationStatusInput input = UpdateReservationStatusInput.builder()
                .reservationId(ReservationId.of(request.reservationId()))
                .storeId(StoreId.of(request.storeId()))
                .staffId(StaffId.of(request.staffId()))
                .status(ReservationStatus.fromValue(request.status()))
                .build();

        // 予約ステータスを更新する
        updateReservationStatusUsecase.execute(input);

        return null;
    }

    // 予約をキャンセルする
    @PutMapping("/cancel")
    public ResponseEntity<?> cancelReservation() {
        // 予約をキャンセルする

        return null;
    }



}
