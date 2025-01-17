package com.proxy.study_aspectj.sample.controller;

import com.proxy.study_aspectj.sample.service.SampleService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SampleController {

     private final SampleService sampleService;

    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    /**
     *  모든 샘플데이터 가져오기
     */
    @GetMapping("/samples")
    public ResponseEntity<?> getAllSample() {
        return ResponseEntity.status(HttpStatus.OK).body(sampleService.getAllSample());
    }

    /**
     * 의도적으로 Exception throw 하기
     */
    @GetMapping("/error")
    public void getError() {
        sampleService.getError();
    }


    /**
     * request header 의 Authrization 이 "pass" 일 때만 데이터 반환 아닐 시 Exceptio throw
     */
    @GetMapping("/getPrivateInfo")
    public ResponseEntity<?> getPrivateInfo(HttpServletRequest request) {
        return ResponseEntity.ok().body(sampleService.getPrivateInfo());
    }
}
