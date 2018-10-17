package ch.globaz.devsecops.activiti.lab.application.service.process.impl;


import ch.globaz.devsecops.activiti.lab.application.api.web.process.demandeProcess.dto.*;
import ch.globaz.devsecops.activiti.lab.application.service.process.ProcessusDemandeActivitService;
import ch.globaz.devsecops.activiti.lab.infrastructure.process.ActivitiRepository;
import ch.globaz.devsecops.activiti.lab.infrastructure.process.impl.ActivitiImplRepository;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.task.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessusDemandeActivitiServiceImpl implements ProcessusDemandeActivitService {

    private ActivitiRepository activitiRepository;

    @Autowired
    public ProcessusDemandeActivitiServiceImpl(ActivitiImplRepository activitiRepository){

        this.activitiRepository = activitiRepository;
    }

    @Override
    public TacheQueryWrapper creerDemandeQuery(String idProcess) {
        return activitiRepository.creerDemandeQuery(idProcess);
    }

    @Override
    public ProcessInstance creerDemandeProcess(StartDemandeProcessCommand startDemandeProcessDto){
        return activitiRepository.createDemandeProcessInstance(startDemandeProcessDto);
    }

    @Override
    public Task creerDemandeTacheDemandeProcess(CreerDemandeTacheCommand creerTacheDemandeProcessCommand){
        return activitiRepository.creerDemandeTache(creerTacheDemandeProcessCommand);
    }

    @Override
    public Task preparerDemandeTacheDemandeProcess(PreparerDemandeTacheCommand preparerDemandeTacheCommand) {
        return activitiRepository.preparerDemandeTache(preparerDemandeTacheCommand);
    }

    @Override
    public Task validerDemandeTacheDemandeProcess(ValiderDemandeTacheCommand validerDemandeTacheCommand) {
        return activitiRepository.validerDemandeTache(validerDemandeTacheCommand);
    }


    /**************************************************************************************/
    @Override
    public Page<Task> getTasks(){
        return activitiRepository.getTasks();
    }


    @Override
    public Page<ProcessInstance> getProcessInstances(){
        return activitiRepository.getProcessInstances();
    }




    @Override
    public Page<Task> getTacheByAssigne(String user){ return activitiRepository.getTasksByAssignee(user); }

    @Override
    public Page<Task> getTacheByGroup(String group){ return activitiRepository.getTasksByGroup(group); }


    @Override
    public List<ProcessDefinition> getProcessDefinitions() {
        return activitiRepository.getProcessDefinitions();
    }

    @Override
    public Page<Task> getTasksByProcessInstance(String processId) {
        return activitiRepository.getTasksByProcessInstance(processId);
    }
    /**************************************************************************************/
}
