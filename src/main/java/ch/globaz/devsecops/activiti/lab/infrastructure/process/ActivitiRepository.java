package ch.globaz.devsecops.activiti.lab.infrastructure.process;

import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.task.model.Task;

import java.util.List;


public interface ActivitiRepository {

    ProcessInstance createDemandeProcessInstance();

    Page<Task> getTasks();

    Page<ProcessInstance> getProcessInstances();

    Task creerDemandeTache(String idTache);

    List<ProcessDefinition> getProcessDefinitions();
}
