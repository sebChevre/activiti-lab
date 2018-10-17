package ch.globaz.devsecops.activiti.lab.application.service.impl;


import ch.globaz.devsecops.activiti.lab.application.service.ActivitiService;
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
public class ActivitiServiceImpl implements ActivitiService{

    private ActivitiRepository activitiRepository;

    @Autowired
    public ActivitiServiceImpl(ActivitiImplRepository activitiRepository){
        this.activitiRepository = activitiRepository;
    }

    @Override
    public ProcessInstance creerDemandeProcess(){
        return activitiRepository.createDemandeProcessInstance();
    }

    @Override
    public Page<Task> getTasks(){
        return activitiRepository.getTasks();
    }


    @Override
    public Page<ProcessInstance> getProcessInstances(){
        return activitiRepository.getProcessInstances();
    }


    @Override
    public Task creeDemandeTache(String idTache){
        return activitiRepository.creerDemandeTache(idTache);
    }

    @Override
    public List<ProcessDefinition> getProcessDefinitions() {
        return activitiRepository.getProcessDefinitions();
    }
}
