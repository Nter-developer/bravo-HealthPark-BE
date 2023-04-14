package com.kgu.bravoHealthPark.domain.alarm.service;

import com.kgu.bravoHealthPark.domain.alarm.domain.Alarm;
import com.kgu.bravoHealthPark.domain.alarm.domain.AlarmStatus;
import com.kgu.bravoHealthPark.domain.alarm.dto.AlarmForm;
import com.kgu.bravoHealthPark.domain.alarm.repository.AlarmRepository;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;

    /**
     * 생성
     */
    public Alarm save(Alarm alarm) {
        alarmRepository.save(alarm);
        return alarm;
    }

    /**
     * 삭제
     */
    public void deleteAlarm(Alarm alarm) {
        alarmRepository.delete(alarm);
    }

    /**
     * 수정
     */
    //폼을 이용해서 제목, 시간, 시작일, 종료일 변경
    public void updateAlarm(Alarm alarm, AlarmForm form) {
        alarm.updateAlarm(form.getTitle(), form.getTime(), form.getStartDate(), form.getEndDate());
    }

    //알람 확인 후 복용으로 상태 변경
    public void changeAlarmDose(Alarm alarm) {
        alarm.changeAlarmStatus(AlarmStatus.DOSE);
    }

    //알람 확인 후 복용하지 않음으로 상태 변경
    public void changeAlarmNotDose(Alarm alarm) {
        alarm.changeAlarmStatus(AlarmStatus.NOT_DOSE);
    }

    /**
     * 검색
     */
    public Alarm findAlarmById(Long alarmId) {
        Alarm findAlarm = alarmRepository.findByAlarmId(alarmId);
        return findAlarm;
    }

    public List<Alarm> findAlarmByTitle(String title) {
        List<Alarm> result = alarmRepository.findByTitle(title);
        return result;
    }

    public List<Alarm> findAlarmAll() {
        List<Alarm> result = alarmRepository.findAll();
        return result;
    }

    public List<Alarm> findAlarmByStatus(AlarmStatus alarmStatus) {
        List<Alarm> result = alarmRepository.findByAlarmStatusIs(alarmStatus);
        return result;
    }

    public List<Alarm> findAlarmByUser(Long userId) {
        List<Alarm> result = alarmRepository.findByMedicationInfo_User_UserId(userId);
        return result;
    }

    public List<Alarm> findAlarmByUserAndStatus(Long userId, AlarmStatus alarmStatus) {
        List<Alarm> result = alarmRepository.findByMedicationInfo_User_UserIdAndAlarmStatus(userId, alarmStatus);
        return result;
    }

    /**
     * 검증
     */

    public boolean checkLoginId(Alarm alarm, User loginUser) {
        return loginUser.getUserId().equals(alarm.getMedicationInfo().getUser().getUserId());
    }
}
