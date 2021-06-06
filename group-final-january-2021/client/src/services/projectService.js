import axios from 'axios'

export const patchProjectNameRequest = async (companyName, teamName, projectCurrentName, projectNewName) => {
    let requestLink = "http://localhost:8080/projects/" + companyName + '/' + teamName + '/' + projectCurrentName + "/" + projectNewName
    let response = await axios.patch(requestLink)
    return response.data
        
        
}

export const patchProjectContentRequest = async (companyName, teamName, projectName, newProjectText) => {
    let requestLink = "http://localhost:8080/projects/" + companyName + '/' + teamName + '/' + projectName + "/text"
    let response = await axios({
        method: 'patch',
        url: requestLink,
        headers: { 'Content-Type' : 'text/plain'}, 
        data: newProjectText
      });
    return response.data
        
        
}