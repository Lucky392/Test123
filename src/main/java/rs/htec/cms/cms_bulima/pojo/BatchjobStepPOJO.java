/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;


import java.util.ArrayList;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.BatchjobStep;

/**
 *
 * @author marko
 */
public class BatchjobStepPOJO {
    
    private Long id;
    private String stepName;
    private Short enabled;
    private Long idBatchjob;
    private String jobName;

    public BatchjobStepPOJO(BatchjobStep batchjobStep) {
        this.id = batchjobStep.getId();
        this.stepName = batchjobStep.getStepName();
        this.enabled = batchjobStep.getEnabled();
        if (batchjobStep.getIdBatchjob() != null){
            this.idBatchjob = batchjobStep.getIdBatchjob().getId();
            this.jobName = batchjobStep.getIdBatchjob().getJobName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Short getEnabled() {
        return enabled;
    }

    public void setEnabled(Short enabled) {
        this.enabled = enabled;
    }

    public Long getIdBatchjob() {
        return idBatchjob;
    }

    public void setIdBatchjob(Long idBatchjob) {
        this.idBatchjob = idBatchjob;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    public static List<BatchjobStepPOJO> toBatchjobStepPOJOList(List<BatchjobStep> steps){
        BatchjobStepPOJO pojo;
        List<BatchjobStepPOJO> pojos = new ArrayList<>();
        for (BatchjobStep step : steps) {
            pojo = new BatchjobStepPOJO(step);
            pojos.add(pojo);
        }
        return pojos;
    }
}
