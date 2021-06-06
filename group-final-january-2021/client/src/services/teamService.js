import axios from 'axios'


export const getTeamProjectsRequest = async (teamName, companyName) => { 
    let requestLink = "http://localhost:8080/teams/" + companyName + "/" + teamName + "/projects"
    let response = await axios.get(requestLink)
    console.log(response.data)
    let projectNames = []
    let projectContent = []
    for (var project of response.data) {
        projectNames.push(project.name)
        projectContent.push(project.text)
    }
    return {
        projectNames: projectNames,
        projectsContent: projectContent
    }
      
}

export const patchTeamNameRequest = async (companyName, teamCurrentName, teamNewName) => {
    let requestLink = "http://localhost:8080/teams/" + companyName + '/' + teamCurrentName + '/' + teamNewName
    let response = await axios.patch(requestLink)
    return response.data
        
        
}

export const patchTeamContentRequest = async (companyName, teamName, newTeamText) => {
    let requestLink = "http://localhost:8080/teams/" + companyName + '/' + teamName + "/text"
    let response = await axios({
        method: 'patch',
        url: requestLink,
        headers: { 'Content-Type' : 'text/plain'}, 
        data: newTeamText
      });
    return response.data
        
        
}