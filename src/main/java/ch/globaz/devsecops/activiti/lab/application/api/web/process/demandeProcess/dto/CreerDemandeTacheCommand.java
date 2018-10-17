package ch.globaz.devsecops.activiti.lab.application.api.web.process.demandeProcess.dto;

import lombok.Getter;

@Getter
public class CreerDemandeTacheCommand {
    private String userAssignee;
    private String idProcess;

    public CreerDemandeTacheCommand(){}

    public CreerDemandeTacheCommand(String userAssignee, String idProcess){
        this.userAssignee = userAssignee;
        this.idProcess = idProcess;
    }

}
