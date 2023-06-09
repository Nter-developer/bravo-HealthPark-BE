package com.kgu.bravoHealthPark.domain.medicationInfo.dto;

import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.state.domain.State;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicationInfoDto {
    private Long medicationInfoId;
    private State state;
    private String itemName;
    private int tablet; //몇 정
    private int days;
    private LocalDate startDate;
    private LocalDate endDate;
    private String memo;
    private int times;

    public MedicationInfoDto(MedicationInfo medicationInfo) {
        this.medicationInfoId=medicationInfo.getMedInfoId();
        this.state = medicationInfo.getState();
        this.itemName = medicationInfo.getItemName();
        this.tablet = medicationInfo.getTablet();
        this.days = medicationInfo.getDays();
        this.startDate = medicationInfo.getStartDate();
        this.endDate = medicationInfo.getEndDate();
        this.memo = medicationInfo.getMemo();
        this.times=medicationInfo.getTimes();
    }
}
