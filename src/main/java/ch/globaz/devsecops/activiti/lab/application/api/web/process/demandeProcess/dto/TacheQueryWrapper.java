package ch.globaz.devsecops.activiti.lab.application.api.web.process.demandeProcess.dto;

import lombok.Getter;
import org.activiti.api.task.model.Task;

import java.util.Map;

@Getter
public class TacheQueryWrapper {

    private Task task;

    private Map<String,Object> data;

    public TacheQueryWrapper(){}

    public TacheQueryWrapper(Task task, Map<String, Object> data) {
        this.task = task;
        this.data = data;
    }
}
