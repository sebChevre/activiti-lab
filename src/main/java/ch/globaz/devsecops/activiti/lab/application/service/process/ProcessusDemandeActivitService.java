package ch.globaz.devsecops.activiti.lab.application.service.process;

import ch.globaz.devsecops.activiti.lab.application.api.web.process.demandeProcess.dto.*;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.task.model.Task;

import java.util.List;

public interface ProcessusDemandeActivitService {
    ProcessInstance creerDemandeProcess(StartDemandeProcessCommand startDemandeProcessDto);

    Task creerDemandeTacheDemandeProcess(CreerDemandeTacheCommand creerTacheDemandeProcessCommand);

    Task preparerDemandeTacheDemandeProcess(PreparerDemandeTacheCommand creerTacheDemandeProcessCommand);

    Task validerDemandeTacheDemandeProcess(ValiderDemandeTacheCommand creerTacheDemandeProcessCommand);

    Page<Task> getTacheByAssigne(String user);

    Page<Task> getTacheByGroup(String group);

    List<ProcessDefinition> getProcessDefinitions();

    Page<Task> getTasksByProcessInstance(String processId);

    Page<Task> getTasks();

    Page<ProcessInstance> getProcessInstances();

    TacheQueryWrapper creerDemandeQuery(String idProcess);
}
