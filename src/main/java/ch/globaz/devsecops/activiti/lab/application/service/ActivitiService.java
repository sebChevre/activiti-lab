package ch.globaz.devsecops.activiti.lab.application.service;

import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.task.model.Task;

import java.util.List;

public interface ActivitiService {
    ProcessInstance creerDemandeProcess();

    Page<Task> getTasks();

    Page<ProcessInstance> getProcessInstances();

    Task creeDemandeTache(String idTache);

    List<ProcessDefinition> getProcessDefinitions();
}
