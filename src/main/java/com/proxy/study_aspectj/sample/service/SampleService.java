package com.proxy.study_aspectj.sample.service;

import com.proxy.study_aspectj.sample.dto.SampleDto;

import java.util.List;
import java.util.Map;

public interface SampleService {

    public List<SampleDto> getAllSample();

    public void getError();

    public Map getPrivateInfo();
}
