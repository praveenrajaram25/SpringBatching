package com.batch.processing.controller;



import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Batching")
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job studentJob;
    
    @Autowired
    private JobOperator jobOperator;
    
    @Autowired
    private JobExplorer jobExplorer;

    @GetMapping("/studentJob")
    public String runJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) 
                    .toJobParameters();

            jobLauncher.run(studentJob, params);
            return "Student job started successfully!";
        } catch (Exception e) {
            return "Job failed: " + e.getMessage();
        }
    }
    
 // 2. Stop a job
    @GetMapping("/stop")
    public String stopJob(@RequestParam Long executionId) throws Exception {
        boolean stopped = jobOperator.stop(executionId);
        return stopped ? "Job stopped successfully." : "Could not stop job.";
    }

    // 3. Resume a job
    @GetMapping("/resume")
    public String resumeJob(@RequestParam Long executionId) throws Exception {
        Long newExecutionId = jobOperator.restart(executionId);
        return "Job resumed. New ExecutionId: " + newExecutionId;
    }

    // 4. Get job status
    @GetMapping("/status")
    public Map<String, Object> getJobStatus(@RequestParam Long executionId) throws Exception {
        JobExecution execution = jobExplorer.getJobExecution(executionId);
        Map<String, Object> status = new HashMap<>();
        status.put("ExecutionId", execution.getId());
        status.put("JobName", execution.getJobInstance().getJobName());
        status.put("Status", execution.getStatus());
        status.put("StartTime", execution.getStartTime());
        status.put("EndTime", execution.getEndTime());
        return status;
    }
}
