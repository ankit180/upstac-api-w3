package org.upgrad.upstac.testrequests.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.users.User;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Validated
public class LabResultService {


    @Autowired
    private LabResultRepository labResultRepository;


    private static Logger logger = LoggerFactory.getLogger(LabResultService.class);



    private LabResult createLabResult(User tester, TestRequest testRequest) {

        LabResult labResult = new LabResult();
        labResult.setTester(tester);
        labResult.setRequest(testRequest);
        return saveLabResult(labResult);
    }

    @Transactional
    LabResult saveLabResult(LabResult labResult) {
        return labResultRepository.save(labResult);
    }



    public LabResult assignForLabTest(TestRequest testRequest, User tester) {

        return createLabResult(tester, testRequest);


    }


    public LabResult updateLabTest(TestRequest testRequest, CreateLabResult createLabResult) {


        LabResult result = labResultRepository.findByRequest(testRequest).map(labResult -> {
            labResult.setBloodPressure(createLabResult.getBloodPressure());

            labResult.setHeartBeat(createLabResult.getHeartBeat());
            labResult.setTemperature(createLabResult.getTemperature());
            labResult.setOxygenLevel(createLabResult.getOxygenLevel());
            labResult.setComments(createLabResult.getComments());
            labResult.setResult(createLabResult.getResult());
            labResult.setUpdatedOn(LocalDate.now());
            return  labResult;
        }).orElseThrow(() -> new AppException("No data found") );

        return saveLabResult(result);

    }


}
