package com.example.demo.DTO.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class BookingRequestDTO {

    @NotNull(message = "Vui lòng cung cấp ID Khách hàng")
    private Long guestId;

    @NotNull(message = "Vui lòng cung cấp ID Phòng")
    private Long roomId;

    @NotNull(message = "Ngày nhận phòng không được để trống")
    @FutureOrPresent(message = "Ngày nhận phòng không được nằm trong quá khứ")
    private LocalDate checkInDate;

    @NotNull(message = "Ngày trả phòng không được để trống")
    @Future(message = "Ngày trả phòng phải là một ngày trong tương lai")
    private LocalDate checkOutDate;

}