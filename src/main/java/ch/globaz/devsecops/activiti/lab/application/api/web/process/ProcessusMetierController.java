package ch.globaz.devsecops.activiti.lab.application.api.web.process;

import ch.globaz.devsecops.activiti.lab.application.service.ActivitiService;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/process")
public class ProcessusMetierController {


    private ActivitiService activitiService;

    @Autowired
    public ProcessusMetierController(ActivitiService activitiService){
        this.activitiService = activitiService;
    }

    /**
     * Démarrage du process
     */
    @PostMapping("/demande/start")
    public ResponseEntity<ProcessInstance> startDemandeProcess() {
        return ResponseEntity.ok(activitiService.creerDemandeProcess());
    }

    @GetMapping("/process/demande/tasks")
    public ResponseEntity<Page<Task>> getTasks(){
        return ResponseEntity.ok(activitiService.getTasks());
    }

    @GetMapping("/process")
    public ResponseEntity<Page<ProcessInstance>> getProcessesInstance(){
        return ResponseEntity.ok(activitiService.getProcessInstances());
    }

    /**
     * Démarrage du process
     */
    @PostMapping("/process/demande/{id}/creerDemande")
    public ResponseEntity<Task> creerDemandeTache(@PathVariable(name = "id") String idTache) {

        return ResponseEntity.ok(activitiService.creeDemandeTache(idTache));
    }

    @GetMapping("/process-definitions")
    public ResponseEntity<List<ProcessDefinition>> getProcessDefinition(){

        return ResponseEntity.ok(activitiService.getProcessDefinitions());
    }


}
