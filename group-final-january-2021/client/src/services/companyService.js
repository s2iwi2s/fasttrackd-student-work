import axios from 'axios'



export const getAllCompaniesRequest = async () => {
    let companyNames = []
    let companiesContent = []
    let requestLink = "http://localhost:8080/companies/"
    let response = await axios.get(requestLink)
    for (var company of response.data) {
        companyNames.push(company.name)
        companiesContent.push(company.text)
    }
    return {
        companyNames: companyNames,
        companiesContent: companiesContent
    }
        
}

export const getCompanyRequest = async (companyName) => {
    let requestLink = "http://localhost:8080/companies/" + companyName
    let response = await axios.get(requestLink)
    return response.data
        
        
}

export const getCompanyTeamsRequest = async (companyName) => {
    let requestLink = "http://localhost:8080/companies/" + companyName + "/teams"
    let response = await axios.get(requestLink)
    let teamNames = []
    let teamsContent = []
    for (var team of response.data) {
        teamNames.push(team.name)
        teamsContent.push(team.text)
    }
    return {
        teamNames: teamNames,
        teamsContent: teamsContent
    }
        
}

export const patchCompanyNameRequest = async (companyCurrentName, companyNewName) => {
    let requestLink = "http://localhost:8080/companies/" + companyCurrentName + "/" + companyNewName
    console.log(requestLink)
    let response = await axios.patch(requestLink)
    return response.data
        
        
}

export const patchCompanyContentRequest = async (companyName, newCompanyText) => {
    let requestLink = "http://localhost:8080/companies/" + companyName + "/text"
    let response = await axios({
        method: 'patch',
        url: requestLink,
        headers: { 'Content-Type' : 'text/plain'}, 
        data: newCompanyText
      });
    

    return response.data
        
        
}