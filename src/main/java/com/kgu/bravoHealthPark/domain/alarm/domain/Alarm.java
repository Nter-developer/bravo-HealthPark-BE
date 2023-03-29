package com.kgu.bravoHealthPark.domain.alarm.domain;

import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "alarm_id")
    private Long alarmId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mi_id")
    private MedicationInfo medicationInfo;

    private String title; //알람 제목

    private LocalTime time; //알람 시간

    private LocalDate startDate; //알람 시작일

    private LocalDate endDate; //알람 종료일

    @Enumerated(EnumType.STRING)
    private Checking checking; //복용 여부 확인

    public Alarm(MedicationInfo medicationInfo, String title, LocalTime time, Checking checking) {
        this.medicationInfo = medicationInfo;
        this.title = title;
        this.time = time;
        this.startDate = medicationInfo.getStartDate();
        this.endDate = medicationInfo.getEndDate();
        this.checking = checking;
    }

}