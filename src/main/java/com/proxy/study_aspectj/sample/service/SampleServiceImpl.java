package com.proxy.study_aspectj.sample.service;

import com.proxy.study_aspectj.sample.dto.SampleDto;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SampleServiceImpl implements SampleService {

    @Override
    public List<SampleDto> getAllSample() {
        List<SampleDto> returnObject = new ArrayList<>();

        String title = "sample_title";
        String conent = "sample_content";


        for (int i = 0; i < 10; i++) {
            SampleDto samplteDto = new SampleDto();
            samplteDto.setTitle(title + "_" + (i+1));
            samplteDto.setContent(conent + "_" + (i+1));
            returnObject.add(samplteDto);
        }

        return returnObject;
    }

    @Override
    public void getError() {
        throw new IllegalArgumentException("INTENDED ERROR IS OCCURED");
    }

    @Override
    public Map getPrivateInfo() {
        Map importanatData = new HashMap<>();

        importanatData.put("name", "홍길동");
        importanatData.put("age", 19);
        importanatData.put("phone", "010-0000-0000");

        return importanatData;
    }
}
