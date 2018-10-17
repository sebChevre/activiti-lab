package ch.globaz.devsecops.activiti.lab.application.api.web.process.demandeProcess;

import ch.globaz.devsecops.activiti.lab.application.api.web.process.demandeProcess.dto.*;
import ch.globaz.devsecops.activiti.lab.application.service.process.ProcessusDemandeActivitService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.task.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/process/demande")
public class ProcessusDemandeController {


    private ProcessusDemandeActivitService activitiService;

    @Autowired
    public ProcessusDemandeController(ProcessusDemandeActivitService activitiService){
        this.activitiService = activitiService;
    }

    /**
     * Démarrage d'une instance d'un process (API : /nomProcess/start)
     */
    @PostMapping("/start")
    public ResponseEntity<ProcessInstance> startDemandeProcess(@RequestBody StartDemandeProcessCommand startDemandeProcessCommand) {

        return ResponseEntity.ok(activitiService.creerDemandeProcess(startDemandeProcessCommand));
    }

    //***************************************************************************************************************
    // ETAPES DU PROCESSUS DE CREATION DE LA DEMANDE
    //***************************************************************************************************************
    /**
     * Gestion de la tâche CrééerDemande (API: /instanceId/nomTache)
     */
    @PostMapping("/{processInstanceId}/creerDemande")
    public ResponseEntity<Task> creerDemandeTacheDemandeProcess(@RequestBody CreerDemandeTacheCommand creerDemandeTacheCommand) {

        return ResponseEntity.ok(activitiService.creerDemandeTacheDemandeProcess(creerDemandeTacheCommand));
    }

    @GetMapping("/{processInstanceId}/creerDemande")
    public ResponseEntity<TacheQueryWrapper> creerDemandeTacheDemandeProcess(@PathVariable(name = "processInstanceId") String processId) {

        return ResponseEntity.ok(activitiService.creerDemandeQuery(processId));
    }

    /**
     * Gestion de la tâche PréparerDemande (API: /instanceId/nomTache)
     */
    @PostMapping("/{processInstanceId}/preparerDemande")
    public ResponseEntity<Task> preparerDemandeTacheDemandeProcess(@RequestBody PreparerDemandeTacheCommand preparerDemandeTacheCommand) {

        return ResponseEntity.ok(activitiService.preparerDemandeTacheDemandeProcess(preparerDemandeTacheCommand));
    }

    /**
     * Gestion de la tâche CrééerDemande (API: /instanceId/nomTache)
     */
    @PostMapping("/{processInstanceId}/validerDemande")
    public ResponseEntity<Task> validerDemandeTacheDemandeProcess(@RequestBody ValiderDemandeTacheCommand validerDemandeTacheCommand) {

        return ResponseEntity.ok(activitiService.validerDemandeTacheDemandeProcess(validerDemandeTacheCommand));
    }
    //***************************************************************************************************************



    @GetMapping
    public ResponseEntity<Page<ProcessInstance>> getProcessesInstance(){
        return ResponseEntity.ok(activitiService.getProcessInstances());
    }

    @GetMapping("/{processId}/tasks")
    public ResponseEntity<Page<Task>> getTasksByProcessInstance(@PathVariable(name = "processId") String processId){
        return ResponseEntity.ok(activitiService.getTasksByProcessInstance(processId));
    }





    /**
     * Recupere les taches par assigne
     */
    @GetMapping("/demande/tasks")
    public ResponseEntity<Page<Task>> getTacheByAssigne(
            @RequestParam(name = "user", required = false) String user,@RequestParam(name = "group",required = false) String group) {

        if(group != null){
            log.info("group param set");
            return ResponseEntity.ok(activitiService.getTacheByGroup(group));
        }else if(user != null){
            log.info("user param set");
            return ResponseEntity.ok(activitiService.getTacheByAssigne(user));
        }else{
            log.info("no param set");
            return ResponseEntity.ok(activitiService.getTasks());
        }

    }





    @GetMapping("/process-definitions")
    public ResponseEntity<Page<ProcessInstance>> getProcessDefinition(){

        return ResponseEntity.ok(activitiService.getProcessInstances());
    }


}
