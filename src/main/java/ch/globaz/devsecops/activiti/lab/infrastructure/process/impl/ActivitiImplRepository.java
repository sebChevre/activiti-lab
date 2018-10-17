package ch.globaz.devsecops.activiti.lab.infrastructure.process.impl;

import ch.globaz.devsecops.activiti.lab.infrastructure.process.ActivitiRepository;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public ProcessInstance createDemandeProcessInstance() {
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("demandeProcess")
                .build());

        return processInstance;
    }

    @Override
    public Page<Task> getTasks() {
        return taskRuntime.tasks(Pageable.of(0, 10));
    }

    @Override
    public Page<ProcessInstance> getProcessInstances() {
        Page<ProcessInstance> processDefinitionPage = processRuntime.processInstances(Pageable.of(0, 10));

        return processDefinitionPage;
    }

    @Override
    public Task creerDemandeTache(String idTache) {

        Task task = taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(idTache).build());

        System.out.println(task);

        task = taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(idTache).build());

        System.out.println(task);

        return task;
    }

    @Override
    public List<ProcessDefinition> getProcessDefinitions() {
        return processRuntime.processDefinitions(Pageable.of(0, 100)).getContent();

    }

}
