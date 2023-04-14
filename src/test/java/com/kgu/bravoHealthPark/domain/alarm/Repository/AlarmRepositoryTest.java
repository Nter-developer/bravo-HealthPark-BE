package com.kgu.bravoHealthPark.domain.alarm.Repository;

import com.kgu.bravoHealthPark.domain.alarm.domain.Alarm;
import com.kgu.bravoHealthPark.domain.alarm.domain.AlarmStatus;
import com.kgu.bravoHealthPark.domain.alarm.repository.AlarmRepository;
import com.kgu.bravoHealthPark.domain.alarm.service.AlarmService;
import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.medicationInfo.dto.MedicationInfoForm;
import com.kgu.bravoHealthPark.domain.medicationInfo.repository.MedicationInfoRepository;
import com.kgu.bravoHealthPark.domain.medicationInfo.service.MedicationInfoService;
import com.kgu.bravoHealthPark.domain.type.domain.Type;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import com.kgu.bravoHealthPark.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AlarmRepositoryTest {

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private MedicationInfoRepository medicationInfoRepository;

    @Autowired
    private MedicationInfoService medicationInfoService;

    @Autowired
    private UserRepository userRepository;

    //알람 생성 테스트
    @Test
    public void createAlarmTest() {
        //given
        User user = User.builder()
                .phoneNumber("010-1234-5678")
                .name("홍길동")
                .build();

        User save = userRepository.save(user);

        MedicationInfoForm medicationInfoForm = new MedicationInfoForm();
        medicationInfoForm.setEnptName("test");
        medicationInfoForm.setDays(10);
        medicationInfoForm.setItemName("test");
        medicationInfoForm.setMemo("약");
        medicationInfoForm.setTablet(1);
        medicationInfoForm.setStartDate(LocalDate.now());
        medicationInfoForm.setEndDate(LocalDate.now());

        MedicationInfo medicationInfo = new MedicationInfo(save, medicationInfoForm, Type.VitaminC);
        medicationInfo.firstState();
        medicationInfoRepository.save(medicationInfo);

        //when
        Alarm alarm = new Alarm(medicationInfo, "알람", LocalTime.of(12, 0));
        alarm.initStatus();
        Alarm saveAlarm = alarmRepository.save(alarm);
        //then
        assertThat(alarm.getTitle()).isEqualTo(saveAlarm.getTitle());
    }

    //알람 삭제 테스트
    @Test
    public void deleteAlarmTest() {
        //given
        User user = User.builder()
                .phoneNumber("010-1234-5678")
                .name("홍길동")
                .build();

        User save = userRepository.save(user);

        MedicationInfoForm medicationInfoForm = new MedicationInfoForm();
        medicationInfoForm.setEnptName("test");
        medicationInfoForm.setDays(10);
        medicationInfoForm.setItemName("test");
        medicationInfoForm.setMemo("약");
        medicationInfoForm.setTablet(1);
        medicationInfoForm.setStartDate(LocalDate.now());
        medicationInfoForm.setEndDate(LocalDate.now());

        MedicationInfo medicationInfo = new MedicationInfo(save, medicationInfoForm, Type.VitaminC);
        medicationInfoService.startState(medicationInfo);
        medicationInfoRepository.save(medicationInfo);

        Alarm alarm = new Alarm(medicationInfo, "알람", LocalTime.of(12, 0));
        Alarm saveAlarm = alarmRepository.save(alarm);

        //when
        alarmRepository.delete(saveAlarm);

        //then
        assertThat(alarmRepository.findByAlarmId(saveAlarm.getAlarmId())).isNull();
    }

    @Test
    public void findAllAlarmTest() {
        //given
        User user1 = User.builder()
                .phoneNumber("010-1234-5678")
                .name("홍길동")
                .build();

        User user2 = User.builder()
                .phoneNumber("010-1234-5678")
                .name("김길동")
                .build();

        User save1 = userRepository.save(user1);
        User save2 = userRepository.save(user2);

        MedicationInfoForm medicationInfoForm = new MedicationInfoForm();
        medicationInfoForm.setEnptName("test");
        medicationInfoForm.setDays(10);
        medicationInfoForm.setItemName("test");
        medicationInfoForm.setMemo("약");
        medicationInfoForm.setTablet(1);
        medicationInfoForm.setStartDate(LocalDate.now());
        medicationInfoForm.setEndDate(LocalDate.now());

        MedicationInfo medicationInfo1 = new MedicationInfo(save1, medicationInfoForm, Type.VitaminC);
        medicationInfoService.startState(medicationInfo1);
        medicationInfoRepository.save(medicationInfo1);

        MedicationInfo medicationInfo2 = new MedicationInfo(save2, medicationInfoForm, Type.VitaminC);
        medicationInfoService.startState(medicationInfo2);
        medicationInfoRepository.save(medicationInfo2);

        Alarm alarm1 = new Alarm(medicationInfo1, "알람1", LocalTime.of(12, 0));
        alarm1.initStatus();
        alarmRepository.save(alarm1);
        Alarm alarm2 = new Alarm(medicationInfo2, "알람2", LocalTime.of(12, 0));
        alarm2.initStatus();
        alarmRepository.save(alarm2);

        //when
        List<Alarm> all = alarmRepository.findAll();

        //then All 조회 테스트
        assertThat(all.size()).isEqualTo(2);

    }

    @Test
    public void findAlarmByTitleTest() {
        //given
        User user1 = User.builder()
                .phoneNumber("010-1234-5678")
                .name("홍길동")
                .build();

        User user2 = User.builder()
                .phoneNumber("010-1234-5678")
                .name("김길동")
                .build();

        User save1 = userRepository.save(user1);
        User save2 = userRepository.save(user2);

        MedicationInfoForm medicationInfoForm = new MedicationInfoForm();
        medicationInfoForm.setEnptName("test");
        medicationInfoForm.setDays(10);
        medicationInfoForm.setItemName("test");
        medicationInfoForm.setMemo("약");
        medicationInfoForm.setTablet(1);
        medicationInfoForm.setStartDate(LocalDate.now());
        medicationInfoForm.setEndDate(LocalDate.now());

        MedicationInfo medicationInfo1 = new MedicationInfo(save1, medicationInfoForm, Type.VitaminC);
        medicationInfoService.startState(medicationInfo1);
        medicationInfoRepository.save(medicationInfo1);

        MedicationInfo medicationInfo2 = new MedicationInfo(save2, medicationInfoForm, Type.VitaminC);
        medicationInfoService.startState(medicationInfo2);
        medicationInfoRepository.save(medicationInfo2);

        Alarm alarm1 = new Alarm(medicationInfo1, "알람1", LocalTime.of(12, 0));
        alarm1.initStatus();
        alarmRepository.save(alarm1);
        Alarm alarm2 = new Alarm(medicationInfo2, "알람2", LocalTime.of(12, 0));
        alarm2.initStatus();
        alarmRepository.save(alarm2);

        //when
        List<Alarm> alarmByTitle1 = alarmRepository.findByTitle("알람1");
        List<Alarm> alarmByTitle2 = alarmRepository.findByTitle("알람2");

        //then All 조회 테스트
        assertThat(alarmByTitle1.get(0).getTitle()).isEqualTo("알람1");
        assertThat(alarmByTitle2.get(0).getTitle()).isEqualTo("알람2");

    }

    @Test
    public void findAlarmByStatusTest() {
        //given
        User user1 = User.builder()
                .phoneNumber("010-1234-5678")
                .name("홍길동")
                .build();

        User user2 = User.builder()
                .phoneNumber("010-1234-5678")
                .name("김길동")
                .build();

        User save1 = userRepository.save(user1);
        User save2 = userRepository.save(user2);

        MedicationInfoForm medicationInfoForm = new MedicationInfoForm();
        medicationInfoForm.setEnptName("test");
        medicationInfoForm.setDays(10);
        medicationInfoForm.setItemName("test");
        medicationInfoForm.setMemo("약");
        medicationInfoForm.setTablet(1);
        medicationInfoForm.setStartDate(LocalDate.now());
        medicationInfoForm.setEndDate(LocalDate.now());

        MedicationInfo medicationInfo1 = new MedicationInfo(save1, medicationInfoForm, Type.VitaminC);
        medicationInfoService.startState(medicationInfo1);
        medicationInfoRepository.save(medicationInfo1);

        MedicationInfo medicationInfo2 = new MedicationInfo(save2, medicationInfoForm, Type.VitaminC);
        medicationInfoService.startState(medicationInfo2);
        medicationInfoRepository.save(medicationInfo2);

        Alarm alarm1 = new Alarm(medicationInfo1, "알람1", LocalTime.of(12, 0));
        alarm1.initStatus();
        alarmRepository.save(alarm1);
        Alarm alarm2 = new Alarm(medicationInfo2, "알람2", LocalTime.of(12, 0));
        alarm2.initStatus();
        alarmRepository.save(alarm2);

        //when
        List<Alarm> alarmStatusIs = alarmRepository.findByAlarmStatusIs(AlarmStatus.NOT_CONFIRMED);

        //then All 조회 테스트
        assertThat(alarmStatusIs.size()).isEqualTo(2);
        assertThat(alarmStatusIs.get(0).getAlarmStatus()).isEqualTo(AlarmStatus.NOT_CONFIRMED);
    }

    @Test
    public void findAlarmByUserTest() {
        //given
        User user1 = User.builder()
                .phoneNumber("010-1234-5678")
                .name("홍길동")
                .build();

        User user2 = User.builder()
                .phoneNumber("010-1234-5678")
                .name("김길동")
                .build();

        User save1 = userRepository.save(user1);
        User save2 = userRepository.save(user2);

        MedicationInfoForm medicationInfoForm = new MedicationInfoForm();
        medicationInfoForm.setEnptName("test");
        medicationInfoForm.setDays(10);
        medicationInfoForm.setItemName("test");
        medicationInfoForm.setMemo("약");
        medicationInfoForm.setTablet(1);
        medicationInfoForm.setStartDate(LocalDate.now());
        medicationInfoForm.setEndDate(LocalDate.now());

        MedicationInfo medicationInfo1 = new MedicationInfo(save1, medicationInfoForm, Type.VitaminC);
        medicationInfoService.startState(medicationInfo1);
        medicationInfoRepository.save(medicationInfo1);

        MedicationInfo medicationInfo2 = new MedicationInfo(save2, medicationInfoForm, Type.VitaminC);
        medicationInfoService.startState(medicationInfo2);
        medicationInfoRepository.save(medicationInfo2);

        Alarm alarm1 = new Alarm(medicationInfo1, "알람1", LocalTime.of(12, 0));
        alarm1.initStatus();
        alarmRepository.save(alarm1);
        Alarm alarm2 = new Alarm(medicationInfo2, "알람2", LocalTime.of(12, 0));
        alarm2.initStatus();
        alarmRepository.save(alarm2);

        //when
        List<Alarm> alarmByUser1 = alarmRepository.findByMedicationInfo_User_UserId(save1.getUserId());
        List<Alarm> alarmByUser2 = alarmRepository.findByMedicationInfo_User_UserId(save2.getUserId());

        //then All 조회 테스트
        assertThat(alarmByUser1.size()).isEqualTo(1);
        assertThat(alarmByUser2.size()).isEqualTo(1);
        assertThat(alarmByUser1.get(0).getMedicationInfo().getUser().getName()).isEqualTo("홍길동");
        assertThat(alarmByUser2.get(0).getMedicationInfo().getUser().getName()).isEqualTo("김길동");
    }

    @Test
    public void findAlarmByUserAndStatusTest() {
        //given
        User user1 = User.builder()
                .phoneNumber("010-1234-5678")
                .name("홍길동")
                .build();

        User user2 = User.builder()
                .phoneNumber("010-1234-5678")
                .name("김길동")
                .build();

        User save1 = userRepository.save(user1);
        User save2 = userRepository.save(user2);

        MedicationInfoForm medicationInfoForm = new MedicationInfoForm();
        medicationInfoForm.setEnptName("test");
        medicationInfoForm.setDays(10);
        medicationInfoForm.setItemName("test");
        medicationInfoForm.setMemo("약");
        medicationInfoForm.setTablet(1);
        medicationInfoForm.setStartDate(LocalDate.now());
        medicationInfoForm.setEndDate(LocalDate.now());

        MedicationInfo medicationInfo1 = new MedicationInfo(save1, medicationInfoForm, Type.VitaminC);
        medicationInfoService.startState(medicationInfo1);
        medicationInfoRepository.save(medicationInfo1);

        MedicationInfo medicationInfo2 = new MedicationInfo(save2, medicationInfoForm, Type.VitaminC);
        medicationInfoService.startState(medicationInfo2);
        medicationInfoRepository.save(medicationInfo2);

        Alarm alarm1 = new Alarm(medicationInfo1, "알람1", LocalTime.of(12, 0));
        alarm1.initStatus();
        alarmRepository.save(alarm1);
        Alarm alarm2 = new Alarm(medicationInfo2, "알람2", LocalTime.of(12, 0));
        alarm2.initStatus();
        alarmRepository.save(alarm2);

        //when

        List<Alarm> alarmByUserAndStatus1 = alarmRepository.findByMedicationInfo_User_UserIdAndAlarmStatus(save1.getUserId(), AlarmStatus.NOT_CONFIRMED);
        List<Alarm> alarmByUserAndStatus2 = alarmRepository.findByMedicationInfo_User_UserIdAndAlarmStatus(save2.getUserId(), AlarmStatus.NOT_CONFIRMED);

        //then

        //user와 status로 조회 테스트
        assertThat(alarmByUserAndStatus1.get(0).getAlarmStatus()).isEqualTo(AlarmStatus.NOT_CONFIRMED);
        assertThat(alarmByUserAndStatus2.get(0).getMedicationInfo().getUser().getUserId()).isEqualTo(save2.getUserId());
    }

}