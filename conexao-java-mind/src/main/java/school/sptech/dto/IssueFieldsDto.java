package school.sptech.dto;

// DTO: representa como objeto Java o JSON { "project": {...}, "summary": "...", "issuetype": {...} }
public class IssueFieldsDto {

    private ProjectDto project;
    private String summary;
    private IssueTypeDto issuetype;
    private String description;

    public IssueFieldsDto() {
    }

    public IssueFieldsDto(ProjectDto project, String summary, IssueTypeDto issuetype, String description) {
        this.project = project;
        this.summary = summary;
        this.issuetype = issuetype;
        this.description = description;
    }

    public ProjectDto getProject() {
        return project;
    }

    public void setProject(ProjectDto project) {
        this.project = project;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public IssueTypeDto getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(IssueTypeDto issuetype) {
        this.issuetype = issuetype;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}
