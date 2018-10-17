package ch.globaz.devsecops.activiti.lab.infrastructure.process;

import ch.globaz.devsecops.activiti.lab.application.api.web.process.demandeProcess.dto.*;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.task.model.Task;

import java.util.List;


public interface ActivitiRepository {

    ProcessInstance createDemandeProcessInstance(StartDemandeProcessCommand startDemandeProcessDto);

    Page<Task> getTasks();

    Page<Task> getTasksByProcessInstance(String processId);

    Page<ProcessInstance> getProcessInstances();

    Page<Task> getTasksByAssignee(String user);

    Page<Task> getTasksByGroup(String group);

    Task creerDemandeTache(CreerDemandeTacheCommand creerTacheDemandeProcessCommand);

    List<ProcessDefinition> getProcessDefinitions();

    Task preparerDemandeTache(PreparerDemandeTacheCommand creerTacheDemandeProcessCommand);

    Task validerDemandeTache(ValiderDemandeTacheCommand validerDemandeTacheCommand);

    TacheQueryWrapper creerDemandeQuery(String idProcess);
}
