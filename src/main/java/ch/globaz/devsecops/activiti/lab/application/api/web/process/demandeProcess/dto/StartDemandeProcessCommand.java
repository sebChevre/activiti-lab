package ch.globaz.devsecops.activiti.lab.application.api.web.process.demandeProcess.dto;

import lombok.Getter;

@Getter
public class StartDemandeProcessCommand {

    private String defaultUserAssignee;

    public StartDemandeProcessCommand(){}

    public StartDemandeProcessCommand(String defaultUserAssignee){
        this.defaultUserAssignee = defaultUserAssignee;
    }


}
