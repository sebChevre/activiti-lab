package ch.globaz.devsecops.activiti.lab.infrastructure.process.impl;

import ch.globaz.devsecops.activiti.lab.application.api.web.process.demandeProcess.dto.*;
import ch.globaz.devsecops.activiti.lab.application.configuration.SecurityConfiguration;
import ch.globaz.devsecops.activiti.lab.domaine.Demande;
import ch.globaz.devsecops.activiti.lab.infrastructure.process.ActivitiRepository;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.ClaimTaskPayloadBuilder;
import org.activiti.api.task.model.builders.CompleteTaskPayloadBuilder;
import org.activiti.api.task.model.payloads.GetTasksPayload;
import org.activiti.api.task.runtime.TaskRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ActivitiImplRepository implements ActivitiRepository {

    private ProcessRuntime processRuntime;

    private TaskRuntime taskRuntime;

    @Autowired
    public ActivitiImplRepository(ProcessRuntime processRuntime,TaskRuntime taskRuntime){
        this.processRuntime = processRuntime;
        this.taskRuntime = taskRuntime;
    }

    @Override
    public ProcessInstance createDemandeProcessInstance(StartDemandeProcessCommand startDemandeProcessDto) {


        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("demandeProcess")
                .withVariable("userId",startDemandeProcessDto.getDefaultUserAssignee())
                .build());

        return processInstance;
    }

    @Override
    public Page<Task> getTasks() {
        return taskRuntime.tasks(Pageable.of(0, 10));
    }

    @Override
    public Page<Task> getTasksByProcessInstance(String processId) {

        GetTasksPayload getTasksPayload = new GetTasksPayload();
        getTasksPayload.setProcessInstanceId(processId);

        return taskRuntime.tasks(Pageable.of(0, 10),getTasksPayload);
    }

    @Override
    public Page<ProcessInstance> getProcessInstances() {
        Page<ProcessInstance> processDefinitionPage = processRuntime.processInstances(Pageable.of(0, 10));

        return processDefinitionPage;
    }

    @Override
    public Page<Task> getTasksByAssignee(String user) {

        GetTasksPayload getTasksPayload = new GetTasksPayload();
        getTasksPayload.setAssigneeId(user);

        return taskRuntime.tasks(Pageable.of(0, 10),getTasksPayload );

    }

    @Override
    public Page<Task> getTasksByGroup(String group) {

        GetTasksPayload getTasksPayload = new GetTasksPayload();
        getTasksPayload.setGroups(Arrays.asList(group));

        return taskRuntime.tasks(Pageable.of(0, 10),getTasksPayload );

    }

    @Override
    public TacheQueryWrapper creerDemandeQuery(String idProcess) {
        //payload pour récup tâche avec idProcess
        GetTasksPayload getTasksPayload = new GetTasksPayload();
        getTasksPayload.setProcessInstanceId(idProcess);

        Task task = taskRuntime.tasks(Pageable.of(0,1),getTasksPayload).getContent().stream().findFirst().get();

        Map<String, Object> data = new HashMap<>();
        data.put("demande",new Demande("demande test"));
        return new TacheQueryWrapper(task,data);
    }

    @Override
    public Task creerDemandeTache(CreerDemandeTacheCommand creerTacheDemandeProcessCommand) {

        //payload pour récup tâche avec idProcess
        GetTasksPayload getTasksPayload = new GetTasksPayload();
        getTasksPayload.setProcessInstanceId(creerTacheDemandeProcessCommand.getIdProcess());

        Task task = taskRuntime.tasks(Pageable.of(0,1),getTasksPayload).getContent().stream().findFirst().get();
        log.info("Tache récupéré: {}",task);

        if(task.getAssignee().isEmpty()){
            ClaimTaskPayloadBuilder claimTaskPayloadBuilder = new ClaimTaskPayloadBuilder();
            claimTaskPayloadBuilder.withTaskId(task.getId());
            claimTaskPayloadBuilder.withAssignee(creerTacheDemandeProcessCommand.getUserAssignee());

            task = taskRuntime.claim(claimTaskPayloadBuilder.build());
        }


        CompleteTaskPayloadBuilder completeTaskPayloadBuilder = new CompleteTaskPayloadBuilder();
        completeTaskPayloadBuilder.withTaskId(task.getId());
        completeTaskPayloadBuilder.withVariable("userId",SecurityConfiguration.getLoggedUsername());



        log.info("Tache prise: {}",task);
        log.info("Tache before working: {}",task);

        task = taskRuntime.complete(completeTaskPayloadBuilder.build());
        log.info("Tache completée: {}",task);

        return task;
    }



    @Override
    public List<ProcessDefinition> getProcessDefinitions() {
        return processRuntime.processDefinitions(Pageable.of(0, 100)).getContent();

    }

    @Override
    public Task preparerDemandeTache(PreparerDemandeTacheCommand creerTacheDemandeProcessCommand) {
        return null;
    }

    @Override
    public Task validerDemandeTache(ValiderDemandeTacheCommand validerDemandeTacheCommand) {
        return null;
    }



}
